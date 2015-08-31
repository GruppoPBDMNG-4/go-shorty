package com.pbdmng.goShorty.spark;

import java.text.Normalizer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pbdmng.goShorty.DAO.*;
import com.pbdmng.goShorty.entity.Click;
import com.pbdmng.goShorty.spark.exceptions.*;
import com.pbdmng.goShorty.utils.Shortener;
import com.pbdmng.goShorty.utils.inspector.*;
import com.pbdmng.goShorty.statistics.*;

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
		String longUrl = jsonRequest.get("longUrl").toString().replaceAll("[^0-9.a-zA-Z-$%&+,/;:=?@#~_]", "");
		String custom = jsonRequest.get("custom").toString().replace("\"", "");
		String customUrlSafe = Normalizer.normalize(custom, Normalizer.Form.NFD).
				   						  replaceAll("[^0-9.a-zA-Z-_]", "");
		
		if (longUrl.equals("")) throw new EmptyUrlException("Empty url");
		if ( !(longUrl.startsWith("https://") || longUrl.startsWith("http://")) ) 
			longUrl = "http://" + longUrl;
		
		if (domainInspector.isNasty(longUrl)) throw new NastyUrlException("Very nasty url");
		
		if (customUrlSafe.equals("")){
			do{
				shortUrl = Shortener.shorten(longUrl);
				reply = dao.insertUrl(shortUrl, longUrl);
				attempt++;
			}while( (reply.getResultCode().getCode()) != (ResultCodeDAO.INSERTED.getCode()) 
					&& MAX_ATTEMPTS > attempt);
			
			if(attempt == MAX_ATTEMPTS) throw new TooManyAttemptsException("Server went bananas");
			else Shortener.setLastUrl("");
			
		} else {
			
			if(wordInspector.isNasty(customUrlSafe)) throw new NastyWordException("Oh that's nasty");
			reply = dao.insertUrl(customUrlSafe, longUrl);
			
			if(reply.getResultCode().getCode() == ResultCodeDAO.INSERTED.getCode())
				shortUrl = customUrlSafe;
			else 
				throw new CustomUrlPresentException("Custom url already taken");
		}
		jsonResponse.addProperty("shortUrl", shortUrl);
		jsonResponse.addProperty("stats", "/rest/stats/:" + shortUrl);
		
		return jsonResponse.toString();
	}
	
	
	public String redirectTo(String shortUrl, String IP, String userAgent) throws DeadLinkException{
		ReplyDAO reply;
		String longUrl;
		Click click = new Click(IP, userAgent);
		
		reply = dao.fetchLongUrl(shortUrl);
		if ( reply.getResultCode().getCode() == ResultCodeDAO.OK.getCode() && !(reply.getLongUrl().equals(""))){
			dao.insertClick(shortUrl, click);
			longUrl = reply.getLongUrl();
		} else throw new DeadLinkException("404");
		
		return longUrl;
	}
	
	
	public String urlStatistics(String shortUrl) throws NonexistentShortUrlException{
		
		if( !(dao.isPresent(shortUrl)) ) 
			throw new NonexistentShortUrlException("Short url not present");
		JsonUrlStatistics jStats = new JsonUrlStatistics(shortUrl);
		
		return jStats.getStats();
	}
	
}
