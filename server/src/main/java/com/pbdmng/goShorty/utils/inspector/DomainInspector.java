package com.pbdmng.goShorty.utils.inspector;

import java.io.BufferedReader;
import java.io.FileReader;

import com.google.gson.JsonObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;


public class DomainInspector {

	private final static String RELATIVE_PATH = "/src/main/java/com/pbdmng/goShorty/utils/inspector/badDomains/baddomains.json";
	private final static String DOMAINS = "domains";
	JsonObject domainsJson;
	
	public DomainInspector(){
		
		Gson gson = new Gson();
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+RELATIVE_PATH));
			domainsJson = gson.fromJson(br, JsonObject.class);
			
		}
		catch (Exception e){
			e.printStackTrace();;
		}
	}
	
	
	public boolean isNasty(String domain){
		
		boolean nasty = false;
		
		JsonArray jArray = domainsJson.getAsJsonArray(DOMAINS);
		
		if(domain.startsWith("http://"))
			domain.replace("http://", "");
		else if(domain.startsWith("https://"))
			domain.replace("https://", "");
		
		String check;
		for (JsonElement j : jArray){
			
			check = j.toString().replace("\"", "");
			if(domain.equalsIgnoreCase(check)) {nasty = true; break;}
		}
			
		return nasty;
	}
	
	/*public static void main(String[] args) {
		
		DomainInspector dc = new DomainInspector();
		System.out.println(dc.isNasty("www.1day.su"));
		
	}*/

}
