package gov.nih.nci.ncicb.cadsr.test;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.*;
import static org.junit.Assert.*;

public class FormRetrievalTest {

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
    	WebClient client = WebClient.create("http://localhost:8080/formrest/services/formRetrieve?protocol=GOG-0221,GOG-0264");
		client.type("application/xml").accept("application/xml");
		Response r = client.get();
		
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
		
		assertTrue(renderedDoc.contains("GOG-0221"));
	}
    
    @Test
    public void testFailure() {
    	WebClient client = WebClient.create("http://localhost:8080/formrest/services/formRetrieve?formPublicId=9876543");
		client.type("application/xml").accept("application/xml");
		Response r = client.get();
		
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
		
		assertTrue(renderedDoc.contains("No Records Found"));
	}

}
