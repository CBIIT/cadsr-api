<?xml version='1.0'?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="text"/>

<xsl:template match="/">
    <xsl:text>Data Element Short Name;Data Element Long Name;	Data Element Preferred Question Text;	Data Element Preferred Definition;	Data Element Version;	Data Element Context Name;	Data Element Context Version;	Data Element Public ID;	Data Element Workflow Status;	Data Element Registration Status;	Data Element Begin Date;	Data Element Source;	Data Element Concept Public ID;	Data Element Concept Short Name;	Data Element Concept Long Name;	Data Element Concept Version;	Data Element Concept Context Name;	Data Element Concept Context Version;	Object Class Public ID;	Object Class Long Name;	Object Class Short Name;	Object Class Context Name;	Object Class Version;	Object Class Concept Name;	Object Class Concept Code;	Object Class Concept Public ID;	Object Class Concept Definition Source;	Object Class Concept EVS Source;	Object Class Concept Primary Flag;	Property Public ID;	Property Long Name;	Property Short Name;	Property Context Name;	Property Version;	Property Concept Name;	Property Concept Code;	Property Concept Public ID;	Property Concept Definition Source;	Property Concept EVS Source;	Property Concept Primary Flag;	Value Domain Public ID;	Value Domain Short Name;	Value Domain Long Name;	Value Domain Version;	Value Domain Context Name;	Value Domain Context Version;	Value Domain Type;	Value Domain Datatype;	Value Domain Min Length;	Value Domain Max Length;	Value Domain Min Value;	Value Domain Max Value;	Value Domain Decimal Place;	Value Domain Format;	Value Domain Concept Name;	Value Domain Concept Code;	Value Domain Concept Public ID;	Value Domain Concept Definition Source;	Value Domain Concept EVS Source;	Value Domain Concept Primary Flag;	Representation Public ID;	Representation Long Name;	Representation Short Name;	Representation Context Name;	Representation Version;	Representation Concept Name;	Representation Concept Code;	Representation Concept Public ID;	Representation Concept Definition Source;	Representation Concept EVS Source;	Representation Concept Primary Flag;	Valid Values;	Value Meaning Name;	Value Meaning Description;	Value Meaning Concepts;	PV Begin Date;	PV End Date;	Value Meaning PublicID;	Value Meaning Version;	Classification Scheme Short Name;	Classification Scheme Version;	Classification Scheme Context Name;	Classification Scheme Context Version;	Classification Scheme Item Name;	Classification Scheme Item Type Name;	Classification Scheme Item Public Id;	Classification Scheme Item Version;	Data Element Alternate Name Context Name;	Data Element Alternate Name Context Version;	Data Element Alternate Name;	Data Element Alternate Name Type;	Document;	Document Name;	Document Type;	Derivation Type;	Derivation Method;	Derivation Rule;	Concatenation Character;	DDE Public ID;	DDE Long Name;	DDE Version;	DDE Workflow Status;	DDE Context;	DDE Display Order;</xsl:text>
