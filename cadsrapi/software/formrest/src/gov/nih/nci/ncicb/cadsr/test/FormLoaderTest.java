package gov.nih.nci.ncicb.cadsr.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.jaxrs.client.WebClient;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.*;
import static org.junit.Assert.*;

public class FormLoaderTest {

    @Before
    public void setUp() {
        System.out.println("@Before - setUp");
    }
 
    @After
    public void tearDown() {
        System.out.println("@After - tearDown");
    }
 
    @Test
    public void testSuccess() {
    	WebClient client = WebClient.create("http://localhost:8080/formrest/services/formLoad/");
    	
		//String userName = "FORMBUILDER";
		//String password = "FORMBUILDER";
		String userName = "SBREXT";
		String password = "jjuser";
		String base64encodedUsernameAndPassword = new String(Base64.encodeBase64((userName + ":" + password).getBytes()));
		client.header("Authorization", "Basic "	+ base64encodedUsernameAndPassword);

		String xmlFileString = readFile("load_forms-5.xml");
		//String xmlFileString = readFile("forms-malformed.xml");
		//String xmlFileString = readFile("invalid-publicid.xml");
		//String xmlFileString = readFile("invalid-context.xml");
				
		client.type("application/xml").accept("application/xml");
		Response r = client.put(xmlFileString.toString());
		if (r.getStatus() == Status.UNAUTHORIZED.getStatusCode()) {
			String html = "Unauthorized access";
			System.out.println(html);
		} else {
			InputStream is = (InputStream) r.getEntity();

			SAXBuilder builder = new SAXBuilder();
			Document jDoc = null;
			try {
				jDoc = builder.build(is);
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			XMLOutputter out = new XMLOutputter();
			out.setFormat(Format.getPrettyFormat());
			String renderedDoc = out.outputString(jDoc);
			
			assertTrue(renderedDoc.contains("3643954"));

			}
	}
    
    private static String readFile(String fileName) {
		String filePath = "/local/content/formloader/data/";
		File xmlFile = new File(filePath + fileName);
		BufferedReader br = null;
		StringBuffer sBuffer = new StringBuffer();
		try {
			
			FileReader fr = new FileReader(xmlFile);
			br = new BufferedReader(fr);

			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				//System.out.println(sCurrentLine);
				sBuffer.append(sCurrentLine + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		  catch (IOException e1) {
			e1.printStackTrace();
		}
		  finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return sBuffer.toString();
	}

}

