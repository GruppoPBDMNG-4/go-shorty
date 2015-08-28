package com.pbdmng.goShorty.populator;

import com.pbdmng.goShorty.utils.Shortener;

import java.io.BufferedReader;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pbdmng.goShorty.DAO.*;
import com.google.gson.JsonElement;

public class RandomPopulator {
	
	private final static String RELATIVE_PATH = "/src/main/java/com/pbdmng/goShorty/populator/urls.json";
	private static JsonObject urlJson;		
	
	public static void populate(){
		
		String shortUrl;
		String longUrl;
		ReplyDAO reply;
		Gson gson = new Gson();
		DAO dao = new RedisDAO();
		
		if( dao.isPresent("randomePopulated") ) return;
		dao.insertUrl("randomePopulated", "true");
		
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(
					System.getProperty("user.dir") + RELATIVE_PATH));
			urlJson = gson.fromJson(br, JsonObject.class);
		}
		catch (Exception e){
			e.printStackTrace();;
		}
		
		JsonArray urlsArray = urlJson.getAsJsonArray("urls") ;
		for(JsonElement j : urlsArray){
			longUrl = j.toString().replace("\"", "");
			do{
				//longUrl = j.toString()
				shortUrl = Shortener.shorten(longUrl);
				reply = dao.insertUrl(shortUrl, longUrl);
				
			}while( (reply.getResultCode().getCode()) != 1);
		}
		
	}
	
	
	public static void main(String[] args) {
		RandomPopulator.populate();
	}

}
