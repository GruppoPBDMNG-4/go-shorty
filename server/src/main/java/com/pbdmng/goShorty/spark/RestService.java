package com.pbdmng.goShorty.spark;


import com.pbdmng.goShorty.DAO.*;
import com.pbdmng.goShorty.spark.exceptions.*;
import com.google.gson.JsonObject;

import static java.lang.System.err;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.options;
import static spark.Spark.before;


public class RestService {
	
	private static Service service = new Service(new RedisDAO());
	private static final String REST_API = "/rest";
	private static final String CREATE_SHORTY = "/go-shorty";
	private static final String REDIRECT = "/:shorty";
	private static final String READ_STATS = "/stats/:shorty";
	private static final String ERROR = "error";
	
	public static void setUpRoutes(){
		setUpShorteningRoute();
		setUpRedirectRoute(); 
		setUpStatsRoute();
    	setPageNotFound();
    	setUpOptions();
	}
	
	private static void setUpShorteningRoute(){
		
		post(REST_API + CREATE_SHORTY, (request, response) ->{
			String jsonResponse = null;
			JsonObject jsonError = new JsonObject();
			
			try{
				jsonResponse = service.shortenUrl(request.body());
				
			}catch(EmptyUrlException e){
				err.println(e.getMessage());
				jsonError.addProperty(ERROR, "URL not provided");
				jsonResponse = jsonError.toString();
				response.status(500);
			}catch(NastyUrlException e){
				err.println(e.getMessage());
				jsonError.addProperty(ERROR, "This URL is forbidden");
				jsonResponse = jsonError.toString();
				response.status(500);
			}catch(TooManyAttemptsException e){
				err.println(e.getMessage());
				jsonError.addProperty(ERROR, "Press it again. I dare you.");
				jsonResponse = jsonError.toString();
				response.status(500);
			}catch(NastyWordException e){
				err.println(e.getMessage());
				jsonError.addProperty(ERROR, "You're a bad boy");
				jsonResponse = jsonError.toString();
				response.status(500);
			}catch(CustomUrlPresentException e){
				err.println(e.getMessage());
				jsonError.addProperty(ERROR, "Custom URL already taken");
				jsonResponse = jsonError.toString();
				response.status(500);
			}
			
			return jsonResponse;
		});
		
	}
	
	private static void setUpRedirectRoute(){
		
		get(REDIRECT, (request, response) -> {
			
			String longUrl;
			try{
				longUrl = service.redirectTo(request.params(":shorty"), request.ip(), request.userAgent());
			}catch(DeadLinkException e){
				err.println(e.getMessage());
				longUrl = "/404.html";
			}
			response.redirect(longUrl);
			
			return null;
		});
		
	}
	
	private static void setUpStatsRoute(){
		
		get(REST_API + READ_STATS, (request, response) -> {
			String jsonResponse;
			JsonObject jsonError = new JsonObject();
			try{
				jsonResponse = service.urlStatistics(request.params(":shorty"));
			}catch(NonexistentShortUrlException e){
				err.println(e.getMessage());
				jsonError.addProperty(ERROR, "The short URL inserted does not exist");
				jsonResponse = jsonError.toString();
				response.status(500);
			}
			return jsonResponse;
		});
		
	}
	
	private static void setPageNotFound() {
		get(REST_API + "/404.html", (request, response) -> {
			response.redirect("/app/404.html");
			return null;
		});
	}
	
	private static void setUpOptions() {
		
    	options("/*", (request,response)->{

    	    String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
    	    if (accessControlRequestHeaders != null)
    	        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);

    	    String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
    	    if(accessControlRequestMethod != null)
    	    	response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
    	    
    	    return "OK";
    	});

    	before((request,response)->{
    	    response.header("Access-Control-Allow-Origin", "*");
    	});
	}
	
	
}
