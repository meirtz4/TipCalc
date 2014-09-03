package com.fatboyd.tipcalc;

public class Utils {

	private final String ADMOB_ID = "ca-app-pub-xxxxxxxxxxxxxx";
	private static Utils utils;
	
	private Utils(){};
	
	public static Utils getUtils(){
		if (utils==null)
			utils = new Utils();
		return utils;
	}
	
	
	public String getADMOB_ID(){
		return ADMOB_ID;
	}
}
