package com.pbdmng.goShorty.DAO;

import java.util.List;

public interface DAO {
	
	
	DAOObj insertUrl(String longUrl, String shortUrl, String Date);
	DAOObj updateUrl();
	DAOObj deleteUrl();
	
	// da testare
	List<String> getStatistics();
	

}
