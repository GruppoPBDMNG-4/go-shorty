package com.pbdmng.goShorty.spark;


import com.pbdmng.goShorty.DAO.*;
import com.pbdmng.goShorty.spark.exceptions.*;
import com.google.gson.JsonObject;

import static java.lang.System.err;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.options;
import static spark.Spark.before;

/**
 * It provides shortening, redirection and statistical services
 * as RESTful web services.
 * @author chris
 */
public class RestService {
	
	private static Service service = new Service(new RedisDAO());
	private static final String REST = "/rest";
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
	
	/**
	 * Route listening to "/rest/go-shorty" endpoint.
	 * Creates a new shortUrl or gives an error message
	 * Uses the POST method as a create operation
	 * 
	 */
	private static void setUpShorteningRoute(){
		
		post(REST + CREATE_SHORTY, (request, response) ->{
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
	
	/**
	 * Route listening to "/rest/" endpoint.
	 * Redirects to the respective longUrl if it exists
	 * Uses the GET method as a read operation 
	 * 
	 */
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
	
	/**
	 * Route listening to "/:shorty/stats/:shorty" endpoint.
	 * Gets the stats 
	 * Uses the GET method as a read operation
	 * 
	 */
	private static void setUpStatsRoute(){
		
		get(REST  + READ_STATS, (request, response) -> {
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
		get(REST  + "/404.html", (request, response) -> {
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
