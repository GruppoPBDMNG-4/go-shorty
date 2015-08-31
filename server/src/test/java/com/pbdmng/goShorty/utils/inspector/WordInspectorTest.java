package com.pbdmng.goShorty.utils.inspector;

import java.text.Normalizer;
import junit.framework.TestCase;

public class WordInspectorTest extends TestCase {


	private String[] nastyWords = { "buzna", 
									"bosserov", 
									"bimbo", 
									"fuc!k", 
									"fu+*ck", 
									"fùçk", 
									"fu ck", 
									"f==uck", 
									"kacsuĉulo", 
									"Fiesta de salchichas", 
									"Cabrón", 
									"bylsiä", 
									"allumée", 
									"chut ke baal", 
									"csöcs", 
									"allupato", 
									"wippen", 
									"sotror", 
									"gównoprawda", 
									"filhodaputa", 
									"promudobladsksyapizdoproebina", 
									"discofitta" ,
									"Qovpatlh",
									"kaltaklarda",
									"ginopino"
									};
	
	private boolean[] testResult = {true, true, true, true, true, true, true, true, true, 
									true, true, true, true, true, true, true, true, true, 
									true, true, true, true, true, true, false};
	WordInspector wordInspector;
	
	protected void setUp() throws Exception {
		
		wordInspector = new WordInspector();
		for(int i = 0; i < nastyWords.length; i++){
			nastyWords[i] = Normalizer.normalize(nastyWords[i], Normalizer.Form.NFD).
							replaceAll("[^0-9.a-zA-Z-_]", "");
		}
		super.setUp();
	}
	
	protected void tearDown() throws Exception {
		wordInspector = null;
		super.tearDown();
	}
	
	public void testIsBadWord() {
		
		for(int i = 0; i < nastyWords.length; i++){
		assertTrue("Failed at "+ i +" : " + nastyWords[i] + " is not " + testResult[i], 
				wordInspector.isNasty(nastyWords[i]) == testResult[i]);	
		}
		
	}


}
