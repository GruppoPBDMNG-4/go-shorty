package com.pbdmng.goShorty.utils.inspector;

import junit.framework.TestCase;


public class DomainInspectorTest extends TestCase {
	
	private String[] nastyUrls = {
			"11.lamarianella.info", 
			"www.11.lamarianella.info",
			"http://11.lamarianella.info", 
			"http://11.lamarianella.info",
			"https://11.lamarianella.info", 
			"https://www.11.lamarianella.info", 
			"github.com", 
			"prova"};
	
	private boolean[] testResult = {true, true, true, true, true, true, false, false, false};
	
	DomainInspector domainInspector;
	
	protected void setUp() throws Exception {
		super.setUp();
		domainInspector = new DomainInspector();
	}
	
	protected void tearDown() throws Exception {
		domainInspector = null;
		super.tearDown();
	}
	
	public void testIsNasty() {
		
		for(int i = 0; i < nastyUrls.length; i++){
		assertTrue("Failed at "+ i +":\n" + nastyUrls[i], 
				domainInspector.isNasty(nastyUrls[i]) == testResult[i]);
		}
	}

}
