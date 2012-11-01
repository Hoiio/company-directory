package com.hoiio.api.extensions.persistence;

public class Configuration {
	
	// Hoiio API credentials. Get from http://developer.hoiio.com
	public static final String appId 		= "";
	public static final String accessToken 	= "";
	
	// Number of digit each extension has
	public static final int numOfDigits = 3;
	
	// Your appspot URL
	public static final String appSpotUrl = "http://your-appspot-identifier.appspot.com";

	// Default messages
	public static final String message = "Welcome Hoiio Developer, kindly key in the extension number ";
	public static final String messageWhenForwarding = "Please wait while we connect your call.";
	
	public static String getAppid() {
		return appId;
	}
	public static String getAccesstoken() {
		return accessToken;
	}
	public static String getMessage() {
		return message;
	}
	
	public static String getAppSpotUrl() {
		return appSpotUrl;
	}
	
	public static String getMessageWhenForwarding(){
		return messageWhenForwarding;
	}
	
	public static int getNumOfDigits(){
		return numOfDigits;
	}
	
}
