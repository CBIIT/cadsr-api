caDSR API Model Changes:--
-----------------------

1.Extract SDK-4.4.1-bin.zip to local folder.
2.Copy caDSR40.xmi to <sdk-extracted dir>/example-project/models
3.Run caDSR40.xmi with SDK4.4.1
4.Update codegen.properties in <sdk-extracted dir>/example-project/build
   
    a.    Update EXCLUDE_PACKAGE=.*?java.*,.*?[V|v]alue.?[D|d]omain.*,.*?iso21090.* to EXCLUDE_PACKAGE=.*?                     java.*,.*?iso21090.*
    b.    Update GENERATE_JAXB_MAPPING value as �false�
5.Update install.properties in <sdk-extracted dir>/example-project/build with database settings 
6.Open cmd window and navigate to <sdk-extracted dir>/example-project/build and type �ant build:SDK�
7.This should generate caDSR system without any errors.

Issues addressed:--
-----------------
 
1.Following validation errors cause SDK code generation to fail. caDSR model has been updated to include 'inverse-of' for the associations for the failed relations below. This change would not change physical characteristics of caDSR model. 
 
-generate-codegen:
     [echo] caCORE SDK - Codegen: Running the Code Generator
     [java] [main] ERROR codegen.Generator  -
     [java] Model Mapping Validator: The foreign key column P_DE_IDSEQ of table CABIO31_DE_DERIVATIONS_VIEW is not nullable (i.e.,
lower end multiplicity > 0 in the logical model association end).  This requires that the inverse setting ('inverse-of' tag) be set
on the P_DE_IDSEQ column with a value of: gov.nih.nci.cadsr.domain.DataElementDerivation.derivedDataElement.
     [java] Model Mapping Validator: The foreign key column CONDR_IDSEQ of table CABIO31_CC_VIEW is not nullable (i.e., lower end m
ultiplicity > 0 in the logical model association end).  This requires that the inverse setting ('inverse-of' tag) be set on the CON
DR_IDSEQ column with a value of: gov.nih.nci.cadsr.domain.ComponentConcept.derivationRule.
     [java] Model Mapping Validator: The foreign key column VD_IDSEQ of table CABIO31_VD_PV_VIEW is not nullable (i.e., lower end m
ultiplicity > 0 in the logical model association end).  This requires that the inverse setting ('inverse-of' tag) be set on the VD_
IDSEQ column with a value of: gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue.enumeratedValueDomain.
     [java] Model Mapping Validator: The foreign key column PV_IDSEQ of table CABIO31_VD_PV_VIEW is not nullable (i.e., lower end m
ultiplicity > 0 in the logical model association end).  This requires that the inverse setting ('inverse-of' tag) be set on the PV_
IDSEQ column with a value of: gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue.permissibleValue.
     [java] Model Mapping Validator: The foreign key column CP_IDSEQ of table UP_CLASS_METADATA_MVW is not nullable (i.e., lower en
d multiplicity > 0 in the logical model association end).  This requires that the inverse setting ('inverse-of' tag) be set on the
CP_IDSEQ column with a value of: gov.nih.nci.cadsr.umlproject.domain.UMLClassMetadata.project.
     [java] Model Mapping Validator: The foreign key column PG_IDSEQ of table UP_CLASS_METADATA_MVW is not nullable (i.e., lower en
d multiplicity > 0 in the logical model association end).  This requires that the inverse setting ('inverse-of' tag) be set on the
PG_IDSEQ column with a value of: gov.nih.nci.cadsr.umlproject.domain.UMLClassMetadata.UMLPackageMetadata.
     [java] Model Mapping Validator: The foreign key column SP_IDSEQ of table UP_PACKAGES_MVW is not nullable (i.e., lower end mult
iplicity > 0 in the logical model association end).  This requires that the inverse setting ('inverse-of' tag) be set on the SP_IDS
EQ column with a value of: gov.nih.nci.cadsr.umlproject.domain.UMLPackageMetadata.subProject.



 