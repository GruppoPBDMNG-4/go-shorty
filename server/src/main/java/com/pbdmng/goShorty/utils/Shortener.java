package com.pbdmng.goShorty.utils;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;
import static java.lang.System.out;

/**
 * Provides an eight letter string from a given URL.
 * It converts the string into base64 format and from 
 * there takes characters at regular intervals. If 
 * it is called again with the same longUrl it changes 
 * the interval for a maximum of eight times. After that, 
 * it takes random characters.
 *   
 * @author chris
 */
public class Shortener {
	
	private static String lastUrl = "";
	private static int jump = 3; // attempts
	private static final int MAX_ATTEMPTS = 10; // max 8 attempts 
	private final static int SHORT_LENGTH = 8;
	private final static int CASUAL_CHARS = 13;
	
	
	public static String shorten(String longUrl){
		
		int base64Length;
		int sel = 0;
		String shortUrl = null;
		
		if(longUrl.equals(lastUrl)) 
			if(jump > MAX_ATTEMPTS)
				return randomShorten(longUrl);
			else jump++; 
		else 
			jump = 3;
		
		try{
			
			byte[] longByteArray = Base64.encodeBase64URLSafe(longUrl.getBytes("UTF-8"));
			base64Length = longByteArray.length;
			
			char[] base64Array = ( new String(longByteArray).toCharArray() );
			char[] shortArray = new char[8];
			
			for(int i = 0; i < SHORT_LENGTH; i ++){
				shortArray[i] = base64Array[sel];
				sel = (sel + jump) % base64Length;
			}
			
			shortUrl = new StringBuilder(String.valueOf(shortArray)).reverse().toString();
			
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		lastUrl = longUrl;
		return shortUrl;
	}

	
	private static String randomShorten(String longUrl){
		
		String shortUrl = null;
		int base64Length;
		Random rnd = new Random();
		
		try{
			String randomStr = longUrl + RandomStringUtils.randomAlphanumeric(rnd.nextInt(CASUAL_CHARS));
			byte[] longByteArray = Base64.encodeBase64URLSafe(randomStr.getBytes("UTF-8"));
			base64Length = longByteArray.length;
			
			out.print(" random generated \n");
			char[] base64Array = ( new String(longByteArray).toCharArray() );
			char[] shortArray = new char[8];
			
			for(int i = 0; i < SHORT_LENGTH; i ++){
				shortArray[i] = base64Array[rnd.nextInt(base64Length - 1)];
			}
			
			// reverse
			shortUrl = new StringBuilder(String.valueOf(shortArray)).reverse().toString();
			
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return shortUrl;
	}
	
	public static void setLastUrl(String url){
		lastUrl = url;
	}
	
}
