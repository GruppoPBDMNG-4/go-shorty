package com.pbdmng.goShorty.populator;

import com.pbdmng.goShorty.entity.Click;
import com.pbdmng.goShorty.utils.Shortener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pbdmng.goShorty.DAO.*;
import com.google.gson.JsonElement;

public class RandomPopulator {
	
	private final static String RELATIVE_PATH = "/src/main/java/com/pbdmng/goShorty/populator/urls.json";
	private final static int MAX_ATTEMPTS = 20;
	private final static String URLS = "urls";
	private static JsonObject urlJson;		
	
	public static void populate(){
		
		Random rnd = new Random();
		String shortUrl, longUrl;
		ReplyDAO reply;
		int attempt = 0;
		Click click = new Click();
		Gson gson = new Gson();
		DAO dao = new RedisDAO();
		
		if( dao.isPresent("randomePopulated") ) return;
		dao.insertUrl("randomePopulated", "yup");
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(
					System.getProperty("user.dir") + RELATIVE_PATH));
			urlJson = gson.fromJson(br, JsonObject.class);
		}
		catch (Exception e){
			e.printStackTrace();;
		}
		
		JsonArray urlsArray = urlJson.getAsJsonArray(URLS) ;
		for(JsonElement j : urlsArray){
			
			longUrl = j.toString().replace("\"", "");
			do{
				shortUrl = Shortener.shorten(longUrl);
				reply = dao.insertUrl(shortUrl, longUrl);
				attempt++;
				
			}while( ((reply.getResultCode().getCode()) != ResultCodeDAO.INSERTED.getCode()) 
					&& attempt < MAX_ATTEMPTS );
			
			for(int i = 0; i < rnd.nextInt(15); i++){
				click.setIP(randomIP());
				click.setBrowser(randomBrowser());
				click.setDate(randomDate());
				dao.insertClick(shortUrl, click);
			}
		}
		
	}
	
	
	private static String randomIP(){
		Random rnd = new Random();
		String IP = rnd.nextInt(223) + "." + rnd.nextInt(255) 
				+ "." + rnd.nextInt(255) + "." +rnd.nextInt(255);
		
		return IP;
	}
	
	private static String randomBrowser(){
		Random rnd = new Random();
		String[] userAgents = {"Chrome/", "Safari/", "Firefox/", ";MSIE", "OPR/"};
		return userAgents[rnd.nextInt(4)];
	}
	
	private static LocalDate randomDate(){
		Random rnd = new Random();
		int minDay = (int) LocalDate.of(2010, 1, 1).toEpochDay();
		int maxDay = (int) LocalDate.now().toEpochDay();
		long randomDay = minDay + rnd.nextInt(maxDay - minDay);
		
		return LocalDate.ofEpochDay(randomDay);
	}
	
}
