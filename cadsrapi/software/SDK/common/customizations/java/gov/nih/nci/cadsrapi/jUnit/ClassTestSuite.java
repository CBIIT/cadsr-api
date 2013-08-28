package gov.nih.nci.cadsrapi.jUnit;

import gov.nih.nci.cadsrapi.jUnit.applicationService.impl.ObjectCartServiceImplTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ClassTestSuite {

	public static Test suite() {

        TestSuite suite = new TestSuite();
  
        suite.addTestSuite(ObjectCartServiceImplTest.class);

        return suite;
    }
}
