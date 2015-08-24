package com.pbdmng.goShorty.DAO;

import java.util.ArrayList;

import java.util.List;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;



public class RedisDAO implements DAO {
	
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
	
	public DAOObj insertUrl(String longUrl, String shortUrl){
		DAOObjCode resultCode = DAOObjCode.NOT_INSERTED;
		DAOObj result = new DAOObj();
		result.setResultCode(resultCode);
		//preso da un programma di prova, se vuoi testarlo inserisci il main
		//public static void main(String[] args) {
			   
			   shortUrl = "";
			   //contatore
			  
			   
			   //Data di prova per la lista
			   long now = System.currentTimeMillis();
			   Date date = new Date(now);
			   String dataDaScrivere = new SimpleDateFormat("yyyy-MM-dd").format(date);
			   
			   
			   
		       //Connecting to Redis server on localhost
		       
		      
		       System.out.println("Connection to server sucessfully");
		       
		       //prendo un input come short
			   Scanner in = new Scanner(System.in);
		       printIntestazione();
		       String s = in.nextLine();
		       shortUrl = s;
		       
		       //set the data in redis string
		       jedis.set(longUrl, shortUrl);
		       jedis.lpush(shortUrl, dataDaScrivere);
		       // Get the stored data and print it
		       System.out.println("Stored string in redis:: " );
		       List<String> list = jedis.lrange(shortUrl, 0, -1);
		       for(int i = 0 ; i < list.size(); i++){
		    	   print(list.get(i));
		       }
		       
		       return result;
		 }
		
	// graffa di chiusura al MAIN	
	//}
	
	
	public DAOObj updateUrl(){
		DAOObj ob = new DAOObj();
		return ob;
	}
	
	public DAOObj deleteUrl(){
		DAOObj ob = new DAOObj();
		return ob;
	}
	public String getUrl(String shortUrl){
		String ob = new String();
		return ob;
	}
	public DAOObj setCliks(String ...param){
		DAOObj ob = new DAOObj();
		return ob;
	};
	public List<String> getClicks(){
		List<String> ob = new ArrayList<String>() ;
		return ob;
	}
	
	
	
	public static void printIntestazione(){
		System.out.println("");
		System.out.println("Inserire lo short url da inserire nel database:");
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

}
