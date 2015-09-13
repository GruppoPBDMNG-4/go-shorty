package com.pbdmng.goShorty.statistics;

import com.pbdmng.goShorty.DAO.*;
import com.pbdmng.goShorty.entity.Click;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates statistics from a given shortUrl
 * and creates a Map with the frequency of each element.
 * 
 * @author chris
 */
public class UrlStatistics {
	
	static String shortUrl;
	static Map<String, Integer> browserStats;
	static Map<String, Integer> countryStats;
	static Map<String, Integer> dateStats;
	static int numClicks;
	static String longUrl;
	static DAO dao = new RedisDAO();
	
	
	public static void generateStats(String shortUrl){
		
		browserStats = new HashMap<String, Integer>();
		countryStats = new HashMap<String, Integer>();
		dateStats = new HashMap<String, Integer>();
		
		List<Click> clickList = new ArrayList<Click>();
		
		if(dao.isPresent(shortUrl)){
			
			clickList = dao.fetchClicks(shortUrl, 0, -1).getClickList();
			numClicks = clickList.size();
			longUrl = dao.fetchLongUrl(shortUrl).getLongUrl();
			for(Click click : clickList){
				
				browserStats.put(click.getBrowser(), browserStats.getOrDefault(click.getBrowser(), 0) + 1);
				countryStats.put(click.getCountry(), countryStats.getOrDefault(click.getCountry(), 0) + 1);
				
				int month = click.getDate().getMonthValue();
				int year = click.getDate().getYear();
				String date = year + "-" + month;
				
				dateStats.put(date, dateStats.getOrDefault(date, 0) + 1);
			}
		}
	}
	
	public static void resetStats(){
		browserStats = null;
		countryStats = null;
		dateStats = null;
	}
	
}
