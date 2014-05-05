package gov.nih.nci.ncicb.cadsr.test;

import gov.nih.nci.ncicb.cadsr.form.FormRetrieverImpl;

import javax.ws.rs.core.Response;




import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.*;

import static org.junit.Assert.*;

public class FormRetrievalTest {
	public static String testURL = "http://localhost:8080/formrest/services/formRetrieve?";
	//public static String testURL = "http://localhost:8080/formrest/services/formRetrieve?";

    @Before
    public void setUp() {
        System.out.println("@Before - setUp");
        System.out.println(testURL);
    }
 
    @After
    public void tearDown() {
        System.out.println("@After - tearDown");
    }
    
    @Test
    public void testCreatedBy() {
    	WebClient client = WebClient.create(testURL + "createdBy=JONGS");
		client.type("application/xml").accept("application/xml");
		Response r = client.get();
		
		String renderedDoc = retrieveDoc(r);   
		
		assertTrue(renderedDoc.contains("JONGS"));
	}
    
    @Test
    public void testFormPublicId() {
    	WebClient client = WebClient.create(testURL + "formPublicId=3360022");
		client.type("application/xml").accept("application/xml");
		Response r = client.get();
		
		String renderedDoc = retrieveDoc(r);   
		
		assertTrue(renderedDoc.contains("3360022"));
	}  
    
    @Test
    public void testFormPublicIds() {
    	WebClient client = WebClient.create(testURL + "formPublicId=2392775,3403023");
		client.type("application/xml").accept("application/xml");
		Response r = client.get();
		
		String renderedDoc = retrieveDoc(r);   
		
		assertTrue(renderedDoc.contains("2392775"));
	}    
    
    @Test
    public void testClassification() {
    	WebClient client = WebClient.create(testURL + "classification=Phase");
		client.type("application/xml").accept("application/xml");
		WebClient.getConfig(client).getHttpConduit().getClient().setReceiveTimeout(6000000);
		Response r = client.get();
		
		String renderedDoc = retrieveDoc(r);   
		
		assertTrue(renderedDoc.contains("Phase"));
	}
    
    @Test
    public void testClassifications() {
    	WebClient client = WebClient.create(testURL + "classification=Phase,Therapeutic");
		client.type("application/xml").accept("application/xml");
		WebClient.getConfig(client).getHttpConduit().getClient().setReceiveTimeout(6000000);
		Response r = client.get();
		
		String renderedDoc = retrieveDoc(r);   
		
		assertTrue(renderedDoc.contains("Phase"));
	}    
    
    @Test
    public void testLongName() {
    	WebClient client = WebClient.create(testURL + "formLongName=blood%20gas*");
		client.type("application/xml").accept("application/xml");
		Response r = client.get();
		
		String renderedDoc = retrieveDoc(r);   
		
		assertTrue(renderedDoc.contains("BLOOD GASES"));
	}
    
    @Test
    public void testLongNameAndContext() {
    	WebClient client = WebClient.create(testURL + "formLongName=blood%20gas*&context=NCIP");
		client.type("application/xml").accept("application/xml");
		Response r = client.get();
		
		String renderedDoc = retrieveDoc(r);   
		
		assertTrue(renderedDoc.contains("NCIP"));
	}  
    
    @Test
    public void testContexts() {
    	WebClient client = WebClient.create(testURL + "context=NCIP,CTEP");
		client.type("application/xml").accept("application/xml");
		
		HTTPConduit conduit = (HTTPConduit)WebClient.getConfig(client).getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); 
		httpClientPolicy.setConnectionTimeout(540000); 
		httpClientPolicy.setAllowChunking(false); 
		httpClientPolicy.setReceiveTimeout(540000); 
		conduit.setClient(httpClientPolicy); 
		
		Response r = client.get();
		
		String renderedDoc = retrieveDoc(r);   
		
		assertTrue(renderedDoc.contains("NCIP"));
	}     
    
	private String retrieveDoc(Response r) {
		InputStream is = (InputStream) r.getEntity();
		
		SAXBuilder builder = new SAXBuilder();
		Document jDoc = null;
		try {
			jDoc = builder.build(is);
		} catch (JDOMException e) {
			assertTrue(1==2);
		} catch (IOException e) {
			assertTrue(1==2);
		}
		
		XMLOutputter out = new XMLOutputter();
		out.setFormat(Format.getPrettyFormat());
		String renderedDoc = out.outputString(jDoc);
		return renderedDoc;
	}
    
    @Test
    public void testInvalidFormPublicId() {
    	WebClient client = WebClient.create(testURL + "formPublicId=9876543");
		client.type("application/xml").accept("application/xml");
		Response r = client.get();
		
		String renderedDoc = retrieveDoc(r);
		
		assertTrue(renderedDoc.contains("No Records Found"));
	}
    
    @Test
    public void testInvalidCreatedBy() {
    	WebClient client = WebClient.create(testURL + "createdBy=InvalidJONGS");
		client.type("application/xml").accept("application/xml");
		Response r = client.get();
		
		String renderedDoc = retrieveDoc(r);
		
		assertTrue(renderedDoc.contains("No Records Found"));
	} 
    
    @Test
    public void testInvalidClassification() {
    	WebClient client = WebClient.create(testURL + "classification=InvalidPhase");
		client.type("application/xml").accept("application/xml");
		Response r = client.get();
		
		String renderedDoc = retrieveDoc(r);
		
		assertTrue(renderedDoc.contains("No Records Found"));
	}  
    
    @Test
    public void testInvalidContext() {
    	WebClient client = WebClient.create(testURL + "context=InvalidNCIP");
		client.type("application/xml").accept("application/xml");
		Response r = client.get();
		
		String renderedDoc = retrieveDoc(r);
		
		assertTrue(renderedDoc.contains("No Records Found"));
	}  
    
    @Test
    public void testInvalidLongName() {
    	WebClient client = WebClient.create(testURL + "formLongName=Invalidblood%20gas*");
		client.type("application/xml").accept("application/xml");
		Response r = client.get();
		
		String renderedDoc = retrieveDoc(r);   
		
		assertTrue(renderedDoc.contains("No Records Found"));
	}  
    
    @Test
    public void testEmptyParam() {
    	WebClient client = WebClient.create(testURL);
		client.type("application/xml").accept("application/xml");
		Response r = client.get();
		
		String renderedDoc = retrieveDoc(r);   
		
		assertTrue(renderedDoc.contains("Please supply a valid Parameter"));
	}    

}
