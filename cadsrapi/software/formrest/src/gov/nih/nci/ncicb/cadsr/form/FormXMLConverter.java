package gov.nih.nci.ncicb.cadsr.form;

import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;

import java.util.LinkedList;
import java.io.StringWriter;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;



import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;
import net.sf.saxon.TransformerFactoryImpl;

public class FormXMLConverter {
	private static Log log = LogFactory.getLog(FormXMLConverter.class.getName());
	
	static private FormXMLConverter _instance = null;
	public static final String path = "/local/content/cadsrapi/transform/xslt/";
	public static final String V1ExtendedToV2XSL = "ConvertFormCartV1ExtendedToV2.xsl";
	protected Transformer transformerV1ToV2 = null;
	
	public String convertFormToXML (FormV2 crf) throws MarshalException, ValidationException, TransformerException {

		// Start with our standard conversion to xml (in V1 format)
		StringWriter writer = new StringWriter();
		try {
			Marshaller.marshal(crf, writer);
		} catch (MarshalException ex) {
			log.debug("FormV2 " + crf);
			throw ex;
		} catch (ValidationException ex) {
			// need exception handling	
			log.debug("FormV2 " + crf);
			throw ex;
		}

		// Now use our transformer to create V2 format
		Source xmlInput = new StreamSource(new StringReader(writer.toString()));
		ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();  
		Result xmlOutput = new StreamResult(xmlOutputStream);
		try {
			transformerV1ToV2.transform(xmlInput, xmlOutput);
		} catch (TransformerException e) {
			log.debug(writer.toString());
			throw e;
		}	
				
		String V2XML = xmlOutputStream.toString();
		return V2XML;
	}
		
	protected FormXMLConverter() {
		
		StreamSource xslSource = null;
		try {
//			InputStream xslStream = this.getClass().getResourceAsStream(V1ExtendedToV2XSL); 
//			xslSource = new StreamSource(xslStream);
			File tf = new File(path,V1ExtendedToV2XSL);
			xslSource = new StreamSource(tf);
		}
		catch(Exception e) {
			System.out.println("FormConverterUtil error loading conversion xsl: " + V1ExtendedToV2XSL + " exc: "+ e);
		}
		
		try {
			log.debug("creating transformerV1ToV2");			
			transformerV1ToV2 = net.sf.saxon.TransformerFactoryImpl.newInstance().newTransformer(xslSource);
		} catch (TransformerException e) {
			log.debug("transformerV1ToV2 exception: " + e.toString());
			log.debug("transformerV1ToV2 exception: " + e.getMessage());
		}	
	} 
	 
	 
	static public FormXMLConverter instance(){
		if (_instance == null) {
			_instance = new FormXMLConverter();
		}
		return _instance;
	}
  
}

