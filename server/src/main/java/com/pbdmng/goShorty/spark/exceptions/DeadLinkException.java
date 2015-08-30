package com.pbdmng.goShorty.spark.exceptions;

public class DeadLinkException extends RuntimeException{
	
	public DeadLinkException(String msg){
		super(msg);
	}
	
	public DeadLinkException(){
	}
}