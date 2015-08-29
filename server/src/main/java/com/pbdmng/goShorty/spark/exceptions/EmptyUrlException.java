package com.pbdmng.goShorty.spark.exceptions;

public class EmptyUrlException extends RuntimeException{
	
	public EmptyUrlException(String msg){
		super(msg);
	}
	
	public EmptyUrlException(){
	}
}
