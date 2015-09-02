package com.pbdmng.goShorty.entity;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.pbdmng.goShorty.utils.ipGeoLocation.CountryLocation;

import java.util.Random;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Click entity. Contains data retrieved form a user's click.
 * Keeps information about the date, the browser, the country and the IP.
 * @author chris
 */
public class Click {
	
	private String IP;
	private String browser;
	private String country;
	private LocalDate date;
	
	/**
	 * Click constructor. It gets the browser from the userAgent,
	 * and the country from the IP. If the IP is not in the database,
	 * it sets a random country.
	 * 
	 * @param IP
	 * @param userAgent
	 */
	public Click(String IP, String userAgent){
		String browser = "other";
		if (userAgent.contains("Chrome/")) browser = "chrome";
		else if (userAgent.contains("Firefox/")) browser = "firefox";
			else if (userAgent.contains("Safari/")) browser = "safari";
				else if (userAgent.contains("OPR/") || userAgent.contains("Opera/")) browser = "opera";
					else if (userAgent.contains(";MSIE")) browser = "explorer";
		
		this.browser = browser;
		this.IP = IP;
		this.date = LocalDate.now(ZoneId.of("Europe/Rome"));
		
		try{
			CountryLocation countryLocation = new CountryLocation();
			this.country = countryLocation.getCountry(IP);
		}catch(IOException e){
			e.printStackTrace();
		}catch(GeoIp2Exception e){
			Random rnd = new Random();
			String[] countries = {"IT", "DE", "US", "JP", "FR", "GB", "IN", "ES", "MX"};
			this.country = countries[rnd.nextInt(8)];
			e.printStackTrace();
		}
	}
	
	public Click(){
		this.date = LocalDate.now(ZoneId.of("Europe/Rome"));
	}
	
	
	// setter
	public void setIP(String IP){
		this.IP = IP;
		try{
			CountryLocation countryLocation = new CountryLocation();
			this.country = countryLocation.getCountry(IP);
		}catch(IOException e){
			e.printStackTrace();
		}catch(GeoIp2Exception e){
			Random rnd = new Random();
			String[] countries = {"IT", "DE", "US", "JP", "FR", "GB", "IN", "ES", "MX"};
			this.country = countries[rnd.nextInt(8)];
			e.printStackTrace();
		}
	}
	
	public void setBrowser(String userAgent){
		String browser = "other";
		if (userAgent.contains("Chrome/")) browser = "chrome";
		if (userAgent.contains("Firefox/")) browser = "firefox";
		if (userAgent.contains("Safari/")) browser = "safari";
		if (userAgent.contains("OPR/")) browser = "opera";
		if (userAgent.contains(";MSIE")) browser = "explorer";
		this.browser = browser;
	}
	
	/**
	 * used only for testing
	 * @param date
	 */
	public void setDate(LocalDate date){
		this.date = date;
	}
	
	// getter
	public String getIP(){
		return this.IP;
	}
	
	public String getBrowser(){
		return this.browser;
	}
	
	public LocalDate getDate(){
		return this.date;
	}
	
	public String getCountry(){
		return this.country;
	}
}
