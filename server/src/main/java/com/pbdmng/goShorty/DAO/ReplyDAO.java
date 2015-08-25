package com.pbdmng.goShorty.DAO;

import com.pbdmng.goShorty.entity.Click;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ReplyDAO {
	
	
		final static String JEDIS_NIL_STRING = "(nil)";
		private String value;
		private Set<String> setKeys = new HashSet<String>();
		private List<Click> listClicks = new ArrayList<Click>();
		
		private ResultCodeDAO resultCode ;
		
		
		public void setResultCode(ResultCodeDAO code){
			this.resultCode = code;
		}
		
		public ResultCodeDAO getResultCode(){
			return resultCode;
		}
		
		public void setLongUrl(String v){
			this.value = v;
		}
		
		public String getLongUrl(){
			return value;
		}
		
		public void setKeySet(Set<String> lk){
			this.setKeys = lk;
		}
		
		public Set<String> getKeySet(){
			return setKeys;
		}
		
		public void setListClicks(List<Click> lc){
			this.listClicks = lc;
		}
		
		public List<Click> getListClicks(){
			return listClicks;
		}
		
		
		
		
}
