package com.pbdmng.goShorty.DAO;

public enum DAOObjCode {
	
	NOT_INSERTED (-1),
	INSERTED(1),
	DUPLICATE(2);
	
	
	private int code;
	
	private DAOObjCode(int v){
		code = v;
	}
	

	/*
	public int getCode(){
		return code;
	}*/

}
