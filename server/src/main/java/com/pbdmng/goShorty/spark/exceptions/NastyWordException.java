package com.pbdmng.goShorty.spark.exceptions;

public class NastyWordException extends RuntimeException{
	
	public NastyWordException(String msg){
		super(msg);
	}
	
	public NastyWordException(){
	}
}