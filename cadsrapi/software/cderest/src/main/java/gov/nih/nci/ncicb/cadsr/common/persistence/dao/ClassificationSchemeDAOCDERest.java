package gov.nih.nci.ncicb.cadsr.common.persistence.dao;

import gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.common.resource.Classification;

public interface ClassificationSchemeDAOCDERest extends ClassificationSchemeDAO {
	Classification getClassificationByName(String classificationName);
	ClassSchemeItem getClassificationItemByName(String classificationItemName);
}
