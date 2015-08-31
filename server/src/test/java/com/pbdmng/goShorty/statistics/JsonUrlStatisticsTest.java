package com.pbdmng.goShorty.statistics;

import java.time.LocalDate;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pbdmng.goShorty.DAO.*;
import com.pbdmng.goShorty.entity.Click;

public class JsonUrlStatisticsTest extends TestCase {
	
	private DAO dao = new RedisDAO();
	private Click[] clickArray = {new Click("151.25.78.65",  "Chrome/"),    //IT
								 new Click("43.12.223.34",   "Safari/"),    //JP
								 new Click("31.72.213.143",  "Opera/"),     //GB
								 new Click("151.43.135.71",  "Safari/"),    //IT
								 new Click("58.56.98.75",    "Firefox/")};  //CN
	
	private JsonObject jsonBrowserStats = new JsonObject();
	private JsonObject jsonCountryStats = new JsonObject();
	private JsonObject jsonDateStats = new JsonObject();
	
	private JsonObject jsonExpected = new JsonObject();
	
	
	protected void setUp() throws Exception {
		super.setUp();
		
		clickArray[2].setDate((LocalDate.of(2011, 1, 1)));
		dao.insertUrl("jUnitTest", "http://jUnitTest.com");
		
		for(Click click : clickArray)
			dao.insertClick("jUnitTest", click);
		
		jsonBrowserStats.addProperty("safari", 2);
		jsonBrowserStats.addProperty("chrome", 1);
		jsonBrowserStats.addProperty("opera", 1);
		jsonBrowserStats.addProperty("firefox", 1);
		
		jsonCountryStats.addProperty("IT", 2);
		jsonCountryStats.addProperty("JP", 1);
		jsonCountryStats.addProperty("GB", 1);
		jsonCountryStats.addProperty("CN", 1);
		
		String date = LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue();
		jsonDateStats.addProperty(date, 4);
		jsonDateStats.addProperty("2011-1", 1);
		
		jsonExpected.add("browserStats", jsonBrowserStats);
		jsonExpected.add("countryStats", jsonCountryStats);
		jsonExpected.add("dateStats", jsonDateStats);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		dao.deleteUrl("jUnitTest");
		dao.deleteUrl("jUnitTest:clicks");
	}
	
	public void testgetStats() {
		JsonUrlStatistics jsonUrlStatistics = new JsonUrlStatistics("jUnitTest");
		Gson gson = new Gson();
		JsonObject jsonActual = gson.fromJson(jsonUrlStatistics.getStats(), JsonObject.class);
		
		assertEquals("Test failed", jsonExpected, jsonActual);
			
		
	}

}
