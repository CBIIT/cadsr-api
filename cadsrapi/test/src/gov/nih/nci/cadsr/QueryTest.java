package gov.nih.nci.cadsr;

import gov.nih.nci.cadsr.domain.ClassificationScheme;
import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.cadsr.umlproject.domain.AttributeTypeMetadata;
import gov.nih.nci.cadsr.umlproject.domain.Project;
import gov.nih.nci.cadsr.umlproject.domain.UMLAttributeMetadata;
import gov.nih.nci.cadsr.umlproject.domain.UMLClassMetadata;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

/**
 * Basic queries against the caDSR API. 
 * 
 * @author <a href="mailto:rokickik@mail.nih.gov">Konrad Rokicki</a>
 */
public class QueryTest extends TestCase {

    private final ApplicationService appService = AllTests.getService();
    
    /**
     * Scenario 1: Query for all projects. 
     * @throws Exception
     */
    public void testDetachedCriteria() throws Exception {
        
        DetachedCriteria projectCriteria =
           DetachedCriteria.forClass(Project.class);
        projectCriteria.addOrder(Order.asc("shortName"));

       List results = appService.query(projectCriteria);

       assertNotNull(results);
       assertTrue((results.size() > 0));
       
       for (Iterator iterator = results.iterator(); iterator.hasNext(); ) {
          Project project = (Project)iterator.next();
          assertNotNull(project.getId());
          assertNotNull(project.getShortName());
          assertNotNull(project.getVersion());
          
          ClassificationScheme cs = project.getClassificationScheme();
          assertNotNull(cs);
          assertNotNull(cs.getContext());
          assertNotNull(cs.getContext().getName());
       }
    }

    /**
     * Scenario 2: Retrieving class metadata.
     * @throws Exception
     */
    public void testClassSearch() throws Exception {

        UMLClassMetadata umlClass = new UMLClassMetadata();
        umlClass.setName("gene");
        
        List results = appService.search(UMLClassMetadata.class, umlClass);
        
        assertNotNull(results);
        assertTrue((results.size() > 0));
        
        for (Iterator iterator = results.iterator(); iterator.hasNext(); ) {
            umlClass = (UMLClassMetadata)iterator.next();
            assertNotNull(umlClass.getFullyQualifiedName());
            assertNotNull(umlClass.getDescription());
            assertNotNull(umlClass.getProject().getVersion());
            assertNotNull(umlClass.getObjectClass().getPublicID());
        }

    }

    /**
     * Scenario 3: Retrieving attribute metadata for a class.
     * @throws Exception
     */
    public void testAttributeSearch() throws Exception {

        UMLClassMetadata umlClass = new UMLClassMetadata();
        umlClass.setName("gene");
        List results = appService.search(UMLClassMetadata.class, umlClass);
        assertNotNull(results);
        assertTrue((results.size() > 0));
        umlClass = (UMLClassMetadata)results.get(0);

        Collection<UMLAttributeMetadata> attrs = 
            umlClass.getUMLAttributeMetadataCollection();
        
        for (Iterator iterator = attrs.iterator(); iterator.hasNext(); ) {
            
            UMLAttributeMetadata umlAttribute = (UMLAttributeMetadata)iterator.next();
            assertNotNull(umlAttribute.getName());
            
            AttributeTypeMetadata atm = umlAttribute.getAttributeTypeMetadata();
            assertNotNull(atm);
            assertNotNull(atm.getValueDomainDataType());
            
            DataElement de = umlAttribute.getDataElement();
            assertNotNull(de);
            assertNotNull(de.getPublicID());
            assertNotNull(umlAttribute.getName());
        }
    }
    
    /**
     * Scenario 4: Retrieving attributes named *id.
     * @throws Exception
     */
    public void testIdStarQuery() throws Exception {

        UMLAttributeMetadata umlAttr = new UMLAttributeMetadata();
        umlAttr.setName("*id");

        List results = appService.search(UMLAttributeMetadata.class, umlAttr);
        assertNotNull(results);
        assertTrue((results.size() > 0));
    }
    
}
