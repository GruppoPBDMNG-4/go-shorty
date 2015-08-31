package com.pbdmng.goShorty.utils.ipGeoLocation;

import java.io.IOException;
import junit.framework.TestCase;

import com.maxmind.geoip2.exception.GeoIp2Exception;


public class CountryLocationTest extends TestCase {
	
	private String[] ips = {"31.72.213.143",
							"43.12.223.34",
							"21.34.223.34",
							"81.12.223.34",
							"81.12.53.34",
							"58.56.98.75",
							"85.96.31.47",
							"151.25.78.65"};
	
	private String[] countries ={"GB","JP","US","RO","IR","CN","TR","IT"};
	
	CountryLocation countryLocation;
	
	protected void setUp() throws Exception {
		countryLocation = new CountryLocation();
		super.setUp();
	}
	
	protected void tearDown() throws Exception {
		countryLocation = null;
		super.tearDown();
	}
	
	
	public void testGetCountry() throws IOException, GeoIp2Exception {
		
		for(int i = 0; i < ips.length ;i++){
			assertTrue(i + ". IP " + ips[i] +" is not from  " + countries[i],
					countryLocation.getCountry(ips[i]).equalsIgnoreCase(countries[i]));
		}
	}
	
}
