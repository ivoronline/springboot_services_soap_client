package com.ivoronline.springboot_services_soap_client;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

public class UtilXML {

  //=======================================================================================
  // DOCUMENT TO STRING
  //=======================================================================================
  public static String documentToString(final Document responseDoc) throws Exception {

    DOMSource          domSource = new DOMSource(responseDoc);
    StringWriter       stringWriter = new StringWriter();
    StreamResult       streamResult = new StreamResult(stringWriter);
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer        transformer;
                       transformer = transformerFactory.newTransformer();
                       transformer.transform(domSource, streamResult);

    return stringWriter.toString();

  }

  //================================================================================
  // STRING TO DOCUMENT
  //================================================================================
  public static Document stringToDocument(String xml) throws Exception {

    //READ XML STRING
    InputSource     inputSource     = new InputSource();
                    inputSource.setCharacterStream(new StringReader(xml));

    //CREATE DOCUMENT
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                           documentBuilderFactory.setNamespaceAware(true);
    DocumentBuilder        documentBuilder        = documentBuilderFactory.newDocumentBuilder();
    Document               document               = documentBuilder.parse(inputSource);

    //RETURN DOCUMENT
    return document;

  }

  //=======================================================================================
  // MARSHAL
  //=======================================================================================
  // Document document = UtilXML.marshal(document, invoiceRequest, InvoiceRequest.class);
  public static Document marshal(Object object, Class className) throws Exception {

    //CREATE REQUEST XML DOCUMENT
    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

    //CONVERT OBJECT TO DOCUMENT
    Marshaller  marshaller = JAXBContext.newInstance(className).createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                marshaller.marshal(object, document);

    //RETURN DOCUMENT
    return document;

  }

  //=======================================================================================
  // UNMARSHAL
  //=======================================================================================
  // InvoiceResponse invoiceResponse = (InvoiceResponse) UtilXML.unmarshal(document, InvoiceResponse.class);
  public static Object unmarshal(Document document, Class className) throws Exception {

    //CONVERT OBJECT TO DOCUMENT
    Object object = JAXBContext.newInstance(className).createUnmarshaller().unmarshal(document);

    //RETURN OBJECT
    return object;
  }

}
