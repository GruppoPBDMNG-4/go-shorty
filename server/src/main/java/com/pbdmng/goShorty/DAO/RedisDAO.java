package com.pbdmng.goShorty.DAO;

import com.pbdmng.goShorty.entity.Click;

import java.util.ArrayList;
import java.util.List;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * @author paolobi
 * 
 * DAO implementation with Jedis as a Redis client
 */
public class RedisDAO implements DAO {
	
	private final static String CLICK_LIST = ":clicks";
	private static JedisPool jPool;
	private Jedis jedis;
	
	
	public RedisDAO(){
		try {
			 this.jedis = getIstance();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Inserts a Redis string into the DB
	 * If the shortUrl is already present, 
	 * it checks if its longUrl is the same as the one that has to be inserted. 
	 * If so it sets the reply code to inserted.
	 * 
	 * @param shortUrl 	key
	 * @param longUrl 	value
	 * @return a ReplyDAO object with the result code
	 */
	public ReplyDAO insertUrl(String shortUrl, String longUrl){
		
		ReplyDAO reply = new ReplyDAO();
		ResultCodeDAO resultCode = ResultCodeDAO.NOT_INSERTED;
		
		resultCode.setCode( jedis.setnx(shortUrl, longUrl) );
		
		if( resultCode.getCode() == 0)
			if(longUrl.equals( jedis.get(shortUrl) ))
				resultCode = ResultCodeDAO.INSERTED;
		
		reply.setResultCode(resultCode);
		return reply;
	}
	
	/**
	 * Inserts a Redis list into the DB using the pattern <shortUrl>:clicks. 
	 * 
	 * @param shortUrl  needed to access to the list
	 * @param click 	It contains data retrieved from a user's click 
	 * 		  			such as the IP, the country, the browser and the date.
	 * @return a ReplyDAO object with the result code
	 */
	public ReplyDAO insertClick(String shortUrl, Click click) {
		
		ReplyDAO reply = new ReplyDAO();
		ResultCodeDAO resultCode = ResultCodeDAO.NOT_PRESENT;
		
		Gson gson = new Gson();
		String jsonClick = gson.toJson(click).toString();
		String shortUrlClicks = shortUrl + CLICK_LIST;
		
		if(isPresent(shortUrl)){
			jedis.lpush(shortUrlClicks, jsonClick);
			resultCode = ResultCodeDAO.OK;
		}
		reply.setResultCode(resultCode);
		
		return reply;
	}
	
	/**
	 * Checks if a shortUrl(key) is present in the DB
	 * 
	 * @param shortUrl 	key that has to be checked	
	 * @return 			true if is present, false otherwise
	 */
	public boolean isPresent(String shortUrl){
		
		boolean present = true;
		try{
			present = jedis.exists(shortUrl.getBytes("UTF-8"));
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return present;
	}
	
	
	/**
	 * Fetches a value from a given key
	 * 
	 * @param shortUrl 	key
	 * #return 			an object with the result code of the operation 
	 * 					and the longUrl(value) fetched 
	 */
	public ReplyDAO fetchLongUrl(String shortUrl){
		
		ReplyDAO reply = new ReplyDAO();
		ResultCodeDAO resultCode = ResultCodeDAO.NOT_PRESENT;
		if (isPresent(shortUrl)){
			reply.setLongUrl( jedis.get(shortUrl) );
			resultCode = ResultCodeDAO.OK;
		}
		reply.setResultCode(resultCode);
		
		return reply;
	}
	
	/**
	 * Fetches a list of clicks from a given key
	 * 
	 * @param shortUrl	the shortUrl we want to fetch the clicks
	 * @param from		starting point
	 * @param to		ending point
	 * @return			an object with the result code and the click list
	 */
	public ReplyDAO fetchClicks(String shortUrl, int from, int to) {
		
		ReplyDAO reply = new ReplyDAO();
		ResultCodeDAO resultCode = ResultCodeDAO.NOT_PRESENT;
		String shortUrlClicks = shortUrl + CLICK_LIST;
		
		if( isPresent(shortUrlClicks) ){
			List<Click> clickList =  new ArrayList<Click>();
			List<String> jsonList = new ArrayList<String>();
			
			jsonList = jedis.lrange(shortUrlClicks, from, to);
			
			Gson gson = new Gson();
			
			for(String json : jsonList)
				clickList.add( gson.fromJson(json, Click.class) );
			
			reply.setClickList(clickList);
			resultCode = ResultCodeDAO.OK;
		}
		reply.setResultCode(resultCode);
		
		return reply;
	}
	
	/**
	 * Fetches keys stored into the DB that matches a regular expression
	 * 
	 * @param regEx		regular expression
	 * @return			an object with the result code and the key set
	 */
	public ReplyDAO fetchKeys(String regEx) {
		
		ReplyDAO reply = new ReplyDAO();
		ResultCodeDAO resultCode = ResultCodeDAO.NOT_PRESENT;
		
		reply.setKeySet( jedis.keys(regEx) );
		
		if( (reply.getKeySet()).size() >= 0) 
			resultCode = ResultCodeDAO.OK;
		
		reply.setResultCode(resultCode);
		
		return reply;
	}
	
	public ReplyDAO updateUrl(String key){
		ReplyDAO ob = new ReplyDAO();
		return ob;
	}
	
	/**
	 * Used only for testing
	 * 
	 */
	public ReplyDAO deleteUrl(String key){
		ReplyDAO reply = new ReplyDAO();
		jedis.del(key);
		return reply;
	}
	
	private static Jedis getIstance() throws Exception{
		if (jPool == null) {
			jPool = new JedisPool("localhost");
		}
		return jPool.getResource();
	} 

}