<xsl:text>&#xd;</xsl:text>        
   	<xsl:for-each select="/DataElementsList/DataElement">
	    <xsl:variable name="node-index" select="count(preceding-sibling::DataElement)+1" />	
	       
        <xsl:variable name="prename" select="/DataElementsList/DataElement[$node-index]/PREFERREDNAME"/>
        <xsl:variable name="lname" select="/DataElementsList/DataElement[$node-index]/LONGNAME"/>
         <xsl:variable name="preftext" select="/DataElementsList/DataElement[$node-index]/REFERENCEDOCUMENTSLIST/REFERENCEDOCUMENTSLIST_ITEM/DocumentText"/>
        <xsl:variable name="predef" select="/DataElementsList/DataElement[$node-index]/PREFERREDDEFINITION"/>
        <xsl:variable name="version" select="/DataElementsList/DataElement[$node-index]/VERSION"/>
        <xsl:variable name="conname" select="/DataElementsList/DataElement[$node-index]/CONTEXTNAME"/>
        <xsl:variable name="conversion" select="/DataElementsList/DataElement[$node-index]/CONTEXTVERSION"/>
        <xsl:variable name="pubid" select="/DataElementsList/DataElement[$node-index]/PUBLICID"/>
        <xsl:variable name="wstatus" select="/DataElementsList/DataElement[$node-index]/WORKFLOWSTATUS"/>
        <xsl:variable name="restatus" select="/DataElementsList/DataElement[$node-index]/REGISTRATIONSTATUS"/>
        <xsl:variable name="begindate"/>
        <xsl:variable name="origin" select="/DataElementsList/DataElement[$node-index]/ORIGIN"/>
        <xsl:variable name="pid" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/PublicId"/>
        <xsl:variable name="prefname" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/PreferredName"/>
        <xsl:variable name="loname" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/LongName"/>
        <xsl:variable name="conceptversion" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Version"/>       
        <xsl:variable name="contextname" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ContextName"/> 
        <xsl:variable name="contextversion" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ContextVersion"/> 
        
       <!-- ObjectClass -->  
          <xsl:variable name="objpubid" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/PublicId"/> 
          <xsl:variable name="objlongname" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/LongName"/>
          <xsl:variable name="objprename" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/PreferredName"/>
          <xsl:variable name="objcontname" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/ContextName"/>
          <xsl:variable name="objversion" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/Version"/> 
              
        
        <xsl:value-of select="concat($prename,';',' ',$lname,';',' ',$preftext,';',' ',$predef,';',' ',$version,';',' ',$conname,';',' ',$conversion,';',' ',$pubid,';',' ',$wstatus,';',' ',$restatus,';',' ',$begindate,';',' ',$origin,';',' ',$pid,';',' ',$prefname,';',' ',$loname,';',' ',$conceptversion,';',' ',$contextname,';',' ',$contextversion,';',' ',$objpubid,';',' ',$objlongname,';',' ',$objprename,';',' ',$objcontname,';',' ',$objversion,';',' ')"/>


	   <!-- Calling 1st ObjectClass -->   
	          <xsl:variable name="objconceptlname" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/ConceptDetails/ConceptDetails_ITEM[1]/LONG_NAME"/> 	         
	          <xsl:variable name="objconceptcode" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/ConceptDetails/ConceptDetails_ITEM[1]/PREFERRED_NAME"/> 
	          
	          <xsl:variable name="objconceptpubid" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/ConceptDetails/ConceptDetails_ITEM[1]/CON_ID"/>
        <xsl:variable name="objconceptdef" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/ConceptDetails/ConceptDetails_ITEM[1]/DEFINITION_SOURCE"/>   
        <xsl:variable name="objconceptevs" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/ConceptDetails/ConceptDetails_ITEM[1]/EVS_SOURCE"/>  
        <xsl:variable name="objconceptflag" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/ConceptDetails/ConceptDetails_ITEM[1]/PRIMARY_FLAG_IND"/>
        
