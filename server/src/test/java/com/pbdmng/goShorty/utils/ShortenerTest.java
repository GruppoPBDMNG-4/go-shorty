package com.pbdmng.goShorty.utils;

import junit.framework.TestCase;


public class ShortenerTest extends TestCase {
	
	private String singleUrl = "http://sparkjava.com/documentation.html#request";
	private String[] singleUrlResults = {"SFrY3o0a", 
										 "ZbYaYLca", 
										 "l92YrNDa",
										 "lb9bFYoa",
										 "GllZSJva",
										 "cdddbaLa",
										 "aJibvF3a",
										 "vc1d9YNa"};
	
	private String[] urls = {"prova", "2", "https://angular.io/", "-"};
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	public void testShorten(){
		for(int i = 0; i < singleUrlResults.length; i++)
			assertTrue("Test failed at " + i, singleUrlResults[i].equals
					(Shortener.shorten(singleUrl)));
	}
	
	public void testShortenLength(){
		for(int i = 0; i < urls.length; i++)
			assertTrue("Test failed at " + i, (Shortener.shorten(urls[i])).length() == 8 );
	}
	
}
