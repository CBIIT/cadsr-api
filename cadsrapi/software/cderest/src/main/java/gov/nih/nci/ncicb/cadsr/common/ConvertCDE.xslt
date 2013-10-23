<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
 
    <xsl:output indent="yes"/>
     
    <xsl:template match="/"> 
          <DataElementsList>
          <xsl:for-each select="//BC4JDataElementTransferObject">
		   <xsl:variable name="index" select="count(preceding-sibling::BC4JDataElementTransferObject)+1" />
            <DataElement num="{$index}">
              <PUBLICID>
                  <xsl:value-of select="//BC4JDataElementTransferObject[$index]/@public-id"/>
              </PUBLICID>
              <LONGNAME>
                  <xsl:value-of select="//BC4JDataElementTransferObject[$index]/long-name"/>
              </LONGNAME>   
              <PREFERREDNAME>
                  <xsl:value-of select="//BC4JDataElementTransferObject[$index]/preferred-name"/>
              </PREFERREDNAME>
              <PREFERREDDEFINITION>
                  <xsl:value-of select="//BC4JDataElementTransferObject[$index]/preferred-definition"/>
              </PREFERREDDEFINITION>     
              <VERSION>
                  <xsl:value-of select="//BC4JDataElementTransferObject[$index]/version"/>
              </VERSION>
              <WORKFLOWSTATUS>
                  <xsl:value-of select="//BC4JDataElementTransferObject[$index]/asl-name"/>
              </WORKFLOWSTATUS>
              <CONTEXTNAME>
                  <xsl:value-of select="//BC4JDataElementTransferObject[$index]/context-name"/>
              </CONTEXTNAME>
              <CONTEXTVERSION>
                  <xsl:value-of select="//BC4JDataElementTransferObject[$index]/context/version"/>
              </CONTEXTVERSION>
              <ORIGIN>
                <xsl:choose>
                    <xsl:when test="//BC4JDataElementTransferObject[$index]/@is-published = 'true'">
                        <xsl:text>TRUE</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>FALSE</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
              </ORIGIN>
              <REGISTRATIONSTATUS>
                  <xsl:value-of select="//BC4JDataElementTransferObject[$index]/registration-status"/>
              </REGISTRATIONSTATUS>
           
          <xsl:call-template name="DATAELEMENTCONCEPT" /> 
          <xsl:call-template name="VALUEDOMAIN" /> 
          <xsl:call-template name="REFERENCEDOCUMENTSLIST" />    
          <xsl:call-template name="CLASSIFICATIONSLIST" /> 
          <xsl:call-template name="ALTERNATENAMELIST" />  
          <xsl:call-template name="DATAELEMENTDERIVATION" />
          </DataElement>  
          </xsl:for-each>                                                                
        </DataElementsList>
     </xsl:template>
     
     <!-- DATAELEMENTCONCEPT -->
     
	   <xsl:template  name="DATAELEMENTCONCEPT">
	        <DATAELEMENTCONCEPT>   
             <PublicId>
                 <xsl:value-of select="./data-element-concept/@public-id"/>
             </PublicId>
             <PreferredName>
                 <xsl:value-of select="./data-element-concept/preferred-name"/>
             </PreferredName>
             <PreferredDefinition>
                 <xsl:value-of select="./data-element-concept/preferred-definition"/>
             </PreferredDefinition>
             <LongName>
                 <xsl:value-of select="./data-element-concept/long-name"/>
             </LongName>
             <Version>
                 <xsl:value-of select="./data-element-concept/version"/>
             </Version>
             <WorkflowStatus>
                 <xsl:value-of select="./data-element-concept/asl-name"/>
             </WorkflowStatus>
             <ContextName>
                 <xsl:value-of select="./data-element-concept/context/name"/>
             </ContextName>
             <ContextVersion>
                 <xsl:value-of select="./data-element-concept/context/version"/>
             </ContextVersion>
             <ConceptualDomain>
                <PublicId>
                  <xsl:value-of select="./data-element-concept/@CDPublicId"/>
                </PublicId>
                <ContextName>
                  <xsl:value-of select="./data-element-concept/CDContextName"/>
                </ContextName>
                <ContextVersion>
                  <xsl:value-of select="./data-element-concept/context/version"/>
                </ContextVersion>
                <PreferredName>
                  <xsl:value-of select="./data-element-concept/CDPrefName"/>
                </PreferredName>
                <Version>
                 <xsl:value-of select="./data-element-concept/CDVersion"/>
                </Version>
                <LongName>
                  <xsl:value-of select="./data-element-concept/cd-long-name"/>
                </LongName>
            </ConceptualDomain>               
             
             <xsl:call-template name="ObjectClass" />
             <xsl:call-template name="Property" />
             
            </DATAELEMENTCONCEPT>
        </xsl:template>
        
        
        
        <!-- Child templates of DATAELEMENTCONCEPT start from here -->
        
        <xsl:template  name="Property">  
         <Property>
            <PublicId>
              <xsl:value-of select="./data-element-concept/property/@public-id"/>
            </PublicId>
            <ContextName>
              <xsl:value-of select="./data-element-concept/property/concept-derivation-rule/component-concepts/concept/context/name"/>
            </ContextName>
            <ContextVersion>
              <xsl:value-of select="./data-element-concept/property/concept-derivation-rule/component-concepts/concept/version"/>
            </ContextVersion>
            <PreferredName>
              <xsl:value-of select="./data-element-concept/property/concept-derivation-rule/component-concepts/concept/preferred-name"/>
            </PreferredName>
            <Version>
              <xsl:value-of select="./data-element-concept/property/concept-derivation-rule/component-concepts/concept/version"/>
            </Version>
            <LongName>
              <xsl:value-of select="./data-element-concept/property/concept-derivation-rule/component-concepts/concept/long-name"/>
            </LongName>
            <ConceptDetails>
               <ConceptDetails_ITEM>
                  <PREFERRED_NAME>
                     <xsl:value-of select="./data-element-concept/property/concept-derivation-rule/component-concepts/concept/preferred-name"/>
                  </PREFERRED_NAME>
                  <LONG_NAME>
                     <xsl:value-of select="./data-element-concept/property/concept-derivation-rule/component-concepts/concept/long-name"/>
                  </LONG_NAME>
                  <CON_ID>
                     <xsl:value-of select="./data-element-concept/property/concept-derivation-rule/component-concepts/concept/@public-id"/>
                  </CON_ID>
                  <DEFINITION_SOURCE>
                     <xsl:value-of select="./data-element-concept/property/concept-derivation-rule/component-concepts/concept/definition-source"/>
                  </DEFINITION_SOURCE>
                  <ORIGIN>
                     <xsl:value-of select="./data-element-concept/property/concept-derivation-rule/component-concepts/concept/origin"/>
                  </ORIGIN>
                  <EVS_SOURCE>
                     <xsl:value-of select="./data-element-concept/property/concept-derivation-rule/component-concepts/concept/evs-source"/>
                  </EVS_SOURCE>
                  <PRIMARY_FLAG_IND>
                    <xsl:value-of select="./data-element-concept/property/concept-derivation-rule/component-concepts/concept/latest-version-ind"/>
                  </PRIMARY_FLAG_IND>
                  <DISPLAY_ORDER>
                    <xsl:value-of select="./data-element-concept/property/concept-derivation-rule/component-concepts/@display-order"/>
                  </DISPLAY_ORDER>
               </ConceptDetails_ITEM>
            </ConceptDetails>
         </Property>
      </xsl:template>

	    
	    <xsl:template  name="ObjectClass">
	       <ObjectClass>
	        <PublicId>
	           <xsl:value-of select="data-element-concept/object-class/@public-id"/>
	        </PublicId>
             <ConceptDetails>
               <xsl:for-each select="data-element-concept/object-class/concept-derivation-rule/component-concepts">
		        <ConceptDetails_ITEM>
	            <ContextName>
	               <xsl:value-of select="concept/context/name"/>
	            </ContextName>
	            <ContextVersion>
	               <xsl:value-of select="concept/version"/>
	            </ContextVersion>
	            <PreferredName>
	               <xsl:value-of select="concept/preferred-name"/>
	            </PreferredName>
	            <Version>
	               <xsl:value-of select="concept/version"/>
	            </Version>
	            <LongName>
	               <xsl:value-of select="concept/long-name"/>
	            </LongName>
                  <PREFERRED_NAME>
                    <xsl:value-of select="concept/preferred-name"/>
                  </PREFERRED_NAME>
                  <LONG_NAME> 
                    <xsl:value-of select="concept/long-name"/>
                  </LONG_NAME>
                  <CON_ID>
                    <xsl:value-of select="concept/@public-id"/>
                  </CON_ID>
                  <DEFINITION_SOURCE>
                    <xsl:value-of select="concept/definition-source"/>
                  </DEFINITION_SOURCE>
                  <ORIGIN>
                    <xsl:value-of select="concept/origin"/>
                  </ORIGIN>
                  <EVS_SOURCE>
                     <xsl:value-of select="concept/evs-source"/>
                  </EVS_SOURCE>
                  <PRIMARY_FLAG_IND>
                     <xsl:value-of select="concept/latest-version-ind"/>
                  </PRIMARY_FLAG_IND>
                  <DISPLAY_ORDER>
                     <xsl:value-of select="@display-order"/>
                  </DISPLAY_ORDER>
                </ConceptDetails_ITEM>
             </xsl:for-each>
            </ConceptDetails> 
           </ObjectClass>  
	    </xsl:template>  
	
  <!-- End of DATAELEMENTCONCEPT -->
  
  <!-- VALUEDOMAIN -->
       <xsl:template  name="VALUEDOMAIN">
        <VALUEDOMAIN>
         <PublicId>
           <xsl:value-of select="./value-domain/@public-id"/>
         </PublicId>
         <PreferredName> 
           <xsl:value-of select="./value-domain/representation/preferred-name"/>
         </PreferredName>
         <PreferredDefinition>
           <xsl:value-of select="./value-domain/representation/concept-derivation-rule/component-concepts/concept/preferred-definition"/>
         </PreferredDefinition>
         <LongName>
           <xsl:value-of select="./value-domain/long-name"/>
         </LongName>
         <Version>
           <xsl:value-of select="./value-domain/version"/>
         </Version>
         <WorkflowStatus>
           <xsl:value-of select="./value-domain/asl-name"/>
         </WorkflowStatus>
         <ContextName>
           <xsl:value-of select="./value-domain/context/name"/>
         </ContextName>
         <ContextVersion>
           <xsl:value-of select="./value-domain/version"/>
         </ContextVersion>
 <!--         <ConceptualDomain>
            <PublicId>
               <xsl:value-of select="./value-domain/representation/concept-derivation-rule/component-concepts/concept/@public-id"/>
            </PublicId>
            <ContextName>
              <xsl:value-of select="./value-domain/representation/concept-derivation-rule/component-concepts/concept/context/name"/>
            </ContextName>
            <ContextVersion>
              <xsl:value-of select="./value-domain/representation/concept-derivation-rule/component-concepts/concept/version"/>
            </ContextVersion>
            <PreferredName>
              <xsl:value-of select="./value-domain/representation/concept-derivation-rule/component-concepts/concept/preferred-name"/>
            </PreferredName>
            <Version>
              <xsl:value-of select="./value-domain/representation/concept-derivation-rule/component-concepts/concept/version"/>
            </Version>
            <LongName>
              <xsl:value-of select="./value-domain/representation/concept-derivation-rule/component-concepts/concept/long-name"/>            
            </LongName>
         </ConceptualDomain>   -->
         <Datatype>
            <xsl:value-of select="./value-domain/datatype"/>
         </Datatype>
         <MaximumLength>
            <xsl:value-of select="./value-domain/max-length"/>
         </MaximumLength>
         
         <xsl:for-each select="./value-domain/representation">
          <Representation>
            <PublicId>
               <xsl:value-of select="@public-id"/>
            </PublicId>
            <ContextName>
               <xsl:value-of select="concept-derivation-rule/name"/>
            </ContextName>
            <PreferredName><xsl:value-of select="preferred-name"/></PreferredName>
            <Version><xsl:value-of select="version"/></Version>
            <LongName><xsl:value-of select="long-name"/></LongName>
            <ConceptDetails>
               <xsl:for-each select="concept-derivation-rule/component-concepts">
	               <ConceptDetails_ITEM>
            			<VERSION><xsl:value-of select="concept/version"/></VERSION>
            			<PREFERRED_NAME><xsl:value-of select="concept/preferred-name"/></PREFERRED_NAME>
	                  <LONG_NAME><xsl:value-of select="concept/long-name"/></LONG_NAME>
	                  <CON_ID><xsl:value-of select="concept/@public-id"/></CON_ID>
	                  <DEFINITION_SOURCE><xsl:value-of select="concept/definition-source"/></DEFINITION_SOURCE>
	                  <ORIGIN><xsl:value-of select="concept/origin"/></ORIGIN>
	                  <EVS_SOURCE><xsl:value-of select="concept/evs-source"/></EVS_SOURCE>
	                  <PRIMARY_FLAG_IND><xsl:value-of select="concept/latest-version-ind"/></PRIMARY_FLAG_IND>
	                  <DISPLAY_ORDER><xsl:value-of select="@display-order"/></DISPLAY_ORDER>
	               </ConceptDetails_ITEM>
               </xsl:for-each>
            </ConceptDetails>
          </Representation>
		  </xsl:for-each>
         <xsl:call-template name="PERMISSIBLEVALUESLIST" />
         <ValueDomainConcepts/>
       </VALUEDOMAIN>
    </xsl:template>
	<!--  End of VALUEDOMAIN -->   
	
	<!-- REFERENCEDOCUMENTSLIST -->
	<xsl:template  name="REFERENCEDOCUMENTSLIST">
	  <REFERENCEDOCUMENTSLIST>
	  	<xsl:for-each select="referece-docs">
         <xsl:variable name="node-rd-index" select="count(preceding-sibling::referece-docs)+1" />
	     <REFERENCEDOCUMENTSLIST_ITEM>
            <Name><xsl:value-of select="doc-name"/></Name>
            <DocumentType><xsl:value-of select="doc-type"/></DocumentType>
            <DocumentText><xsl:value-of select="doc-text"/></DocumentText>
            <Language><xsl:value-of select="./designations/language"/></Language>
            <DisplayOrder NULL = "display-order" >
              <xsl:choose>
                    <xsl:when test="@display-order = '0'">
                        <xsl:text>TRUE</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>FALSE</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>                 
            </DisplayOrder>
         </REFERENCEDOCUMENTSLIST_ITEM>	  
        </xsl:for-each> 
	  </REFERENCEDOCUMENTSLIST>	
	</xsl:template>
 <!-- End of REFERENCEDOCUMENTSLIST -->	

 <!-- Start CLASSIFICATIONSLIST -->
  
   <xsl:template  name="CLASSIFICATIONSLIST">
      <CLASSIFICATIONSLIST>
        <xsl:for-each select="classifications">
         <xsl:variable name="node-cc-index" select="count(preceding-sibling::classifications)+1" />
          <CLASSIFICATIONSLIST_ITEM>
             <ClassificationScheme>
               <PublicId><xsl:value-of select="class-scheme-item-id"/></PublicId>
               <ContextName><xsl:value-of select="../context-name"/></ContextName>
               <ContextVersion><xsl:value-of select="../context/version"/></ContextVersion>
                <PreferredName><xsl:value-of select="class-scheme-name"/></PreferredName>
               <Version><xsl:value-of select="cs-version"/></Version>
            </ClassificationScheme>
            <ClassificationSchemeItemName><xsl:value-of select="class-scheme-item-name"/></ClassificationSchemeItemName>
            <ClassificationSchemeItemType><xsl:value-of select="class-scheme-item-type"/></ClassificationSchemeItemType>
            <CsiPublicId><xsl:value-of select="class-scheme-item-id"/></CsiPublicId>
            <CsiVersion><xsl:value-of select="cs-version"/></CsiVersion>                           
          
          </CLASSIFICATIONSLIST_ITEM>
                    
        </xsl:for-each> 
     </CLASSIFICATIONSLIST>
  </xsl:template>    
  <!--  End CLASSIFICATIONSLIST -->
  
  <!-- Start ALTERNATENAMELIST -->
     <xsl:template  name="ALTERNATENAMELIST">
        <ALTERNATENAMELIST>
         <ALTERNATENAMELIST_ITEM>
            <ContextName><xsl:value-of select="./context-name"/></ContextName>
            <ContextVersion><xsl:value-of select="./context/version"/></ContextVersion>
            <AlternateName><xsl:value-of select="./designations/name"/></AlternateName>
            <AlternateNameType><xsl:value-of select="./designations/type"/></AlternateNameType>
            <Language><xsl:value-of select="./designations/language"/></Language>
         </ALTERNATENAMELIST_ITEM>
      </ALTERNATENAMELIST>	
   </xsl:template> 

 <!-- End ALTERNATENAMELIST --> 
 
 <!-- Start DATAELEMENTDERIVATION -->
    <xsl:template  name="DATAELEMENTDERIVATION">
       <DATAELEMENTDERIVATION>
         <DerivationType><xsl:value-of select="./derived-data-element/type/name"/></DerivationType>         
         <Methods><xsl:value-of select="./derived-data-element/methods"/></Methods>
         <Rule><xsl:value-of select="./derived-data-element/rule"/></Rule>
         <ConcatenationCharacter><xsl:value-of select="./derived-data-element/concatenation-character"/></ConcatenationCharacter>
         <ComponentDataElementsList>
            <xsl:for-each select="./derived-data-element/data-element-derivation">
                <xsl:variable name="node-dd-index" select="count(preceding-sibling::data-element-derivations)+1" />                   
                     <ComponentDataElementsList_ITEM>
                        <PublicId><xsl:value-of select="//data-element-derivation[$node-dd-index]/derived-data-element/@public-id"/></PublicId>
                        <LongName><xsl:value-of select="//data-element-derivation[$node-dd-index]/derived-data-element/long-name"/></LongName>
                        <Version><xsl:value-of select="//data-element-derivation[$node-dd-index]/derived-data-element/version"/></Version>
                        <WorkflowStatus><xsl:value-of select="//data-element-derivation[$node-dd-index]/derived-data-element/asl-name"/></WorkflowStatus>
                        <ContextName><xsl:value-of select="//data-element-derivation[$node-dd-index]/derived-data-element/context-name"/></ContextName>
                        <DisplayOrder><xsl:value-of select="//data-element-derivation[$node-dd-index]/@display-order"/></DisplayOrder>                            
                     </ComponentDataElementsList_ITEM>               
            </xsl:for-each>       
       </ComponentDataElementsList>
       </DATAELEMENTDERIVATION>  
   </xsl:template>
 
  <!-- Start PERMISSIBLEVALUESLIST -->
  
   <xsl:template  name="PERMISSIBLEVALUESLIST">
      <PermissibleValues>
        <xsl:for-each select="value-domain/valid-values">
          <xsl:variable name="node-pv-index" select="count(preceding-sibling::valid-values)+1" />
          <PermissibleValues_ITEM>
             <VALIDVALUE><xsl:value-of select="short-meaning-value"/></VALIDVALUE>
               <VALUEMEANING><xsl:value-of select="short-meaning"/></VALUEMEANING>
               <MEANINGDESCRIPTION><xsl:value-of select="value-meaning/preferred-definition"/></MEANINGDESCRIPTION>
               <MEANINGCONCEPTS>
                   <xsl:choose>
                    <xsl:when test="@is-published = 'true'">
                        <xsl:text>TRUE</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>FALSE</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>               
               </MEANINGCONCEPTS>
               <VMPUBLICID><xsl:value-of select="value-meaning/@public-id"/></VMPUBLICID>
               <VMVERSION><xsl:value-of select="value-meaning/version"/></VMVERSION>                          
          </PermissibleValues_ITEM>
        </xsl:for-each> 
     </PermissibleValues>
  </xsl:template>    
  <!--  End PERMISSIBLEVALUESLIST -->
 
 
 
</xsl:stylesheet>