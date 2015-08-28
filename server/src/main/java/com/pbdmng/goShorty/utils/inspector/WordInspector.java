package com.pbdmng.goShorty.utils.inspector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

import com.google.gson.JsonObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class WordInspector {
	
	private final static String RELATIVE_PATH = "/src/main/java/com/pbdmng/goShorty/utils/inspector/badWords/badwords.json";
	JsonObject badJson;		
	
	public WordInspector(){
		
		Gson gson = new Gson();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + RELATIVE_PATH));
			badJson = gson.fromJson(br, JsonObject.class);
		}
		catch (Exception e){
			e.printStackTrace();;
		}
	}
	
	
	public boolean isNasty(String word){
		
		boolean nasty = false;
		String check ;
		
		for (Map.Entry<String, JsonElement> entry : badJson.entrySet()){
			
			JsonArray jarray = entry.getValue().getAsJsonArray();
			
			for (JsonElement j : jarray){
				check = j.toString().replace("\"", "");
				if(word.equalsIgnoreCase(check)) 
					nasty = true; break;
			}
		} 
		
		return nasty;
		
	}
	

}
