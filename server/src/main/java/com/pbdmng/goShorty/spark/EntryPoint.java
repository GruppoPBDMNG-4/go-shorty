package com.pbdmng.goShorty.spark;

import static spark.SparkBase.externalStaticFileLocation;


public class EntryPoint {
	
	public static void main( String[] args ) {
		externalStaticFileLocation("../client");
        RestService.setUpRoutes();
	}
	
}
