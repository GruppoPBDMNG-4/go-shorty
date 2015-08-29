package com.pbdmng.goShorty.spark.exceptions;

public class CustomUrlPresentException extends RuntimeException{
	
	public CustomUrlPresentException(String msg){
		super(msg);
	}
	
	public CustomUrlPresentException(){
	}
}