<xsl:value-of select="concat($objconceptlname,';',' ',$objconceptcode,';',' ',$objconceptpubid,';',' ',$objconceptdef,';',' ',$objconceptevs,';',' ',$objconceptflag,';',' ')"/>  
      
       <!-- Property -->        
        <xsl:variable name="propid" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/PublicId"/>   
        <xsl:variable name="proplname" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/LongName"/>
        <xsl:variable name="propprename" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/PreferredName"/>
        <xsl:variable name="propconxname" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/ContextName"/>
        <xsl:variable name="propversion" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/ContextVersion"/>
  
      <xsl:value-of select="concat($propid,';',' ',$proplname,';',' ',$propprename,';',' ',$propconxname,';',' ',$propversion,';',' ')"/>
  
    <!--Calling 1st repeatable Property -->   
         <xsl:variable name="propconcname" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/ConceptDetails/ConceptDetails_ITEM[1]/LONG_NAME"/>
        <xsl:variable name="propconccode" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/ConceptDetails/ConceptDetails_ITEM[1]/PREFERRED_NAME"/>
        <xsl:variable name="propconcid" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/ConceptDetails/ConceptDetails_ITEM[1]/CON_ID"/>
        <xsl:variable name="propconcsrc" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/ConceptDetails/ConceptDetails_ITEM[1]/DEFINITION_SOURCE"/>
        <xsl:variable name="propconcevs" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/ConceptDetails/ConceptDetails_ITEM[1]/EVS_SOURCE"/>
        <xsl:variable name="propconcflag" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/ConceptDetails/ConceptDetails_ITEM[1]/PRIMARY_FLAG_IND"/>

 <xsl:value-of select="concat($propconcname,';',' ',$propconccode,';',' ',$propconcid,';',' ',$propconcsrc,';',' ',$propconcevs,';',' ',$propconcflag,';',' ')"/>
  
  

  
   <!-- VALUEDOMAIN -->
        <xsl:variable name="valid" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PublicId"/>   
        <xsl:variable name="valsname" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PreferredName"/>
        <xsl:variable name="vallname" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/LongName"/>
        <xsl:variable name="valversion" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Version"/>
        <xsl:variable name="valconname" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/ContextName"/>
        <xsl:variable name="valconversion" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/ContextVersion"/>
        <xsl:variable name="valdomtype" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/ValueDomainType"/>
         <xsl:variable name="valdatatype" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Datatype"/>
        <xsl:variable name="valmin" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/MinimumLength"/>
        <xsl:variable name="valmax" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/MaximumLength"/>
        <xsl:variable name="valminval" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/MinimumValue"/>
        <xsl:variable name="valmaxval" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/MaximumValue"/>
        <xsl:variable name="valdecimal" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/DecimalPlace"/>
        <xsl:variable name="valformat" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/DisplayFormat"/>
        <xsl:variable name="valconcname" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/CharacterSetName"/>
        
