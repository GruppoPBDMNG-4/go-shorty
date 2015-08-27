package com.pbdmng.goShorty.statistics;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

public class JsonUrlStatistics extends UrlStatistics{
	
	private JsonObject jsonBrowserStats = new JsonObject();
	private JsonObject jsonCountryStats = new JsonObject();
	private JsonObject jsonDateStats = new JsonObject();

	
	public JsonUrlStatistics(String shortUrl){
		super(shortUrl);
		generateJson();
	}
	
	
	private void generateJson(){
		this.browserStats.forEach((browser, freq) -> 
			jsonBrowserStats.addProperty(browser, freq));
		
		this.countryStats.forEach((country, freq) -> 
			jsonCountryStats.addProperty(country, freq));
		
		this.dateStats.forEach((date, freq) -> 
			jsonDateStats.addProperty(date, freq));
	}
	
	
	public String getBrowserStats(){
		return this.jsonBrowserStats.toString();
	}
	
	
	public String getCountryStats(){
		return this.jsonCountryStats.toString();
	}
	
	
	public String getDateStats(){
		return this.jsonDateStats.toString();
	}
	
	public static void main(String[] args) {
		JsonUrlStatistics jus = new JsonUrlStatistics("mammt");
		Map<String, Integer> hm = new HashMap<String, Integer>();
		String m = jus.getCountryStats();
		
		System.out.println(m);
		m = jus.getDateStats();
		
		System.out.println(m);
		m = jus.getBrowserStats();
		
		System.out.println(m);
		
	}

}
