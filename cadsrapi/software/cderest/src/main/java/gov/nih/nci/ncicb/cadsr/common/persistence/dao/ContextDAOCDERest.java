package gov.nih.nci.ncicb.cadsr.common.persistence.dao;

import gov.nih.nci.ncicb.cadsr.common.resource.Context;

public interface ContextDAOCDERest extends ContextDAO {
	Context getContextByName(String name); 
}
