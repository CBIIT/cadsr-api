package gov.nih.nci.ncicb.cadsr.test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.junit.*;

import static org.junit.Assert.*;

public class CDERetrievalTest {
	public static String testURL = "http://localhost:8080/cderest/rest/services/getDataElement?";
	//public static String testURL = "http://localhost:8080/cderest/rest/services/getDataElement?";

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
    public void testPublicId() {
    	Client client = Client.create();
		WebResource webResource = client.resource(testURL + "publicId=2183222");
 		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
 
		String output = retrieveOutput(response);  
		
		assertTrue(output.contains("2183222"));
	}

	private String retrieveOutput(ClientResponse response) {
		if (response.getStatus() != 200) {
		   assertTrue(1==2);
		}

		String output = response.getEntity(String.class);
		return output;
	}
    
    @Test
    public void testName() {
    	Client client = Client.create();
		WebResource webResource = client.resource( testURL + "name=Bone%20Pain");
 
		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
 
		String output = retrieveOutput(response);  
		
		assertTrue(output.contains("Bone Pain"));
	}    
    
    @Test
    public void testWorkFlowStatus() {
    	Client client = Client.create();
		WebResource webResource = client.resource( testURL + "workflowStatus=DRAFT%20MOD");
 
		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
 
		String output = retrieveOutput(response);  
		
		assertTrue(output.contains("DRAFT MOD"));
	}  
    
    @Test
    public void testRegistrationStatus() {
    	Client client = Client.create();
		WebResource webResource = client.resource( testURL + "registrationStatus=Standard");
 
		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
 
		String output = retrieveOutput(response);  
		
		assertTrue(output.contains("Standard"));
	}  
    
    @Test
    public void testClassificationItem() {
    	Client client = Client.create();
		WebResource webResource = client.resource( testURL + "classificationItem=Phase%20II");
 
		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
 
		String output = retrieveOutput(response);  
		
		assertTrue(output.contains("Phase II"));
	}    
    
    @Test
    public void testClassificationItems() {
    	Client client = Client.create();
		WebResource webResource = client.resource( testURL + "classificationItem=Phase%20II,6665-TA");
 
		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
 
		String output = retrieveOutput(response);  
		
		assertTrue(output.contains("Phase II"));
	}
    
    @Test
    public void testClassification() {
    	Client client = Client.create();
		WebResource webResource = client.resource( testURL + "classification=pHASE");
 
		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
 
		String output = retrieveOutput(response);  
		
		assertTrue(output.contains("Phase"));
	}  
    
    @Test
    public void testContext() {
    	Client client = Client.create();
		WebResource webResource = client.resource( testURL + "context=NCIP");
 
		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
 
		String output = retrieveOutput(response);  
		
		assertTrue(output.contains("NCIP"));
	}  
    
    @Test
    public void testContexts() {
    	Client client = Client.create();
		WebResource webResource = client.resource( testURL + "context=NCIP,CTEP");
 
		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
 
		String output = retrieveOutput(response);  
		
		assertTrue(output.contains("NCIP"));
	}    
    
    @Test
    public void testInvalidContext() {
    	Client client = Client.create();
		WebResource webResource = client.resource( testURL + "context=ACRIN");
 
		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
 
		String output = retrieveOutput(response);  
		
		assertTrue(output.contains("No Records Found"));
	}   
    
    @Test
    public void testInvalidName() {
    	Client client = Client.create();
		WebResource webResource = client.resource( testURL + "name=InvalidBone%20Pain");
 
		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
 
		String output = retrieveOutput(response);  
		
		assertTrue(output.contains("No Records Found"));
	} 
    
    @Test
    public void testInvalidWorkFlowStatus() {
    	Client client = Client.create();
		WebResource webResource = client.resource( testURL + "workflowStatus=InvalidDRAFT%20MOD");
 
		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
 
		String output = retrieveOutput(response);  
		
		assertTrue(output.contains("No Records Found"));
	}  
    
    @Test
    public void testInvalidRegistrationStatus() {
    	Client client = Client.create();
		WebResource webResource = client.resource( testURL + "registrationStatus=InvalidStandard");
 
		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
 
		String output = retrieveOutput(response);  
		
		assertTrue(output.contains("No Records Found"));
	}  
    
    @Test
    public void testInvalidClassificationItem() {
    	Client client = Client.create();
		WebResource webResource = client.resource( testURL + "classificationItem=Invalid6665-RTOG");
 
		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
 
		String output = retrieveOutput(response);  
		
		assertTrue(output.contains("No Records Found"));
	}    
    
    @Test
    public void testInvalidClassification() {
    	Client client = Client.create();
		WebResource webResource = client.resource( testURL + "classification=InvalidPhase");
 
		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
 
		String output = retrieveOutput(response);  
		
		assertTrue(output.contains("No Records Found"));
	} 
    
    @Test
    public void testEmptyParam() {
    	Client client = Client.create();
		WebResource webResource = client.resource( testURL );
 
		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
 
		String output = retrieveOutput(response);  
		
		assertTrue(output.contains("Please supply a valid Parameter"));
	}

}
