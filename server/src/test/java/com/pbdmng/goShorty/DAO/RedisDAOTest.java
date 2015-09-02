package com.pbdmng.goShorty.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.pbdmng.goShorty.entity.Click;

import junit.framework.TestCase;

public class RedisDAOTest extends TestCase {
	
	DAO dao = new RedisDAO();
	
	private String[] shortUrl = {"SFrY3o0a", "2l4c3o0a", "angular", "X5uZ3o0a", "dockerhub"};
	
	private String[] longUrl = {"http://sparkjava.com/documentation.html#request",
								"http://vertx.io/docs/",
								"https://angular.io/",
								"http://pbdmng.datatoknowledge.it/case_study.html",
								"https://hub.docker.com/"};
	
	private Click[] clickArray = {new Click("151.25.78.65",   "Chrome/"),
								  new Click("43.12.223.34",   "Safari/"),
								  new Click("31.72.213.143",  "Opera/"),
								  new Click("151.43.135.71",  "Safari/"),
								  new Click("58.56.98.75",    "Firefox/")};
	
	private boolean[] alreadyPresent = {false, false, false, false, false,};
	
	protected void setUp() throws Exception {
		super.setUp();
		
		for(int i = 0; i < shortUrl.length; i++){
			if(dao.isPresent(shortUrl[i])) 
				alreadyPresent[i] = true;
			dao.insertUrl(shortUrl[i], longUrl[i]);
		}
		
		for(int i = 0; i < shortUrl.length; i++)
			for(int j = 0; j < clickArray.length; j++)
				dao.insertClick(shortUrl[i], clickArray[j]);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		for (int i = 0; i < shortUrl.length; i++){
			if(!alreadyPresent[i]){
				dao.deleteUrl(shortUrl[i]);
				dao.deleteUrl(shortUrl[i] + ":clicks");
			}
		}
	}
	
	
	
	public void testInsertUrl(){
		ReplyDAO reply;
		for (int i = 0; i < shortUrl.length; i++){
			reply = dao.insertUrl(shortUrl[i], longUrl[i]);
			assertEquals("", reply.getResultCode().getCode(), ResultCodeDAO.INSERTED.getCode());
		}
	}
	
	public void testInsertClick(){
		ReplyDAO reply;
		for(int i = 0; i < shortUrl.length; i++){
			for(int j = 0; j < clickArray.length; j++){
				reply = dao.insertClick(shortUrl[i], clickArray[j]);
				assertEquals("", reply.getResultCode().getCode(), ResultCodeDAO.OK.getCode());
			}
		}
	}
	
	
	
	public void testIsPresent(){
		for(int i = 0; i < shortUrl.length; i++)
			assertTrue("ShortUrl n." + i + " is not present", dao.isPresent(shortUrl[i]));
	}
	
	
	
	public void testFetchLongUrl(){
		for(int i = 0; i < shortUrl.length; i++){
			if(!alreadyPresent[i])
				assertEquals("Test failed at " + i, dao.fetchLongUrl(shortUrl[i]).getLongUrl(), longUrl[i] );
		}
	} 
	
	public void testFetchClicks(){
		List<Click> clickList = new ArrayList<Click>();
		for(int i = 0; i < shortUrl.length; i++){
			clickList = dao.fetchClicks(shortUrl[i], 0, -1).getClickList();
			assertTrue("", clickList.size() >= clickArray.length );
		}
	}
	
	public void testFetchKeys(){
		Set<String> fetchedKeys = dao.fetchKeys("*").getKeySet(); 
		assertTrue("", fetchedKeys.size() >= shortUrl.length);
	}
	
	
}
