package transformation;

import java.io.*;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


import java.io.IOException;

import org.apache.commons.lang.RandomStringUtils;



public class FilesTransformation {

	 
    public static String transformCdeToCSV(String xmlFile)  {
             	
    	   
	       StringBuffer  sb = null;
    	     	 
    	try {
    		
	         File tf = new File("cdebrowser.xslt"); // template file
             
             String path = "/local/content/transform/data/";
             String ext = "txt";
             File dir = new File(path);
             String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
             File rf = new File(dir, name);
             
	         if (tf == null || !tf.exists() || !tf.canRead()) {
	        	 System.out.println("File path incorrect");
	        	 return "";
	         }
    		 
	         long startTime = System.currentTimeMillis();
	         
	           
	          //Obtain a new instance of a TransformerFactory. 
	          TransformerFactory f = TransformerFactory.newInstance();
	          // Process the Source into a Transformer Object...Construct a StreamSource from a File.
	          Transformer t = f.newTransformer(new StreamSource(tf));
	           
	          //Construct a StreamSource from input and output
	          Source s = new StreamSource(xmlFile);
	          Result r = new StreamResult(rf); 
	           
	          //Transform the XML Source to a Result.
	          t.transform(s,r);
	          System.out.println("Tranformation completed ..."); 

	          //convert output file to string	          	       	        	  
			try {
					BufferedReader bf = new BufferedReader(new FileReader(rf));
					sb = new 	StringBuffer();							
				    try {
				    	//String output = "";
						while ((bf.readLine()) != null)
						{
							 sb.append(bf.readLine()); 														
						    System.out.println(bf.readLine());

						}
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}
			    	          
	       
	          long endTime = System.currentTimeMillis();
	          System.out.println("Transformation took " + (endTime - startTime) + " milliseconds"); 
	          System.out.println("Transformation took " + (endTime - startTime)/1000 + " seconds");
    	}
    	
    	 catch (TransformerConfigurationException e) {
	           System.out.println(e.toString()); 
	           e.printStackTrace();
	           
	     } catch (TransformerException e) {
	           System.out.println(e.toString());
	           
	        }
    	 
    	
    	 return sb.toString();
    }
    
    public static String transformFormToCSV(String xmlFile) {
    
    	StringBuffer  sb = null;
    	
    	try {
    		
	         File tf = new File("formbuilder.xslt"); // template file
             String path = "/local/content/transform/data/";
             String ext = "txt";
             File dir = new File(path);
             String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
             File rf = new File(dir, name);
             
	         if (tf == null || !tf.exists() || !tf.canRead()) {
	        	 System.out.println("File path incorrect");
	        	 return "";
	         }
    		 
	         long startTime = System.currentTimeMillis();
	         
	           
	          //Obtain a new instance of a TransformerFactory. 
	          TransformerFactory f = TransformerFactory.newInstance();
	          // Process the Source into a Transformer Object...Construct a StreamSource from a File.
	          Transformer t = f.newTransformer(new StreamSource(tf));
	           
	          //Construct a StreamSource from input and output
	          Source s = new StreamSource(xmlFile);
	          Result r = new StreamResult(rf); 
	           
	          //Transform the XML Source to a Result.
	          t.transform(s,r);
	          System.out.println("Tranformation completed ..."); 
	          
	          //convert output file to string	          	       	        	  
			try {
					BufferedReader bf = new BufferedReader(new FileReader(rf));
					sb = new 	StringBuffer();							
				    try {
				    	
						while ((bf.readLine()) != null)
						{
							 sb.append(bf.readLine()); 														
						    System.out.println(bf.readLine());

						}
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}   
	         
	          long endTime = System.currentTimeMillis();
	          System.out.println("Transformation took " + (endTime - startTime) + " milliseconds"); 
	          System.out.println("Transformation took " + (endTime - startTime)/1000 + " seconds");
    	}
    	
    	 catch (TransformerConfigurationException e) {
	           System.out.println(e.toString()); 
	           
	     } catch (TransformerException e) {
	           System.out.println(e.toString());
	           
	        }
    	
		return sb.toString();    	   	
    }

    public  static String transformToCSV(String xmlFile, String xsltFile){
    	
    	StringBuffer  sb = null;
    	
    	try{
    		
            String path = "/local/content/transform/data/";
            String ext = "txt";
            File dir = new File(path);
            String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
            File rf = new File(dir, name);
   
            long startTime = System.currentTimeMillis();
            
	        //Obtain a new instance of a TransformerFactory. 
	        TransformerFactory f = TransformerFactory.newInstance();
	        // Process the Source into a Transformer Object...Construct a StreamSource from a File.
	        Transformer t = f.newTransformer(new StreamSource(xsltFile));
	           
	        //Construct a StreamSource from input and output
	        Source s = new StreamSource(xmlFile);
	        Result r = new StreamResult(rf); 
	        
	           
	        //Transform the XML Source to a Result.
	        t.transform(s,r);
	        System.out.println("Tranformation completed ..."); 
    		
	        //convert output file to string	          	       	        	  
			try {
					BufferedReader bf = new BufferedReader(new FileReader(rf));
					sb = new 	StringBuffer();							
				    try {
				    	
						while ((bf.readLine()) != null)
						{
							 sb.append(bf.readLine()); 														
						    System.out.println(bf.readLine());

						}
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}   
	         
	          long endTime = System.currentTimeMillis();
	          System.out.println("Transformation took " + (endTime - startTime) + " milliseconds"); 
	          System.out.println("Transformation took " + (endTime - startTime)/1000 + " seconds");
    	}
    	
    	 catch (TransformerConfigurationException e) {
	           System.out.println(e.toString()); 
	           
	     } catch (TransformerException e) {
	           System.out.println(e.toString());
	           
	        }
    	
		return sb.toString();    	   	
    }
}
