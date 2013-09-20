package gov.nih.nci.ncicb.cadsr.common.dto;

import gov.nih.nci.ncicb.cadsr.common.dto.bc4j.BC4JDataElementConceptTransferObject;


public class BC4JDataElementConceptTransferObjectCDERest extends BC4JDataElementConceptTransferObject {
	protected String cdContextVersion;
	protected String cdLongName;
	
	public void setCdPrefName(String cdPrefName) {
		this.cdPrefName = cdPrefName;
	}
	public void setCdContextName(String cdContextName) {
		this.cdContextName = cdContextName;
	}
	public void setCdVersion(Float cdVersion) {
		this.cdVersion = cdVersion;
	}
	public void setCdPublicId(int cdPublicId) {
		this.cdPublicId = cdPublicId;
	}
	public String getCdContextVersion() {
		return cdContextVersion;
	}
	public void setCdContextVersion(String cdContextVersion) {
		this.cdContextVersion = cdContextVersion;
	}
	public String getCdLongName() {
		return cdLongName;
	}
	public void setCdLongName(String cdLongName) {
		this.cdLongName = cdLongName;
	}	
}
