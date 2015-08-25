package com.pbdmng.goShorty.DAO;

import java.time.LocalDate;
import java.util.List;

import com.pbdmng.goShorty.entity.Click;

public interface DAO {
	
	// inserimento
	public ReplyDAO insertUrl(String shortUrl, String longUrl);
	public ReplyDAO insertClick(String shortUrl, Click click);
	// aggiornamento
	public ReplyDAO updateUrl();
	public ReplyDAO deleteUrl();
	// verifica
	public boolean isPresent(String shortUrl);
	// prelevamento dati
	public ReplyDAO fetchLongUrl(String shortUrl);
	public ReplyDAO fetchKeys();
	public ReplyDAO fetchClicks(String shortUrl);
	

}
