package com.gruppo4.ezstocks;

public abstract class urlConstructor {
	
	// URL Constructor for Polygon.io
	
	
	// Settings:
	private static String urlBase = "https://api.polygon.io/";
	private static String key = "3EeuHppKTp5zgsIDr1KI2BtsfdveDj7G";
	private static String multipler = "1";
	private static String timespan = "day";
	private static int limit = 1000;
	
	private static String i = "/";
	
	// Ticker Details
	public static String generateUrl(String version, String type, String ticker) {
		String generatedUrl = "" + urlBase + version + i  + type + "/tickers/" + ticker + "?apiKey=" + key;
	    return generatedUrl;
	}
	
	// Ticker Price Data
	public static String generateUrl(String version, String type,  String ticker, String from, String to) {
		String generatedUrl = "" + urlBase + version + i  + type + "/ticker/" + ticker + "/range/" + multipler + i + timespan + i + from + i + to + "?adjusted=true&sort=desc&limit=" + limit + "&apiKey=" + key;
	    return generatedUrl; 
	}
}


// URL Examples
// Ticker Details V3: https://api.polygon.io/v3/reference/tickers/AAPL?apiKey=3EeuHppKTp5zgsIDr1KI2BtsfdveDj7G
// Aggregates (Bars): https://api.polygon.io/v2/aggs/ticker/AAPL/range/1/day/2021-07-22/2021-07-22?adjusted=true&sort=asc&limit=120&apiKey=3EeuHppKTp5zgsIDr1KI2BtsfdveDj7G