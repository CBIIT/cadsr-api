package gov.nih.nci.ncicb.cadsr.common;

import gov.nih.nci.ncicb.cadsr.common.resource.DataElement;

import java.util.LinkedList;
import java.util.List;
import java.io.StringWriter;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;




import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;
import net.sf.saxon.TransformerFactoryImpl;

public class CDEXMLConverter
{
	private static Log log = LogFactory.getLog(CDEXMLConverter.class.getName());

	static private CDEXMLConverter _instance = null;
	public static final String CDEFormatStyleSheet = "ConvertCDE.xslt";
	protected Transformer cdeTransformer = null;

	public String convertFormToXML (List<DataElement> listDE) throws MarshalException, ValidationException, TransformerException
	{
		StringWriter writer = new StringWriter();
		try
		{
			Marshaller.marshal(listDE, writer);
		}
		catch (MarshalException ex)
		{
			log.debug("CDEV2 " + listDE);
			throw ex;
		} catch (ValidationException ex) {
			// need exception handling
			log.debug("CDEV2 " + listDE);
			throw ex;
		}

		// Now use our transformer to create V2 format
		Source xmlInput = new StreamSource(new StringReader(writer.toString()));
		//Source xmlInput = new StreamSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?><BC4JDataElementTransferObject public-id=\"2183222\" is-published=\"false\"><value-domain xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" CDPublicId=\"0\" public-id=\"0\" is-published=\"false\" xsi:type=\"java:gov.nih.nci.ncicb.cadsr.common.dto.ValueDomainTransferObject\"><vd-idseq>D8307BCB-408D-2325-E034-0003BA12F5E7</vd-idseq></value-domain><long-cDEName>Alpha DVG Blood Pressure, Diastolic</long-cDEName><using-contexts></using-contexts><context-name>DCP</context-name><de-idseq>FAA03445-BB9E-0301-E034-0003BA3F9857</de-idseq><CDEId>2183222</CDEId><data-element-concept xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" CDPublicId=\"0\" public-id=\"0\" is-published=\"false\" xsi:type=\"java:gov.nih.nci.ncicb.cadsr.common.dto.DataElementConceptTransferObject\"><dec-idseq>D536CC36-DCAD-2CF4-E034-0003BA12F5E7</dec-idseq><idseq>D536CC36-DCAD-2CF4-E034-0003BA12F5E7</idseq></data-element-concept><long-name>Alpha DVG Blood Pressure, Diastolic</long-name><context xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"java:gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject\"><conte-idseq>A932C6E7-82EE-67C2-E034-0003BA12F5E7</conte-idseq></context><version>2.0</version><idseq>FAA03445-BB9E-0301-E034-0003BA3F9857</idseq><registration-status>Qualified</registration-status><preferred-name>ALPHA_DVG_BPD</preferred-name><asl-name>RELEASED</asl-name><preferred-definition>Oracle\"s Discrete Value Group (DVG) for a diastolic blood pressure that may not be obtained.</preferred-definition></BC4JDataElementTransferObject>"));

		ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
		Result xmlOutput = new StreamResult(xmlOutputStream);
		try {
			cdeTransformer.transform(xmlInput, xmlOutput);
		} catch (TransformerException e) {
			log.debug(writer.toString());
			throw e;
		}

		String V2XML = xmlOutputStream.toString();
		System.out.println("X2XML::");
		return V2XML;
		//return writer.toString();
	}

	protected CDEXMLConverter()
	{
		StreamSource xslSource = null;
		try
		{
			InputStream xslStream = this.getClass().getResourceAsStream(CDEFormatStyleSheet);
			xslSource = new StreamSource(xslStream);
		}
		catch(Exception e) {
			System.out.println("CDEXMLConverter error loading conversion xsl: " + CDEFormatStyleSheet + " exc: "+ e);
		}
		try
		{
			log.debug("creating transformerV1ToV2");
			cdeTransformer = net.sf.saxon.TransformerFactoryImpl.newInstance().newTransformer(xslSource);
		} catch (TransformerException e) {
			log.debug("transformerV1ToV2 exception: " + e.toString());
			log.debug("transformerV1ToV2 exception: " + e.getMessage());
		}
	}

	static public CDEXMLConverter instance(){
		if (_instance == null) {
			_instance = new CDEXMLConverter();
		}
		return _instance;
	}

	public static void main(String args[])
	{
		List<DataElement> de = null;
		try {
			System.out.println(CDEXMLConverter.instance().convertFormToXML(de));
		} catch (MarshalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
