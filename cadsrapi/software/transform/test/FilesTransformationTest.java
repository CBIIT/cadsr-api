/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-api/LICENSE.txt for details.
 */

package test;

import static org.junit.Assert.*;

import transformation.FilesTransformation;
import java.io.File;

import org.junit.Test;

public class FilesTransformationTest {

	
	File sf = new File("test.xml");
	File tf = new File("FormDemo.xslt");
	
	
	@Test
	
	public void test()
	
	
	{
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
