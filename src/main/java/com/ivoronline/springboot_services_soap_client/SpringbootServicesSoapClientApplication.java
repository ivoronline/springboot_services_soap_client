package com.ivoronline.springboot_services_soap_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootServicesSoapClientApplication {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(SpringbootServicesSoapClientApplication.class, args);

    //CALL SERVER
    String response = PersonClient.getPerson();
    System.out.println(response);

  }

}
