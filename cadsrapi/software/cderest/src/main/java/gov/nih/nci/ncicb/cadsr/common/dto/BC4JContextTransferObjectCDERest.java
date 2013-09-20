package gov.nih.nci.ncicb.cadsr.common.dto;

import gov.nih.nci.ncicb.cadsr.common.resource.Context;

import java.sql.Date;
import java.sql.SQLException;
import java.io.Serializable;

import gov.nih.nci.ncicb.cadsr.common.dto.base.AuditTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.bc4j.BC4JContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.ContextsViewRowImpl;

public class BC4JContextTransferObjectCDERest extends BC4JContextTransferObject {
	public void setVersion(Float version) {
		this.version = version;
	}

}
