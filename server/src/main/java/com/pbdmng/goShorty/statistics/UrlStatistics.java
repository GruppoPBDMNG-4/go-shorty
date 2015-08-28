package com.pbdmng.goShorty.statistics;

import com.pbdmng.goShorty.DAO.*;
import com.pbdmng.goShorty.entity.Click;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlStatistics {
	
	String shortUrl;
	Map<String, Integer> browserStats = new HashMap<String, Integer>();
	Map<String, Integer> countryStats = new HashMap<String, Integer>();
	Map<String, Integer> dateStats = new HashMap<String, Integer>();
	
	public UrlStatistics(String shortUrl){
		this.shortUrl = shortUrl;
		generateStats();
	}
	
	
	private void generateStats(){
		
		RedisDAO dao = new RedisDAO();
		List<Click> clickList = new ArrayList<Click>();
		
		if(dao.isPresent(shortUrl)){
			
			clickList = dao.fetchClicks(shortUrl, 0, -1).getClickList();
			for(Click click : clickList){
				
				browserStats.put(click.getBrowser(), browserStats.getOrDefault(click.getBrowser(), 0) + 1);
				countryStats.put(click.getCountry(), countryStats.getOrDefault(click.getCountry(), 0) + 1);
				
				int month = click.getDate().getMonthValue();
				int year = click.getDate().getYear();
				String date = year +"-" + month;
				
				dateStats.put(date, dateStats.getOrDefault(date, 0) + 1);
			}
		}
	}
	
}