<xsl:value-of select="concat($valid,';',' ',$valsname,';',' ',$vallname,';',' ',$valversion,';',' ',$valconname,';',' ',$valconversion,';',' ',$valdomtype,';',' ',$valdatatype,';',' ',$valmin,';',' ',$valmax,';',' ',$valminval,';',' ',$valmaxval,';',' ',$valdecimal,';',' ',$valformat,';',' ',$valconcname,';',' ')"/>     
        
        <!-- Calling 1st repeatable ValueDomainConcepts -->   
          <xsl:variable name="valconccode" select="//DataElementsList/DataElement[$node-index]/VALUEDOMAIN/ValueDomainConcepts/ValueDomainConcepts_ITEM[1]/PREFERRED_NAME"/>
        <xsl:variable name="valconcid" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/ValueDomainConcepts/ValueDomainConcepts_ITEM[1]/CON_ID"/>
        <xsl:variable name="valconcsrc" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/ValueDomainConcepts/ValueDomainConcepts_ITEM[1]/DEFINITION_SOURCE"/>
        <xsl:variable name="valconcevs" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/ValueDomainConcepts/ValueDomainConcepts_ITEM[1]/EVS_SOURCE"/>
        <xsl:variable name="valconcflag" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/ValueDomainConcepts/ValueDomainConcepts_ITEM[1]/PRIMARY_FLAG_IND"/>
        
         <xsl:value-of select="concat($valconccode,';',' ',$valconcid,';',' ',$valconcsrc,';',' ',$valconcevs,';',' ',$valconcflag,';',' ')"/>
        
        <xsl:variable name="valrepid" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/PublicId"/>
        <xsl:variable name="valreplname" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/LongName"/>
        <xsl:variable name="valrepprename" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/PreferredName"/>
        <xsl:variable name="valrepconxname" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/ContextName"/>
        <xsl:variable name="valrepversion" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/Version"/>
      
      
        <xsl:value-of select="concat($valrepid,';',' ',$valreplname,';',' ',$valrepprename,';',' ',$valrepconxname,';',' ',$valrepversion,';',' ')"/> 
        
        <!-- Calling 1st repeatable VALUEDOMAIN/Representation --> 
        <xsl:variable name="valrepconcname" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/ConceptDetails/ConceptDetails_ITEM[1]/LONG_NAME"/>
        <xsl:variable name="valrepconccode" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/ConceptDetails/ConceptDetails_ITEM[1]/PREFERRED_NAME"/>
        <xsl:variable name="valrepconcid" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/ConceptDetails/ConceptDetails_ITEM[1]/CON_ID"/>
        <xsl:variable name="valrepconcsrc" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/ConceptDetails/ConceptDetails_ITEM[1]/DEFINITION_SOURCE"/>
        <xsl:variable name="valrepconcevs" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/ConceptDetails/ConceptDetails_ITEM[1]/EVS_SOURCE"/>
        <xsl:variable name="valrepconcflag" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/ConceptDetails/ConceptDetails_ITEM[1]/PRIMARY_FLAG_IND"/>
        
         <xsl:value-of select="concat($valrepconcname,';',' ',$valrepconccode,';',' ',$valrepconcid,';',' ',$valrepconcsrc,';',' ',$valrepconcevs,';',' ',$valrepconcflag,';',' ')"/> 
         
        
        <!-- Calling 1st repeatable VALUEDOMAIN/"Valid Values" --> 
        
        <xsl:variable name="valperval" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM[1]/VALIDVALUE"/>
        <xsl:variable name="valpername" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM[1]/VALUEMEANING"/>
        <xsl:variable name="valperdesc" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM[1]/MEANINGDESCRIPTION"/>
        <xsl:variable name="valperconc" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM[1]/MEANINGCONCEPTS"/>
        <xsl:variable name="valperbdate" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM[1]/PVBEGINDATE"/>
        <xsl:variable name="valperedate" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM[1]/PVENDDATE"/>
        <xsl:variable name="valpervmid" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM[1]/VMPUBLICID"/>
        <xsl:variable name="valpervmversion" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM[1]/VMVERSION"/>
        
        <xsl:value-of select="concat($valperval,';',' ',$valpername,';',' ',$valperdesc,';',' ',$valperdesc,';',' ',$valperbdate,';',' ',$valperedate,';',' ',$valpervmid,';',' ',$valpervmversion,';',' ')"/>
        
