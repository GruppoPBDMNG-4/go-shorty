package com.pbdmng.goShorty.statistics;

import com.pbdmng.goShorty.DAO.*;
import com.pbdmng.goShorty.entity.Click;

import java.util.HashMap;
import java.util.Map;

public class UrlStatistics {
	
	private String shortUrl;
	private Map<String, Integer> browserStats = new HashMap<String, Integer>();
	private Map<String, Integer> countryStats = new HashMap<String, Integer>();
	private Map<String, Integer> monthStats = new HashMap<String, Integer>();
	private final static String CLICK_LIST = ":clicks";
	
	
	public UrlStatistics(String shortUrl){
		this.shortUrl = shortUrl;
		String shortUrlClicks = shortUrl + CLICK_LIST;
		generateStats(shortUrlClicks);
	}
	
	private void generateStats(String shortUrlClicks){
		RedisDAO dao = new RedisDAO();
		if(dao.isPresent(shortUrlClicks)){
			for(Click click : dao.fetchClicks(shortUrlClicks, 0, -1).getClickList()){
				browserStats.put(click.getBrowser(), browserStats.getOrDefault(click.getBrowser(), 0) + 1);
				countryStats.put(click.getCountry(), countryStats.getOrDefault(click.getCountry(), 0) + 1);
				
				int month = click.getDate().getMonthValue();
				int year = click.getDate().getYear();
				String date = month +"-" + year;
				monthStats.put(date, monthStats.getOrDefault(click.getBrowser(), 0) + 1);
			}
		}
	}
	
	/*
	public static void main(String[] args) {
		
		Map<String, Integer> cd = new HashMap<String, Integer>();
		cd.put("ciao", cd.getOrDefault("ciao", 0) + 1);
	}*/
}
