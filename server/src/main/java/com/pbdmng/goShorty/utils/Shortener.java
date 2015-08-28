package com.pbdmng.goShorty.utils;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.Random;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;
import static java.lang.System.out;


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
			else
				jump++; 
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
		// Handle exceptions
		String shortUrl = null;
		int base64Length;
		Random rnd = new Random();
		
		try{
			String randomStr = longUrl + RandomStringUtils.randomAlphanumeric(rnd.nextInt(CASUAL_CHARS));
			byte[] longByteArray = Base64.encodeBase64URLSafe(randomStr.getBytes("UTF-8"));
			base64Length = longByteArray.length;
			
			
			
			out.print(" random generated \n");;
			
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
	
	
	public static void main(String[] args) {
		
		// https://github.com/GruppoPBDMNG-4/go-shorty
		String longUrl = "https://github.com/GruppoPBDMNG-4/go-shorty";
		int exit = 0;
		Scanner in = new Scanner(System.in);;
		
		while(exit == 0){
			out.println(shorten(longUrl));
			exit = in.nextInt();
		}
		
		in.close();
	}
	
	
	
	
	
	
	/*
	public static void ciu(String[] args) {
		
		Random rnd = new Random();
		final int MAX = 8;
		String url = "";
		for(int i = 0; i < MAX; i++){
			url += rnd.nextInt(9);
		}url = "maiineaa";
		
		try{
			
			byte[] d = Base64.encodeBase64(url.getBytes("UTF-8"));
			
			out.println("originalUrl: " + url + "\n" + "urlLength:   " + url.length() + "\n" + Arrays.toString(d) + "\nshortLength: " + d.length);
			out.println("shortUrl:    " + new String(d));
			
			int prova = MAX / 3;
			if(MAX % 3 != 0) prova++ ; 
			int fin = (prova) * 4;
			out.println("\n" + d.length + " : " + fin);
			
			
			
			char[] ka = (new String(d).toCharArray());
			char[] ca = new char[8];
			int zero = 2;
			//out.println(ka.length);
			
			for(int i = 0; i < 8; i++){
				
				ca[i] = ka[zero];
				zero = (zero + 5) % fin;
			}
			out.println(String.valueOf(ca) + "    ----");
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	*/
	
	
}
