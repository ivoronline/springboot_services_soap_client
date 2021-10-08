package com.ivoronline.springboot_services_soap_client;

import com.ivoronline.soap.GetPersonRequest;
import com.ivoronline.soap.GetPersonResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import java.net.URI;

public class PersonClient {

  //PROPERTIES
  static String serverURL = "http://localhost:8080/GetPerson";

  //============================================================================================
  // GET PERSON
  //============================================================================================
  public static String getPerson() throws Exception {

    //CREATE REQUEST OBJECT
    GetPersonRequest    requestObject       = new GetPersonRequest();
                        requestObject.setId(1);

    //MARSHAL REQUEST OBJECT
    Document            requestXMLDocument  = UtilXML.marshal(requestObject, GetPersonRequest.class);
    Document            requestSOAPDocument = UtilSOAP.createSOAPDocument(requestXMLDocument);
    String              requestSOAPString   = UtilXML.documentToString(requestSOAPDocument);

    //CONFIGURE REQUEST PARAMETERS
    URI                 serverURI           = new URI(serverURL);
    HttpHeaders         headers             = new HttpHeaders();
                        headers.set("Content-Type", "text/xml;charset=UTF-8");
    HttpEntity<String>  entity              = new HttpEntity<>(requestSOAPString, headers);

    //SEND  REQUEST
    RestTemplate        restTemplate        = new RestTemplate();
    String              responseSOAPString  = restTemplate.postForObject(serverURI, entity, String.class);

    //UNMARSHAL RESPONSE XML INTO OBJECT
    Document            responseXMLDocument = UtilSOAP.extractSOAPBody(responseSOAPString);
    GetPersonResponse   responseObject      = (GetPersonResponse) UtilXML.unmarshal(responseXMLDocument, GetPersonResponse.class);

    //GET PERSON NAME
    String              name                = responseObject.getName();

    //DISPLAY REQUEST & RESPONSE
    System.out.println(requestSOAPString );
    System.out.println(responseSOAPString);

    //RETURN RESPONSE OBJECT
    return "Hello " + name;

  }

}
