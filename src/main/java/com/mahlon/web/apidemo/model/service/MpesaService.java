/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mahlon.web.apidemo.model.service;


import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author mahlo
 */
@Service
public class MpesaService {

    private static final Logger log = Logger.getLogger(MpesaService.class.getName());
   
    
    public String getToken() {
         String consumerKey = "15b7uQQRwtQM37Hn53wsjkvsU8Y0dSwa";
        String consumerSecret = "09eTLRvjwCJUkdoA";
        String tokenEndpint = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";
        RestTemplate restTemplate = new RestTemplate();
        
        
        //@TODO: Put logic here to generate token from Safaricom token endpoint
        
        
      
        return null;
    }
   
    
    public String send() {
        RestTemplate restTemplate = new RestTemplate();
        
        String stkPushEndpoint = "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";
    String token = "uRdzmpESICNsoEltxlqUcpdkqQsD";
      
        String data = "{\n" +
"    \"BusinessShortCode\": 174379,\n" +
"    \"Password\": \"MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMjIwNTI0MjEzMTQ5\",\n" +
"    \"Timestamp\": \"20220524213149\",\n" +
"    \"TransactionType\": \"CustomerPayBillOnline\",\n" +
"    \"Amount\": 1,\n" +
"    \"PartyA\": 254745864994,\n" +
"    \"PartyB\": 174379,\n" +
"    \"PhoneNumber\": 254745864994,\n" +
"    \"CallBackURL\": \"https://mydomain.com/path\",\n" +
"    \"AccountReference\": \"Mahlon Agencies\",\n" +
"    \"TransactionDesc\": \"Payment of X\" \n" +
"  }";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, MediaType.ALL_VALUE);
            headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            headers.set("Authorization", "Bearer " + token);
            log.info("HEADERS:" + headers.toString());
            HttpEntity<?> entity = new HttpEntity<>(data, headers);
            log.info("SENDING POST REQUEST TO URL:" + stkPushEndpoint);
            String bodyStr = null;
            ResponseEntity<String> response = restTemplate.exchange(stkPushEndpoint, HttpMethod.POST, entity, String.class);
            if (response != null) {
                log.info("RESPONSE RECEIVED::..");
                if (response.getBody() != null) {
                    bodyStr = response.getBody();
                    log.info("Response body: " + response.getBody());
                }
                if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.ACCEPTED) {
                    log.info("Response ok: HTTP Status: " + response.getStatusCode().getReasonPhrase());
                } else {
                    log.info("Response NOT ok: HTTP Status: " + response.getStatusCode().getReasonPhrase());
                }
                if(bodyStr != null) {
                    return bodyStr;
                }
            } else {
                log.info("API Call NOT successful, could not get any Response");
            }
        } catch(HttpStatusCodeException e) {
            log.severe("Error occurred on calling API: " + e.getMessage());
            log.severe("HTTP status code: " + e.getStatusCode());
            log.severe("Response body: " + e.getResponseBodyAsString());
            e.printStackTrace(System.out);
        }
        return null;
    }

   
}
//CompanyXLTD