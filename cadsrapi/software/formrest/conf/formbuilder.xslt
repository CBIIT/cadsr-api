<?xml version='1.0'?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="text"/>
	
<xsl:template match="/">
<xsl:for-each select="/formCollection/form">
<xsl:variable name="node-form" select="count(preceding-sibling::form)+1" />

<xsl:text>Long Name; </xsl:text><xsl:value-of select="/formCollection/form[$node-form]/longName"/><xsl:text>&#xd;</xsl:text>
<xsl:text>Definition; </xsl:text><xsl:value-of select="/formCollection/form[$node-form]/preferredDefinition"/><xsl:text>&#xd;</xsl:text>
<xsl:text>Context; </xsl:text><xsl:value-of select="/formCollection/form[$node-form]/context"/><xsl:text>&#xd;</xsl:text>
<xsl:text>Protocol Long Name; </xsl:text><xsl:value-of select="/formCollection/form[$node-form]/protocol/longName"/><xsl:text>&#xd;</xsl:text>
<xsl:text>Workflow; </xsl:text><xsl:value-of select="/formCollection/form[$node-form]/workflowStatusName"/><xsl:text>&#xd;</xsl:text>
<xsl:text>Type; </xsl:text><xsl:value-of select="/formCollection/form[$node-form]/type"/><xsl:text>&#xd;</xsl:text>
<xsl:text>Public ID; </xsl:text><xsl:value-of select="/formCollection/form[$node-form]/publicID"/><xsl:text>&#xd;</xsl:text>
<xsl:text>Version; </xsl:text><xsl:value-of select="/formCollection/form[$node-form]/version"/><xsl:text>&#xd;</xsl:text>
<xsl:text>Footer Instruction; </xsl:text><xsl:value-of select="/formCollection/form[$node-form]/footerInstruction/text"/><xsl:text>&#xd;</xsl:text>
<xsl:text></xsl:text><xsl:text>&#xd;</xsl:text>
<xsl:text>Module Long Name;	Module Instructions;	Number of Repetitions;	Question;	CDE;	CDE Public ID;	CDE Version;	Question Instructions;	Answer is Mandatory;	Question Default Value;	Value Domain Long Name;	Value Domain Data Type;	Value Domain Unit of Measure;	Display Format;	Concepts;	Valid Value;	Form Value Meaning Text;	Form Value Meaning Public ID Version;	Form Value Meaning Desc.;	Valid Value Instructions;	Module Preferred Name;	Module Preferred Definition;	Module Public Id;	Module Version;	Module Display Order</xsl:text>


<!-- Module -->  
<xsl:for-each select="/formCollection/form/module">
	    <xsl:variable name="node-index" select="count(preceding-sibling::module)+1" />
	    
        <xsl:variable name="lname" select="/formCollection/form/module[$node-index]/longName"/>
        <xsl:variable name="Instruct" select="/formCollection/form/module[$node-index]/instruction/text"/>
        <xsl:variable name="norep" select="/formCollection/form/module[$node-index]/maximumModuleRepeat"/>     
          
<xsl:text>&#xd;</xsl:text>   
<xsl:value-of select="concat($lname,';',' ',$Instruct,';',' ',$norep)"/><xsl:text> ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; </xsl:text>    
 
        <xsl:variable name="pname" />
        <xsl:variable name="pdefinition" select="/formCollection/form[$node-form]/module[$node-index]/preferredDefinition"/>
        <xsl:variable name="pubid" select="/formCollection/form[$node-form]/module[$node-index]/publicID"/>     
        <xsl:variable name="version" select="/formCollection/form[$node-form]/module[$node-index]/version"/> 
        <xsl:variable name="order" select="/formCollection/form[$node-form]/module[$node-index]/displayOrder"/> 

<xsl:value-of select="concat($pname,';',' ',$pdefinition,';',' ',$pubid,';',' ',$version,';',' ',$order)"/><xsl:text>&#xd;</xsl:text>
<!--Question -->
   <xsl:for-each select="/formCollection/form[$node-form]/module[$node-index]/question">
      <xsl:variable name="node-q-index" select="count(preceding-sibling::question)+1" />
        <xsl:variable name="question" select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/questionText"/>
        <xsl:variable name="cde" select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/dataElement/longName"/>     
        <xsl:variable name="cdepid" select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/dataElement/publicID"/> 
        <xsl:variable name="cdeversion" select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/dataElement/version"/>
        <xsl:variable name="instructions" select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/instruction/text"/>
        <xsl:variable name="mandatory" select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/isMandatory"/>
        <xsl:variable name="default" select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/defaultValue"/>
        <xsl:variable name="vdomlname" select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/dataElement/valueDomain/longName"/>
        <xsl:variable name="vdatatype" select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/dataElement/valueDomain/datatypeName"/>
        <xsl:variable name="vunit" select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/dataElement/valueDomain/UOMName"/>
        <xsl:variable name="vformat" select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/dataElement/valueDomain/formatName"/>
        <xsl:variable name="vconcepts" select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/dataElement/valueDomain/referenceDocument"/>
                
        <xsl:value-of select="concat('; ','; ','; ',$question,';',' ',$cde,';',' ',$cdepid,';',' ',$cdeversion,';',' ',$instructions,';',' ',$mandatory,';',' ',$default,';',' ',$vdomlname,';',' ',$vdatatype,';',' ',$vunit,';',' ',$vformat,';',' ',$vconcepts)"/><xsl:text>&#xd;</xsl:text>
        <!--Valid Value -->
        <xsl:for-each select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/validValue">
           <xsl:variable name="node-v-index" select="count(preceding-sibling::validValue)+1" />
              <xsl:variable name="vavalue" select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/validValue[$node-v-index]/value"/>
              <xsl:variable name="formvameaning" select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/validValue[$node-v-index]/meaningText"/>
              <xsl:variable name="formvaidversion" select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/dataElement/valueDomain/permissibleValue/valueMeaning/version" />
              <xsl:variable name="formvadesc" select="/formCollection/form[$node-form]/module[$node-index]/question[$node-q-index]/validValue[$node-v-index]/description"/>
        
        
        
         <xsl:value-of select="concat('; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ','; ',$vavalue,';',' ',$formvameaning,';',' ',$formvaidversion,';',' ',$formvadesc)"/><xsl:text>&#xd;</xsl:text>
         </xsl:for-each>
         
</xsl:for-each>
   
   
   
   
</xsl:for-each>
</xsl:for-each>     
</xsl:template>

</xsl:stylesheet>
