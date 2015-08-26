package com.pbdmng.goShorty.DAO;

import com.pbdmng.goShorty.entity.Click;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ReplyDAO {
	
	
		//final static String JEDIS_NIL_STRING = "(nil)";
		private String longUrl;
		private Set<String> keyset = new HashSet<String>();
		private List<Click> clickList = new ArrayList<Click>();
		private ResultCodeDAO resultCode ;
		
		
		public void setResultCode(ResultCodeDAO code){
			this.resultCode = code;
		}
		
		public void setLongUrl(String longUrl){
			this.longUrl = longUrl;
		}
		
		public void setKeySet(Set<String> keyset){
			this.keyset = keyset;
		}
		
		
		public ResultCodeDAO getResultCode(){
			return resultCode;
		}
		
		public String getLongUrl(){
			return longUrl;
		}
		
		
		
		public Set<String> getKeySet(){
			return keyset;
		}
		
		public void setClickList(List<Click> clickList){
			this.clickList = clickList;
		}
		
		public List<Click> getClickList(){
			return clickList;
		}
		
}
