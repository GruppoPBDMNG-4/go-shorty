package com.pbdmng.goShorty.spark.exceptions;

public class NonexistentShortUrlException extends RuntimeException{
	
	public NonexistentShortUrlException(String msg){
		super(msg);
	}
	
	public NonexistentShortUrlException(){
	}
}
