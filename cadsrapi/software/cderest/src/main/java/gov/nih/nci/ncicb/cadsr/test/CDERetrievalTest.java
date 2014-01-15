package gov.nih.nci.ncicb.cadsr.test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.*;

import static org.junit.Assert.*;

public class CDERetrievalTest {

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
    	Client client = Client.create();
		WebResource webResource = client
		   .resource("http://localhost:8080/cderest/rest/services/getDataElement?publicId=2183222");
 
		ClientResponse response = webResource.accept("application/xml")
                   .get(ClientResponse.class);
 
		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}

		String output = response.getEntity(String.class);  
		
		assertTrue(output.contains("2183222"));
	}
    
    @Test
    public void testFailure() {
    	Client client = Client.create();
		WebResource webResource = client
		   .resource("http://localhost:8080/cderest/rest/services/getDataElement?context=ACRIN&format=XML&start=1&size=2");
 
		ClientResponse response = webResource.accept("application/xml")
                   .get(ClientResponse.class);
 
		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}

		String output = response.getEntity(String.class);     
		
		assertTrue(output.contains("No Records Found"));
	}

}
