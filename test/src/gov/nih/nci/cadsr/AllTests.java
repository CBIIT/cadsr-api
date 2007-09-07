package gov.nih.nci.cadsr;

import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * A test suite with all the unit tests for caDSR.
 * 
 * @author <a href="mailto:rokickik@mail.nih.gov">Konrad Rokicki</a>
 */
public class AllTests {

    private static ApplicationService service;

    static {
        try {
            service = ApplicationServiceProvider.getApplicationService();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ApplicationService getService() {
        return service;
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for gov.nih.nci.cadsr");
        //$JUnit-BEGIN$
        suite.addTestSuite(QueryTest.class);
        //$JUnit-END$
        return suite;
    }

}
