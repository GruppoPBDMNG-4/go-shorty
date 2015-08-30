package com.pbdmng.goShorty.DAO;

import com.pbdmng.goShorty.entity.Click;

import java.util.ArrayList;
import java.util.List;

import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * @author paolobi
 *
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
	
	
	public ReplyDAO insertUrl(String shortUrl, String longUrl){
		
		ReplyDAO reply = new ReplyDAO();
		ResultCodeDAO resultCode = ResultCodeDAO.NOT_INSERTED;
		
		resultCode.setCode( jedis.setnx(shortUrl, longUrl) );
		
		/* If the shortUrl is already present, 
		 * it checks if its longUrl is the same as the one that has to be inserted. 
		 * If so it sets the reply code to inserted. */
		if( resultCode.getCode() == 0)
			if(longUrl.equals( jedis.get(shortUrl) ))
				resultCode = ResultCodeDAO.INSERTED;
		
		reply.setResultCode(resultCode);
		return reply;
	}
	
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
	
	
	public boolean isPresent(String shortUrl){
		
		boolean present = true;
		
		try{
			present = jedis.exists(shortUrl.getBytes("UTF-8"));
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return present;
	}
	
	
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
	
	public ReplyDAO fetchKeys(String regEx) {
		
		ReplyDAO reply = new ReplyDAO();
		ResultCodeDAO resultCode = ResultCodeDAO.NOT_PRESENT;
		
		reply.setKeySet( jedis.keys(regEx) );
		
		if( (reply.getKeySet()).size() >= 0) 
			resultCode = ResultCodeDAO.OK;
		
		reply.setResultCode(resultCode);
		
		return reply;
	}
	
	
	
	public ReplyDAO updateUrl(){
		ReplyDAO ob = new ReplyDAO();
		return ob;
	}
	
	public ReplyDAO deleteUrl(){
		ReplyDAO ob = new ReplyDAO();
		return ob;
	}
	
	
	private static Jedis getIstance() throws Exception{
		if (jPool == null) {
			jPool = new JedisPool("localhost");
		}
		return jPool.getResource();
	} 
	public static void main(String[] args){
		RedisDAO jedis = new RedisDAO();
		ReplyDAO r = new ReplyDAO();
		r = jedis.insertUrl("bababababa", "pippopippo");
		System.out.println(r.getResultCode().getCode());
	}

}
