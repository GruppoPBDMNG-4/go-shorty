package com.pbdmng.goShorty.utils.inspector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import static java.lang.System.err;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;


public class DomainInspector {

	private final static String RELATIVE_PATH = 
			"/src/main/java/com/pbdmng/goShorty/utils/inspector/badDomains/baddomains.json";
	private final static String DOMAINS = "domains";
	JsonObject domainsJson;
	
	public DomainInspector(){
		
		Gson gson = new Gson();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(
					System.getProperty("user.dir").replace("/target", "") + RELATIVE_PATH));
			domainsJson = gson.fromJson(br, JsonObject.class);
		}
		catch (FileNotFoundException e){
			err.println("File not found");
			e.printStackTrace();
		}
	}
	
	
	public boolean isNasty(String domain){
		
		String aNastyDomain;
		boolean nasty = false;
		JsonArray jArray = domainsJson.getAsJsonArray(DOMAINS);
		
		if(domain.startsWith("http://"))
			domain.replace("http://", "");
		else if(domain.startsWith("https://"))
			domain.replace("https://", "");
		
		for (JsonElement j : jArray){
			aNastyDomain = j.toString().replace("\"", "");
			if(domain.contains(aNastyDomain)) {
				nasty = true; 
				break;
			}
		}
			
		return nasty;
	}

}
