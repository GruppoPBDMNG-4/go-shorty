package com.pbdmng.goShorty.spark.exceptions;

public class TooManyAttemptsException extends RuntimeException{
	
	public TooManyAttemptsException(String msg){
		super(msg);
	}
	
	public TooManyAttemptsException(){
	}
}
