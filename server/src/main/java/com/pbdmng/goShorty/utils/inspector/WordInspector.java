package com.pbdmng.goShorty.utils.inspector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.Normalizer;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * Checks if a word is a nasty word, 
 * by searching from a JSON file with nasty words.
 * 
 * @author paolobi
 */
public class WordInspector {
	
	JsonObject badJson;
	private final static String RELATIVE_PATH = 
			"/src/main/java/com/pbdmng/goShorty/utils/inspector/badWords/badwords.json";
			
	public WordInspector(){
		
		Gson gson = new Gson();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir").replace("/target", "") + RELATIVE_PATH));
			badJson = gson.fromJson(br, JsonObject.class);
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	// Word has to be already normalized and without unsafe characters
	public boolean isNasty(String word){
		
		boolean nasty = false;
		String aNastyWord ;
		for (Map.Entry<String, JsonElement> entry : badJson.entrySet()){
			
			JsonArray jarray = entry.getValue().getAsJsonArray();
			for (JsonElement j : jarray){
				aNastyWord = j.toString();
				aNastyWord = Normalizer.normalize(aNastyWord, Normalizer.Form.NFD)
							.replaceAll("[^0-9.a-zA-Z-_]", "");
				
				if(word.toLowerCase().contains(aNastyWord.toLowerCase())){
					nasty = true; 
					break;
				}	
			}
		} 
		
		return nasty;
	}

}
