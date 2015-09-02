package com.pbdmng.goShorty.spark;

import com.pbdmng.goShorty.DAO.*;
import junit.framework.TestCase;


public class ServiceTest extends TestCase {
	
	DAO dao;
	
	private String[] requestBody = {  "{'longUrl':\"http://sparkjava.com/documentation.html#request\","
										+ "'custom':\"\"}",
									  "{\"longUrl\":\"http://vertx.io/docs/\","
										+ "\"custom\":\"\"}",
									  "{\"longUrl\":\"https://angular.io/\","
										+ "\"custom\":\"angular\"}",
									  "{\"longUrl\":\"http://pbdmng.datatoknowledge.it/case_study.html\","
										+ "\"custom\":\"\"}",
									  "{\"longUrl\":\"https://hub.docker.com/\","
										+ "\"custom\":\"dockerhub\"}",
								   };
	
	private String[] response = { "{\"shortUrl\":\"SFrY3o0a\",\"stats\":\"/rest/stats/:SFrY3o0a\"}",
								  "{\"shortUrl\":\"2l4c3o0a\",\"stats\":\"/rest/stats/:2l4c3o0a\"}",
								  "{\"shortUrl\":\"angular\",\"stats\":\"/rest/stats/:angular\"}",
								  "{\"shortUrl\":\"X5uZ3o0a\",\"stats\":\"/rest/stats/:X5uZ3o0a\"}",
								  "{\"shortUrl\":\"dockerhub\",\"stats\":\"/rest/stats/:dockerhub\"}"
								};
	
	private String[] from = {"SFrY3o0a","2l4c3o0a","angular","X5uZ3o0a","dockerhub"};
	
	private String[] to = {"http://sparkjava.com/documentation.html#request",
							 "http://vertx.io/docs/",
							 "https://angular.io/",
							 "http://pbdmng.datatoknowledge.it/case_study.html",
							 "https://hub.docker.com/"};
	
	private Service service;
	
	protected void setUp() throws Exception {
		super.setUp();
		service = new Service(new RedisDAO());
		dao = new RedisDAO();
		
		for(int i = 0; i < from.length; i++)
			dao.insertUrl(from[i], to[i]);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		for(int i = 0; i < from.length; i++){			
			dao.deleteUrl(from[i]);
			dao.deleteUrl(from[i] + ":clicks");
		}
	}
	
	
	
	public void testShortenUrl()throws Exception{
		for(int i = 0; i < response.length; i++)
			assertEquals("Test failed", service.shortenUrl(requestBody[i]), response[i]);
	}
	
	public void testRedirectTo()throws Exception{
		for(int i = 0; i < response.length; i++)
			assertEquals("Test Failed", service.redirectTo(from[i], "151.25.78.65", "Chrome/"), to[i]);
	}
	
}