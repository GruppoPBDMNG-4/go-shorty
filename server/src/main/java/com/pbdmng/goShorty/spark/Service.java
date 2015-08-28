package com.pbdmng.goShorty.spark;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pbdmng.goShorty.DAO.*;
import com.pbdmng.goShorty.utils.Shortener;
import com.pbdmng.goShorty.utils.inspector.*;

public class Service {
	
	RedisDAO dao ;
	
	final static int MAX_ATTEMPTS = 20;
	
	public Service(){
		
		this.dao = new RedisDAO() ;
		
	}
	
	public String shortenUrl(String requestBody) throws Exception{
		DomainInspector di = new DomainInspector();
		WordInspector wi = new WordInspector() ;
		
		String shortUrl;
		ReplyDAO reply; 
		int numTentativi = 0;
		
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(requestBody, JsonObject.class);
		JsonObject rispostaJson = new JsonObject(); 
		String longUrl = json.get("longUrl").toString().replace("\"", "");
		if(longUrl.equals("")){
			throw new Exception();
		}
		
		if (di.isNasty(longUrl)){
			throw new Exception();
		}
		
		String custom = json.get("custom").toString().replace("\"", "");
		if (custom.equals(""))
		{
			do{
				//longUrl = j.toString()
				shortUrl = Shortener.shorten(longUrl);
				reply = dao.insertUrl(shortUrl, longUrl);
				numTentativi++;
				
			}while( (reply.getResultCode().getCode()) != (ResultCodeDAO.INSERTED.getCode()) && MAX_ATTEMPTS > numTentativi);
		} else {
			if(wi.isNasty(custom))
				throw new Exception();
			reply = dao.insertUrl(custom, longUrl);
			if(reply.getResultCode().getCode() == ResultCodeDAO.INSERTED.getCode())
				shortUrl = custom;
			else 
				throw new Exception();
		}
		
		rispostaJson.addProperty("shorty", shortUrl);
		return rispostaJson.toString();
		
	}
	
	public static void main (String[] args){
		Service s = new Service();
		JsonObject jsonTmp = new JsonObject();
		jsonTmp.addProperty("longUrl", "lapizza.it");
		jsonTmp.addProperty("custom", "ciaone");
		try{
		System.out.println(s.shortenUrl(jsonTmp.toString()));
		}
		catch (Exception e) {
			
		}
		}

}
