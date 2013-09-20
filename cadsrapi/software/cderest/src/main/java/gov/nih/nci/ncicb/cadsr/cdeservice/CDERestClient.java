package gov.nih.nci.ncicb.cadsr.cdeservice;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
	 
public class CDERestClient {
 
public static void main(String[] args) 
{
	try
	{
		Client client = Client.create();
		WebResource webResource = client
		   .resource("http://localhost:8080/cderest/rest/services/getDataElement?publicId=2183222");
 
		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
 
		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}

		String output = response.getEntity(String.class);
		System.out.println("Output from Server .... \n");
		System.out.println(output);
	 
	  } catch (Exception e) 
	  { 
		e.printStackTrace();
	  }
 
	}
}