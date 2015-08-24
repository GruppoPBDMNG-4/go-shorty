package com.pbdmng.goShorty.DAO;

import java.util.List;

public interface DAO {
	
	
	public DAOObj insertUrl(String longUrl, String shortUrl);
	public DAOObj updateUrl();
	public DAOObj deleteUrl();
	public boolean isPresent(String shortUrl);
	
	
	public String getUrl(String shortUrl);
	public DAOObj setCliks(String ...param);
	
	// da testare
	public List<String> getClicks();
	

}
