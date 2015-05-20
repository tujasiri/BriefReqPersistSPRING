package com.sample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;


public class BriefVerify {
/*
	//@Test
	public static void main(String[] args) throws Throwable {

		BriefVerify bv = new BriefVerify();
		bv.sayHello();
		
	}
*/
	
	//public String sayHello() throws Throwable{
	public static void sayHello(String str) throws Throwable{
		
		System.out.println("Hell000000000000000000000000000!...");
		
		try{
		//System.out.println("Hello..."+wrld);
		System.out.println("Hello...");
		} catch(Exception ex){
			System.out.println("sayHello exception==>"+ex);
		}
		//return "Hello";
		
	
	}
	
	public boolean isScannedDocument(String str) throws Throwable{
		return true;
	}
	
	//public static int downloadBrief() throws Throwable{
	public int downloadBrief(String str) throws Throwable{
			System.out.println("IN Downloading METHOD...");
		
		String uri = "http://localhost/docs/1993435.pdf";
		//String uri = "http://localhost/docs/1993435xxx.pdf";
		URL url = new URL(uri);
		File destination = new File("/tmp/test_files2/testbrief.pdf");
		//File destination = new File("testbrief.pdf");
		
		//checkConnection
		
		try{
			
			FileUtils.copyURLToFile(url, destination);
			System.out.println("Downloading...");
		}
		catch(Exception e){
			
			System.err.println(e.toString() +" caught while running downloadBrief().");
			return(99);
			
		}
			return(0);
		
	}//end downloadBrief
	
	public int downloadBrief2(String str) throws Throwable{
		
		String uri = "http://localhost/docs/1993435.pdf";
		URL url = new URL(uri);
		File destination = new File("/tmp/test_files/testbrief.pdf");
		//File destination = new File("testbrief.pdf");
		
		//checkConnection
		
		try{
			
			FileUtils.copyURLToFile(url, destination);
			System.out.println("Downloading 2...");
		}
		catch(Exception e){
			
			System.err.println(e.toString() +"caught while running downloadBrief().");
			return(1);
			
		}
			return(0);
		
	}//end downloadBrief2
	
	public static int checkCaption(String str) throws Throwable{
		Document document = new Document();
		
		
		return(0);
	}
	
	public int checkCertOfInterestedPersons(String str) throws Throwable{
		String PDFDOC = "/tmp/test_files2/testbrief.pdf";
		
		Document document = new Document();
		
		System.out.println("STARTEDxxxxxy=========>");
		boolean x=true;
		
		/*
		if(x)
			return(99);
		*/
		

        PdfReader reader = new PdfReader(PDFDOC);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        PrintWriter out = new PrintWriter(new FileOutputStream("/tmp/test_files2/xxx.txt"));
        TextExtractionStrategy strategy;
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
            out.println(strategy.getResultantText());
            System.out.println(strategy.getResultantText());
            
            String pageText = strategy.getResultantText();
         
            if(pageText.contains("CERTIFICATE OF INTERESTED PERSONS")){
            	System.out.println("found it");
            	return(0);
            	
            }
         /*
            else{
   		     	return(99);
   	     }
   	     */
            
        }
        out.flush();
        out.close();
        reader.close();

		return(1);
	}
	
	public static int checkStatementRegardingOA(String str) throws Throwable{
		return(0);
	}
	
	
} 
