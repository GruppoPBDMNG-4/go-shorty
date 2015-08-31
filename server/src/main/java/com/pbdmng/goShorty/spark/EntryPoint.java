package com.pbdmng.goShorty.spark;

import static spark.SparkBase.externalStaticFileLocation;
import com.pbdmng.goShorty.populator.RandomPopulator;

public class EntryPoint {
	
	public static void main( String[] args ) {
		RandomPopulator.populate();
		externalStaticFileLocation("../client");
        RestService.setUpRoutes();
	}
	
}