<!-- Calling 1st repeatable CLASSIFICATIONSLIST--> 
        
         <xsl:variable name="clsprename" select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM[1]/ClassificationScheme/PreferredName"/> 
       <xsl:variable name="clsversion" select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM[1]/ClassificationScheme/Version"/> 
       <xsl:variable name="clsconx" select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM[1]/ClassificationScheme/ContextName"/>
       <xsl:variable name="clsconxvers" select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM[1]/ClassificationScheme/ContextVersion"/>
       <xsl:variable name="clssname" select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM[1]/ClassificationSchemeItemName"/>
       <xsl:variable name="clsstype" select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM[1]/ClassificationSchemeItemType"/>
       <xsl:variable name="clssid" select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM[1]/CsiPublicId"/>
       <xsl:variable name="clssversion" select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM[1]/CsiVersion"/>
       
     <xsl:value-of select="concat($clsprename,';',' ',$clsversion,';',' ',$clsconx,';',' ',$clsconxvers,';',' ',$clssname,';',' ',$clsstype,';',' ',$clssid,';',' ',$clssversion,';',' ')"/>
     
     
 <!--Calling 1st repeatable ALTERNATENAMELIST-->
       
        <xsl:variable name="altconxname" select="/DataElementsList/DataElement[$node-index]/ALTERNATENAMELIST/ALTERNATENAMELIST_ITEM[1]/ContextName"/>
        <xsl:variable name="altconxversion" select="/DataElementsList/DataElement[$node-index]/ALTERNATENAMELIST/ALTERNATENAMELIST_ITEM[1]/ContextVersion"/>
        <xsl:variable name="altname" select="/DataElementsList/DataElement[$node-index]/ALTERNATENAMELIST/ALTERNATENAMELIST_ITEM[1]/AlternateName"/>
        <xsl:variable name="alttype" select="/DataElementsList/DataElement[$node-index]/ALTERNATENAMELIST/ALTERNATENAMELIST_ITEM[1]/AlternateNameType"/>
       
    <xsl:value-of select="concat($altconxname,';',' ',$altconxversion,';',' ',$altname,';',' ',$alttype,';',' ')"/>     
    
    
     <!--Calling 1st repeatable REFERENCEDOCUMENTSLIST-->
    
         <xsl:variable name="doc" select="/DataElementsList/DataElement[$node-index]/REFERENCEDOCUMENTSLIST/REFERENCEDOCUMENTSLIST_ITEM[1]/DocumentText"/>
         <xsl:variable name="docname" select="/DataElementsList/DataElement[$node-index]/REFERENCEDOCUMENTSLIST/REFERENCEDOCUMENTSLIST_ITEM[1]/Name"/>
         <xsl:variable name="doctype" select="/DataElementsList/DataElement[$node-index]/REFERENCEDOCUMENTSLIST/REFERENCEDOCUMENTSLIST_ITEM[1]/DocumentType"/>
                  
         <xsl:value-of select="concat($doc,';',' ',$docname,';',' ',$doctype,';',' ')"/> 
     
     <!--DATAELEMENTDERIVATION-->
        <xsl:variable name="drvtype" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTDERIVATION/DerivationType"/>
        <xsl:variable name="drvmethods" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTDERIVATION/Methods"/>
        <xsl:variable name="drvrule" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTDERIVATION/Rule"/>
        <xsl:variable name="drvconcat" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTDERIVATION/ConcatenationCharacter"/>
     
      <xsl:value-of select="concat($drvtype,';',' ',$drvmethods,';',' ',$drvrule,';',' ',$drvconcat)"/><xsl:text>&#xd;</xsl:text>  
  
       <!--END OF 1ST LINE !!!!!!-->
       <!--END OF 1ST LINE !!!!!!-->
       
       
     <!--START OF 2nd LINE and so on !!!!!!-->
       
   
       <!--ObjectClass !!!!!!-->
       <xsl:for-each select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/ConceptDetails/ConceptDetails_ITEM">
	         <xsl:variable name="node-obj-index" select="count(preceding-sibling::ConceptDetails_ITEM)+1" />
	         
	       <xsl:variable name="objconceptname2" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/ConceptDetails/ConceptDetails_ITEM[$node-obj-index]/LONG_NAME"/> 
	          <xsl:variable name="objconceptcode2" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/ConceptDetails/ConceptDetails_ITEM[$node-obj-index]/PREFERRED_NAME"/> 	          
	          <xsl:variable name="objconceptpubid2" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/ConceptDetails/ConceptDetails_ITEM[$node-obj-index]/CON_ID"/>
        <xsl:variable name="objconceptdef2" select="/DataElementsList/DataElement[$node-index]/DATAELEMEsNTCONCEPT/ObjectClass/ConceptDetails/ConceptDetails_ITEM[$node-obj-index]/DEFINITION_SOURCE"/>   
        <xsl:variable name="objconceptevs2" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/ConceptDetails/ConceptDetails_ITEM[$node-obj-index]/EVS_SOURCE"/>  
        <xsl:variable name="objconceptflag2" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/ObjectClass/ConceptDetails/ConceptDetails_ITEM[$node-obj-index]/PRIMARY_FLAG_IND"/>  
               
   <xsl:if test="$node-obj-index>1 "><xsl:text> ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; </xsl:text><xsl:value-of select="concat($objconceptname2,';',' ',$objconceptcode2,';',' ',$objconceptpubid2,';',' ',$objconceptdef2,';',' ',$objconceptevs2,';',' ',$objconceptflag2,';',' ')"/><xsl:text>&#xd;</xsl:text></xsl:if>
  
       
        </xsl:for-each>
       
       
       
         <!--Property !!!!!!-->
         
   <xsl:for-each select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/ConceptDetails/ConceptDetails_ITEM"> 
         <xsl:variable name="node-prop-index" select="count(preceding-sibling::ConceptDetails_ITEM)+1" />
   
     <xsl:variable name="propconcname2" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/ConceptDetails/ConceptDetails_ITEM[$node-prop-index]/LONG_NAME"/>
        <xsl:variable name="propconccode2" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/ConceptDetails/ConceptDetails_ITEM[$node-prop-index]/PREFERRED_NAME"/>
        <xsl:variable name="propconcid2" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/ConceptDetails/ConceptDetails_ITEM[$node-prop-index]/CON_ID"/>
        <xsl:variable name="propconcsrc2" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/ConceptDetails/ConceptDetails_ITEM[$node-prop-index]/DEFINITION_SOURCE"/>
        <xsl:variable name="propconcevs2" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/ConceptDetails/ConceptDetails_ITEM[$node-prop-index]/EVS_SOURCE"/>
        <xsl:variable name="propconcflag2" select="/DataElementsList/DataElement[$node-index]/DATAELEMENTCONCEPT/Property/ConceptDetails/ConceptDetails_ITEM[$node-prop-index]/PRIMARY_FLAG_IND"/>

