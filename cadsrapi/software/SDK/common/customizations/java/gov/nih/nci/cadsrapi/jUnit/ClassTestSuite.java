/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-api/LICENSE.txt for details.
 */

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
