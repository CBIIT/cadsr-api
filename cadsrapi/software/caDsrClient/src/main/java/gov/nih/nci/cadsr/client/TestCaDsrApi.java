package gov.nih.nci.cadsr.client;

import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.cadsr.domain.DataElementConcept;
import gov.nih.nci.cadsr.domain.EnumeratedValueDomain;
import gov.nih.nci.cadsr.domain.PermissibleValue;
import gov.nih.nci.cadsr.domain.ValueDomain;
import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import java.util.Collection;
import java.util.List;

 
/**
 *  A sample use of the caDSR api.
 *  @author caDSR team.
 */
public class TestCaDsrApi
{
    /**
     * Search for Data Elements using the Long Name
     *
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
        	
        	System.out.println("...TestCaDsrApi Started...");
        	ApplicationService appService = ApplicationServiceProvider.getApplicationServiceFromUrl("https://cadsrapi.nci.nih.gov/cadsrapi4");
            System.out.println("Searching for DataElements");
 
            // Search for the Data Element with the Long Name "Patient Race Category*". The asterisk (*) is a wild card.
            DataElement dataElement = new DataElement();
            dataElement.setLongName("Patient Race Category*");
            //dataElement.setPublicID(2238438L);
            //dataElement.setLatestVersionIndicator("Yes");
            List<Object> results = appService.search(DataElement.class, dataElement);
            System.out.println(" received results : " + results.size());
           
            for (int i = 0; i < results.size(); i++)
            {
                // Show the DE
            	Object curr = results.get(i);
                DataElement de = (DataElement) curr;
                System.out.println("===Data Element " + de.getLongName());
                System.out.println("Data Element " + de.getVersion());
                System.out.println("Data Element " + de.getPreferredDefinition());
            }
        }
        catch (Exception exception)
        {
            System.out.println("--------Error in the TestCaDsrApi");
            exception.printStackTrace();
        }
    }
}