<xsl:if test="$node-prop-index >1 "> <!--36th-->
         <xsl:value-of select="concat('; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ',$propconcname2,';',' ',$propconccode2,';',' ',$propconcid2,';',' ',$propconcsrc2,';',' ',$propconcevs2,';',' ',$propconcflag2,';',' ')"/><xsl:text>&#xd;</xsl:text> </xsl:if>
     
    </xsl:for-each>
    
    
    
      <!--  ValueDomainConcepts -->
      
     <xsl:for-each select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/ValueDomainConcepts/ValueDomainConcepts_ITEM"> 
         <xsl:variable name="node-vdc-index" select="count(preceding-sibling::ValueDomainConcepts_ITEM)+1" />  
    
    
         <xsl:variable name="valconccode2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/ValueDomainConcepts/ValueDomainConcepts_ITEM[$node-vdc-index]/PREFERRED_NAME"/>
        <xsl:variable name="valconcid2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/ValueDomainConcepts/ValueDomainConcepts_ITEM[$node-vdc-index]/CON_ID"/>
        <xsl:variable name="valconcsrc2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/ValueDomainConcepts/ValueDomainConcepts_ITEM[$node-vdc-index]/DEFINITION_SOURCE"/>
        <xsl:variable name="valconcevs2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/ValueDomainConcepts/ValueDomainConcepts_ITEM[$node-vdc-index]/EVS_SOURCE"/>
        <xsl:variable name="valconcflag2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/ValueDomainConcepts/ValueDomainConcepts_ITEM[$node-vdc-index]/PRIMARY_FLAG_IND"/>
        
       <xsl:if test="$node-vdc-index >1 "> <!--54th-->
               <xsl:value-of select="concat('; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ',$valconccode2,';',' ',$valconcid2,';',' ',$valconcsrc2,';',' ',$valconcevs2,';',' ',$valconcflag2,';',' ')"/><xsl:text>&#xd;</xsl:text> </xsl:if>
        
    </xsl:for-each>
    
     
    <!--  VALUEDOMAIN/Representation -->  
    
     <xsl:for-each select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/ConceptDetails/ConceptDetails_ITEM"> 
       <xsl:variable name="node-vdr-index" select="count(preceding-sibling::ConceptDetails_ITEM)+1" />  
         
        <xsl:variable name="valrepconcname2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/ConceptDetails/ConceptDetails_ITEM[$node-vdr-index]/LONG_NAME"/>
        <xsl:variable name="valrepconccode2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/ConceptDetails/ConceptDetails_ITEM[$node-vdr-index]/PREFERRED_NAME"/>
        <xsl:variable name="valrepconcid2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/ConceptDetails/ConceptDetails_ITEM[$node-vdr-index]/CON_ID"/>
        <xsl:variable name="valrepconcsrc2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/ConceptDetails/ConceptDetails_ITEM[$node-vdr-index]/DEFINITION_SOURCE"/>
        <xsl:variable name="valrepconcevs2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/ConceptDetails/ConceptDetails_ITEM[$node-vdr-index]/EVS_SOURCE"/>
        <xsl:variable name="valrepconcflag2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/Representation/ConceptDetails/ConceptDetails_ITEM[$node-vdr-index]/PRIMARY_FLAG_IND"/>
        
      <xsl:if test="$node-vdr-index >1 "> <!--BI - BS checked-->  
         <xsl:value-of select="concat('; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ',$valrepconcname2,';',' ',$valrepconccode2,';',' ',$valrepconcid2,';',' ',$valrepconcsrc2,';',' ',$valrepconcevs2,';',' ',$valrepconcflag2,';',' ')"/><xsl:text>&#xd;</xsl:text> </xsl:if>
     
     
     </xsl:for-each>
     
    <!-- VALUEDOMAIN/"Valid Values" --> 
      
     <xsl:for-each select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM"> 
       <xsl:variable name="node-vvv-index" select="count(preceding-sibling::PermissibleValues_ITEM)+1" />  
             
        <xsl:variable name="valperval2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM[$node-vvv-index]/VALIDVALUE"/>
        <xsl:variable name="valpername2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM[$node-vvv-index]/VALUEMEANING"/>
        <xsl:variable name="valperdesc2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM[$node-vvv-index]/MEANINGDESCRIPTION"/>
        <xsl:variable name="valperconc2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM[$node-vvv-index]/MEANINGCONCEPTS"/>
        <xsl:variable name="valperbdate2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM[$node-vvv-index]/PVBEGINDATE"/>
        <xsl:variable name="valperedate2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM[$node-vvv-index]/PVENDDATE"/>
        <xsl:variable name="valpervmid2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM[$node-vvv-index]/VMPUBLICID"/>
        <xsl:variable name="valpervmversion2" select="/DataElementsList/DataElement[$node-index]/VALUEDOMAIN/PermissibleValues/PermissibleValues_ITEM[$node-vvv-index]/VMVERSION"/>
       
        <xsl:if test="$node-vvv-index >1 "> <!--65th-->   
        <xsl:value-of select="concat('; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ','; ','; ','; ','; ','; ','; ',$valperval2,';',' ',$valpername2,';',' ',$valperdesc2,';',' ',$valperdesc2,';',' ',$valperbdate2,';',' ',$valperedate2,';',' ',$valpervmid2,';',' ',$valpervmversion2,';',' ')"/><xsl:text>&#xd;</xsl:text> </xsl:if>
     
         </xsl:for-each>
 
 <!-- CLASSIFICATIONSLIST--> 
      <xsl:for-each select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM"> 
       <xsl:variable name="node-cls-index" select="count(preceding-sibling::CLASSIFICATIONSLIST_ITEM)+1" />  
         
        <xsl:variable name="clsprename2" select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM[$node-cls-index]/ClassificationScheme/PreferredName"/> 
       <xsl:variable name="clsversion2" select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM[$node-cls-index]/ClassificationScheme/Version"/> 
       <xsl:variable name="clsconx2" select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM[$node-cls-index]/ClassificationScheme/ContextName"/>
       <xsl:variable name="clsconxvers2" select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM[$node-cls-index]/ClassificationScheme/ContextVersion"/>
       <xsl:variable name="clssname2" select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM[$node-cls-index]/ClassificationSchemeItemName"/>
       <xsl:variable name="clsstype2" select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM[$node-cls-index]/ClassificationSchemeItemType"/>
       <xsl:variable name="clssid2" select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM[$node-cls-index]/CsiPublicId"/>
       <xsl:variable name="clssversion2" select="/DataElementsList/DataElement[$node-index]/CLASSIFICATIONSLIST/CLASSIFICATIONSLIST_ITEM[$node-cls-index]/CsiVersion"/>
       
     <xsl:if test="$node-cls-index >1 "><!--80th-->
     <xsl:value-of select="concat('; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',$clsprename2,';',' ',$clsversion2,';',' ',$clsconx2,';',' ',$clsconxvers2,';',' ',$clssname2,';',' ',$clsstype2,';',' ',$clssid2,';',' ',$clssversion2,';',' ')"/><xsl:text>&#xd;</xsl:text> </xsl:if>
      </xsl:for-each>
 
  <!--ALTERNATENAMELIST-->
     <xsl:for-each select="/DataElementsList/DataElement[$node-index]/ALTERNATENAMELIST/ALTERNATENAMELIST_ITEM"> 
       <xsl:variable name="node-alt-index" select="count(preceding-sibling::ALTERNATENAMELIST_ITEM)+1" />  
            
        <xsl:variable name="altconxname2" select="/DataElementsList/DataElement[$node-index]/ALTERNATENAMELIST/ALTERNATENAMELIST_ITEM[$node-alt-index]/ContextName"/>
        <xsl:variable name="altconxversion2" select="/DataElementsList/DataElement[$node-index]/ALTERNATENAMELIST/ALTERNATENAMELIST_ITEM[$node-alt-index]/ContextVersion"/>
        <xsl:variable name="altname2" select="/DataElementsList/DataElement[$node-index]/ALTERNATENAMELIST/ALTERNATENAMELIST_ITEM[$node-alt-index]/AlternateName"/>
        <xsl:variable name="alttype2" select="/DataElementsList/DataElement[$node-index]/ALTERNATENAMELIST/ALTERNATENAMELIST_ITEM[$node-alt-index]/AlternateNameType"/>
        
       <xsl:if test="$node-alt-index >1 "><!--CN Checked-->  
       <xsl:value-of select="concat('; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',';',' ',$altconxname2,';',' ',$altconxversion2,';',' ',$altname2,';',' ',$alttype2,';',' ')"/><xsl:text>&#xd;</xsl:text> </xsl:if>   
      </xsl:for-each>
      
    <!--Calling 1st repeatable REFERENCEDOCUMENTSLIST-->
    
      <xsl:for-each select="/DataElementsList/DataElement[$node-index]/REFERENCEDOCUMENTSLIST/REFERENCEDOCUMENTSLIST_ITEM"> 
       <xsl:variable name="node-ref-index" select="count(preceding-sibling::REFERENCEDOCUMENTSLIST_ITEM)+1" />
        
         <xsl:variable name="doc2" select="/DataElementsList/DataElement[$node-index]/REFERENCEDOCUMENTSLIST/REFERENCEDOCUMENTSLIST_ITEM[$node-ref-index]/DocumentText"/>
         <xsl:variable name="docname2" select="/DataElementsList/DataElement[$node-index]/REFERENCEDOCUMENTSLIST/REFERENCEDOCUMENTSLIST_ITEM[$node-ref-index]/Name"/>
         <xsl:variable name="doctype2" select="/DataElementsList/DataElement[$node-index]/REFERENCEDOCUMENTSLIST/REFERENCEDOCUMENTSLIST_ITEM[$node-ref-index]/DocumentType"/>
         
         <xsl:if test="$node-ref-index >1 "><!--CN Checked-->           
         <xsl:value-of select="concat('; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ',$doc2,';',' ',$docname2,';',' ',$doctype2,';',' ')"/><xsl:text>&#xd;</xsl:text> </xsl:if>  
      
          </xsl:for-each>
          
          
          
    </xsl:for-each>
    
    </xsl:template>
    </xsl:stylesheet>