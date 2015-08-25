package com.pbdmng.goShorty.entity;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.pbdmng.goShorty.utils.ipGeoLocation.CountryLocation;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

public class Click {
	
	private String IP;
	private String browser;
	private String country;
	private LocalDate date;
	
	public Click(String IP, String browser){
		this.IP = IP;
		this.browser = browser;
		this.date = LocalDate.now(ZoneId.of("Europe/Rome"));
		try{
			CountryLocation countryLocation = new CountryLocation();
			this.country = countryLocation.getCountry(IP);
		}catch(IOException e){
			e.printStackTrace();
		}catch(GeoIp2Exception e){
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
			e.printStackTrace();
		}
	}
	
	public void setBrowser(String browser){
		this.browser = browser;
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
