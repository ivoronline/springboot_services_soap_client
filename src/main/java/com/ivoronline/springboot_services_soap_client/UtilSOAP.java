
package com.ivoronline.springboot_services_soap_client;

import org.w3c.dom.Document;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class UtilSOAP {

  //=======================================================================================
  // EXTRACT SOAP BODY
  //=======================================================================================
  public static Document extractSOAPBody(String response) throws Exception {
    InputStream inputStream = new ByteArrayInputStream(response.getBytes());
    SOAPMessage responseXML = MessageFactory.newInstance().createMessage(null, inputStream);
    Document    responseDocument = responseXML.getSOAPBody().extractContentAsDocument();
    return      responseDocument;
  }

  //=======================================================================================
  // CREATE SOAP DOCUMENT
  //=======================================================================================
  public static Document createSOAPDocument(Document document) throws Exception {

    //ADD SOAP ENVELOPE
    SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
                soapMessage.getSOAPBody().addDocument(document);

    //CONVER SOAP MESSAGE TO DOCUMENT
    Document    soapDocument = SOAPToDocument(soapMessage);

    //RETURN SOAP DOCUMENT
    return soapDocument;

  }

  //=======================================================================================
  // SOAP TO DOCUMENT
  //=======================================================================================
  public static Document SOAPToDocument(SOAPMessage soapMessage) throws Exception {

    Source             src         = soapMessage.getSOAPPart().getContent();
    TransformerFactory tf          = TransformerFactory.newInstance();
    Transformer        transformer = tf.newTransformer();
    DOMResult          result      = new DOMResult();
                       transformer.transform(src, result);
    Document           document    = (Document) result.getNode();

    return document;

  }

}
