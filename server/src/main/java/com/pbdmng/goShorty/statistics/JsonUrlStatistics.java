package com.pbdmng.goShorty.statistics;

import com.google.gson.JsonObject;

/**
 * Provides statistics as JSON formatted string
 * 
 * @author chris
 */
public class JsonUrlStatistics extends UrlStatistics{
	
	static JsonObject jsonBrowserStats;
	static JsonObject jsonCountryStats;
	static JsonObject jsonDateStats;
	
	/*public JsonUrlStatistics(String shortUrl){
		super(shortUrl);
		generateJson();
	}*/
	
	
	public static void generateJson(String shortUrl){
		jsonBrowserStats = new JsonObject();
		jsonCountryStats = new JsonObject();
		jsonDateStats = new JsonObject();
		
		generateStats(shortUrl);
		browserStats.forEach((browser, freq) -> 
			jsonBrowserStats.addProperty(browser, freq));
		
		countryStats.forEach((country, freq) -> 
			jsonCountryStats.addProperty(country, freq));
		
		dateStats.forEach((date, freq) -> 
			jsonDateStats.addProperty(date, freq));
		
		UrlStatistics.resetStats();
	}
	
	public static String getStats(String shortUrl){
		JsonObject jsonStats = new JsonObject();
		generateJson(shortUrl);
		
		jsonStats.add("browserStats", jsonBrowserStats);
		jsonStats.add("countryStats", jsonCountryStats);
		jsonStats.add("dateStats", jsonDateStats);
		jsonStats.addProperty("numClicks", numClicks);
		resetJsonStats();
		
		return jsonStats.toString();
	}
	
	public static void resetJsonStats(){
		jsonBrowserStats = null;
		jsonCountryStats = null;
		jsonDateStats = null;
	}
	
	public static String getBrowserStats(){
		return jsonBrowserStats.toString();
	}
	
	public static String getCountryStats(){
		return jsonCountryStats.toString();
	}
	
	public static String getDateStats(){
		return jsonDateStats.toString();
	}
	
}
