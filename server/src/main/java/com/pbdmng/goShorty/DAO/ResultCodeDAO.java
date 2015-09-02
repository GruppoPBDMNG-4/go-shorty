package com.pbdmng.goShorty.DAO;

/**
 * Possible return values
 * @author paolobi
 */
public enum ResultCodeDAO {
	
	NOT_PRESENT(-2),
	NOT_INSERTED (-1),
	DUPLICATE(0),
	INSERTED(1),
	OK(2);
	
	
	private long code;
	
	private ResultCodeDAO(long v){
		code = v;
	}
	
	
	public long getCode(){
		return code;
	}
	
	public void setCode(long v){
		code = v;
	}

}