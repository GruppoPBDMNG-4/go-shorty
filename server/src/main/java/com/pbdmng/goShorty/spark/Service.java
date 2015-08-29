package com.pbdmng.goShorty.spark;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pbdmng.goShorty.DAO.*;
import com.pbdmng.goShorty.entity.Click;
import com.pbdmng.goShorty.spark.exceptions.*;
import com.pbdmng.goShorty.utils.Shortener;
import com.pbdmng.goShorty.utils.inspector.*;

public class Service {
	
	DAO dao;
	
	private final static int MAX_ATTEMPTS = 30;
	
	public Service(DAO dao){
		this.dao = dao;
	}
	
	
	public String shortenUrl(String requestBody) {
		
		DomainInspector domainInspector = new DomainInspector();
		WordInspector wordInspector = new WordInspector() ;
		Gson gson = new Gson();
		String shortUrl;
		ReplyDAO reply; 
		int attempt = 0;
		JsonObject jsonRequest = gson.fromJson(requestBody, JsonObject.class);
		JsonObject jsonResponse = new JsonObject(); 
		String longUrl = jsonRequest.get("longUrl").toString().replace("\"", "");
		String custom = jsonRequest.get("custom").toString().replace("\"", "");
		
		
		if(longUrl.equals("")) throw new EmptyUrlException("Empty url");
		if (domainInspector.isNasty(longUrl)) throw new NastyUrlException("Very nasty url");
		
		if (custom.equals("")){
			do{
				shortUrl = Shortener.shorten(longUrl);
				reply = dao.insertUrl(shortUrl, longUrl);
				attempt++;
			}while( (reply.getResultCode().getCode()) != (ResultCodeDAO.INSERTED.getCode()) 
					&& MAX_ATTEMPTS > attempt);
			
			if(attempt == MAX_ATTEMPTS) throw new TooManyAttemptsException("Server went bananas");
			
		} else {
			if(wordInspector.isNasty(custom))
				throw new NastyWordException("Oh that's nasty");
			
			reply = dao.insertUrl(custom, longUrl);
			if(reply.getResultCode().getCode() == ResultCodeDAO.INSERTED.getCode())
				shortUrl = custom;
			else 
				throw new CustomUrlPresentException("Custom url already taken");
		}
		jsonResponse.addProperty("shorty", shortUrl);
		
		return jsonResponse.toString();
	}
	
	public String redirectTo(String shortUrl, Click click){
		return null;
	}
	
	public String urlStatistics(String shortUrl){
		return null;
	}
	

}
