package com.pbdmng.goShorty.DAO;

import java.util.ArrayList;

import java.util.List;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.Gson;
import com.pbdmng.goShorty.entity.Click;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;



public class RedisDAO implements DAO {
	
	private final static String CLICK_LIST = ":clicks";
	private static JedisPool jPool;
	
	private Jedis jedis;
	
	
	
	public RedisDAO(){

		 try {
	    	   this.jedis = getIstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
			}
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
	
	public ReplyDAO insertUrl(String shortUrl, String longUrl){
		ResultCodeDAO resultCode = ResultCodeDAO.NOT_INSERTED;
		ReplyDAO reply = new ReplyDAO();
		System.out.println("Connection to server sucessfully");
		resultCode.setCode(jedis.setnx(shortUrl, longUrl)); 
		// Get the stored data and print it
		//System.out.println("Stored string in redis "+resultCode.getCode() );
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
	public ReplyDAO fetchLongUrl(String shortUrl){
		ReplyDAO reply = new ReplyDAO();
		ResultCodeDAO resultCode = ResultCodeDAO.NOT_PRESENT;
		if (isPresent(shortUrl)){
			reply.setLongUrl(jedis.get(shortUrl));
			resultCode = ResultCodeDAO.OK;
			print(reply.getLongUrl());
		}
		
		reply.setResultCode(resultCode);
		return reply;
	}
	
	public ReplyDAO insertClick(String shortUrl, Click click) {
		
		ReplyDAO reply = new ReplyDAO();
		ResultCodeDAO resultCode = ResultCodeDAO.NOT_PRESENT;
		
		Gson gson = new Gson();
		String jsonClick = gson.toJson(click).toString();
		print(jsonClick);
		
		String key = shortUrl + CLICK_LIST;
		if(isPresent(shortUrl)){
			jedis.lpush(key, jsonClick);
			resultCode = ResultCodeDAO.OK;
			print(String.valueOf(resultCode.getCode()));
		}
		reply.setResultCode(resultCode);
		
		return reply;
	}
	
	
	public ReplyDAO fetchKeys() {
		ReplyDAO reply = new ReplyDAO();
		ResultCodeDAO resultCode = ResultCodeDAO.NOT_PRESENT;
		reply.setKeySet(jedis.keys("*"));
		if( (reply.getKeySet()).size() >= 0) 
			resultCode = ResultCodeDAO.OK;
		reply.setResultCode(resultCode);
		
		/*
		Set<String> hs = new HashSet<String>();
		hs = reply.getKeySet();
		Iterator it = hs.iterator();
		while(it.hasNext()) System.out.println(it.next() );
		System.out.println(hs.size());
		*/
		
		return reply;
	}
	
	public ReplyDAO fetchClicks(String shortUrl) {
		ReplyDAO reply = new ReplyDAO();
		ResultCodeDAO resultCode = ResultCodeDAO.NOT_PRESENT;
		
		String key = shortUrl + CLICK_LIST;
		if(isPresent(key)){
			List<Click> clickList =  new ArrayList<Click>();
			List<String> jsonList = new ArrayList<String>();
			jsonList = jedis.lrange(key, 0, -1);
			Gson gson = new Gson();
			
			Click tmp;
			for(int i = 0; i < jsonList.size(); i++){
				tmp = gson.fromJson(jsonList.get(i), Click.class);
				clickList.add(tmp);
			}
			reply.setListClicks(clickList);
			resultCode = ResultCodeDAO.OK;
		}
		reply.setResultCode(resultCode);
		
		return reply;
	}
	
	
	
	public static void print(String s){
		System.out.println("");
		System.out.println(s);
	}
	
	//***************************** utils for class
	private static Jedis getIstance() throws Exception{
		if (jPool == null) {
			jPool = new JedisPool("localhost");
		}
		return jPool.getResource();
	}


	public static void main(String[] args) {
	RedisDAO redis = new RedisDAO();
	redis.insertUrl("dj","sparalicchio");
	print("primo: ");
	redis.fetchLongUrl("obaoba");
	print("secondo: ");
	redis.fetchLongUrl("dj");
	
	Click clc = new Click();
	clc.setBrowser("mozzarella");
	clc.setIP("195.37.108.130");
	redis.insertClick("mammt", clc);
	redis.fetchKeys();
	
	List<Click> clickL = new ArrayList<Click>();
	ReplyDAO rp = redis.fetchClicks("mammt");
	clickL = rp.getListClicks();
	print("______________________________________");
	for(Click c : clickL){
		print(c.getIP());
		print(c.getBrowser());
		print(c.getCountry());
		print(c.getDate().toString());print("______________________________________");
	}

	
	}

}
