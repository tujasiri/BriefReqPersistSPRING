package com.sample;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;


public class DownloadBrief {
	
	//public static int downloadBrief(String str) throws Throwable{
	public static int downloadBrief() throws Throwable{
		
		String uri = "http://localhost/docs/1993435.pdf";
		URL url = new URL(uri);
		File destination = new File("/tmp/test_files/testbrief.pdf");
		//File destination = new File("/Users/tajiriujasiri/tmp/testbrief.pdf");
		
		//checkConnection
	
		try{
			
			FileUtils.copyURLToFile(url, destination);
			System.out.println("Downloading...");
		}
		catch(Exception e){
			
			System.err.println(e.toString() +"caught while running downloadBrief().");
			return(1);
			
		}
			return(0);
		
	}//end downloadBrief
	

	
}
