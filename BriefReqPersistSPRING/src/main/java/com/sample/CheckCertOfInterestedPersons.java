package com.sample;

import java.io.FileOutputStream;
import java.io.PrintWriter;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;


public class CheckCertOfInterestedPersons{


	//public static int checkCertOfInterestedPersons(String str) throws Throwable{
	public static int checkCert(String str) throws Throwable{

	String PDFDOC = "/tmp/testbrief.pdf";

  	System.out.println("STARTED!");
			
	Document document = new Document();
		

       	PdfReader reader = new PdfReader(PDFDOC);
       	PdfReaderContentParser parser = new PdfReaderContentParser(reader);
       	PrintWriter out = new PrintWriter(new FileOutputStream("xxx.txt"));
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
       	     else{
       		     	return(99);
       	     }
       	 }

       	 out.flush();
       	 out.close();
       	 reader.close();

		
		
	return(1);

	}//end method


}//end class
