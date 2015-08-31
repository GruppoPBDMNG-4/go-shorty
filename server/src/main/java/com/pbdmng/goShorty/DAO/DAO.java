package com.pbdmng.goShorty.DAO;

import com.pbdmng.goShorty.entity.Click;

public interface DAO {
	
	// create
	public ReplyDAO insertUrl(String shortUrl, String longUrl);
	public ReplyDAO insertClick(String shortUrl, Click click);
	
	public boolean isPresent(String shortUrl);
	
	// read
	public ReplyDAO fetchLongUrl(String shortUrl);
	public ReplyDAO fetchClicks(String shortUrl, int from, int to);
	public ReplyDAO fetchKeys(String regEx);
	
	// update
	public ReplyDAO updateUrl(String key);
	
	// delete
	public ReplyDAO deleteUrl(String key);
	
}
