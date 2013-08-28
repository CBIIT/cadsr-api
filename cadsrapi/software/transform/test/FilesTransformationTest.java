package test;

import static org.junit.Assert.*;

import transformation.FilesTransformation;
import java.io.File;

import org.junit.Test;

public class FilesTransformationTest {


	
	//File sf = new File("C:/Users/thitisatananta/Documents/TransformerUtility/CDEBrowser/test2.xml"); // source file		
	
	File sf = new File("test.xml");
	File tf = new File("FormDemo.xslt");
	
	
	//String xmlFile = sf.toString();
	
	@Test
	
	public void test()
	
	
	{
		//File sf = new File("C:/Users/thitisatananta/Documents/TransformerUtility/CDEBrowser/test2.xml"); // source file		
		 StringBuilder sb = new StringBuilder();
		
		  sb.append(sf);
		  sb.append(tf);
				
		  String xmlFile = sf.toString();
		  String xsltFile = tf.toString();
		
		//FilesTransformation.transformCdeToCSV(xmlFile);
		 //FilesTransformation.transformFormToCSV(xmlFile);
		 FilesTransformation.transformToCSV (xmlFile, xsltFile);
	}

}
