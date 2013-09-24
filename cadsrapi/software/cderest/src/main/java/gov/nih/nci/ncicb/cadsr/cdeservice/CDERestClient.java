package gov.nih.nci.ncicb.cadsr.cdeservice;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
	 
public class CDERestClient {
 
public static void main(String[] args) 
{
	try
	{
		String query = "SELECT distinct  de.de_idseq       ,de.preferred_name de_preferred_name      ,de.long_name       ,rd.doc_text       ,conte.name       ,de.asl_name       ,to_char(de.cde_id) de_cdeid      ,de.version de_version       ,meta_config_mgmt.get_usedby(de.de_idseq) de_usedby       ,de.vd_idseq       ,de.dec_idseq       ,de.conte_idseq       ,de.preferred_definition       ,acr.registration_status       ,rsl.display_order       ,asl.display_order wkflow_order       ,de.cde_id cdeid from sbr.data_elements_view de , sbr.reference_documents_view rd , sbr.contexts_view conte  , sbr.ac_registrations_view acr , sbr.reg_status_lov_view rsl , sbr.ac_status_lov_view asl  where de.de_idseq = rd.ac_idseq (+) and rd.dctl_name (+) = 'Preferred Question Text' and nvl(acr.registration_status,'-1') NOT IN ( 'Retired' )  and asl.asl_name NOT IN ( 'CMTE APPROVED' , 'CMTE SUBMTD' , 'CMTE SUBMTD USED' , 'RETIRED ARCHIVED' , 'RETIRED PHASED OUT' , 'RETIRED WITHDRAWN' )  and conte.name NOT IN ( 'TEST', 'Training' ) and de.asl_name != 'RETIRED DELETED'  and conte.conte_idseq = de.conte_idseq  and de.de_idseq IN (select ac_idseq                      from   sbr.designations_view des                      where  des.conte_idseq = 'A7CF3B35-74D1-8852-E040-BB89AD435FAB'                     and    des.DETL_NAME = 'USED_BY'                      UNION                      select de_idseq                      from   sbr.data_elements_view de1                      where  de1.conte_idseq ='A7CF3B35-74D1-8852-E040-BB89AD435FAB')  and de.de_idseq = acr.ac_idseq (+) and acr.registration_status = rsl.registration_status (+)  and de.asl_name = asl.asl_name (+)";
		System.out.println(query);
    	query.replaceFirst("SELECT distinct", "SELECT distinct rownnum rn,");
    	System.out.println(query.replaceFirst("SELECT distinct", "SELECT distinct rownnum rn,"));
//		Client client = Client.create();
//		WebResource webResource = client
//		   .resource("http://localhost:8080/cderest/rest/services/getDataElement?publicId=2183222");
// 
//		ClientResponse response = webResource.accept("application/json")
//                   .get(ClientResponse.class);
// 
//		if (response.getStatus() != 200) {
//		   throw new RuntimeException("Failed : HTTP error code : "
//			+ response.getStatus());
//		}

//		String output = response.getEntity(String.class);
//		System.out.println("Output from Server .... \n");
//		System.out.println(output);
	 
	  } catch (Exception e) 
	  { 
		e.printStackTrace();
	  }
 
	}
}