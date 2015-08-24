package com.pbdmng.goShorty.utils.ipGeoLocation;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;


public class CountryLocation {
	
	private DatabaseReader reader;
	private File database ;
	private String relativePath = "/src/main/java/com/pbdmng/goShorty/utils/ipGeoLocation/GeoLite2-Country.mmdb";
	
	public CountryLocation() throws IOException{
		
		database = new File(System.getProperty("user.dir") + relativePath);
		reader = new DatabaseReader.Builder(database).build();
		
	}
	
	public String getCountry(String IP) throws IOException, GeoIp2Exception{
		
		InetAddress ipAddress = InetAddress.getByName(IP);
		CountryResponse response = reader.country(ipAddress);
		Country country = response.getCountry();	
		
		return country.getIsoCode();
	}

}
