<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="2.0">
    <xsl:output indent="yes" exclude-result-prefixes="xsi"/>
    <xsl:variable name="FILLDATE">0001-01-01T00:00:01</xsl:variable>
    <xsl:variable name="FILLPUBLICID">0000000</xsl:variable>
    <xsl:variable name="FILLVERSION">1.0</xsl:variable>

  <xsl:template match="/">
    <xsl:apply-templates select="*"/>
  </xsl:template>
  <xsl:template match="node()">
    <xsl:copy><xsl:apply-templates select="node()"/></xsl:copy>
  </xsl:template>
  <xsl:template match="BC4JDataElementTransferObject">
    <DataElement><xsl:apply-templates select="node()"/></DataElement>
  </xsl:template> 

 
</xsl:stylesheet>
