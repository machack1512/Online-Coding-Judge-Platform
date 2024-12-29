package com.mycompany.myapp.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.web.utills.SpinRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PlayRequest {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/config")
    public ResponseEntity<String> getConfigData(@RequestBody List<Map<String, String>> jsonInput) {
        try {
            String url = "http://localhost:5000/getConfig";
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(jsonInput);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> externalResponse = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(externalResponse.getBody(), responseHeaders, externalResponse.getStatusCode());
        } catch (RestClientException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching data from external service");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
    @PostMapping("/newGame")
    public ResponseEntity<String> getNewGameData(@RequestBody List<Map<String, String>> jsonInput) {
        try {
            String url = "http://localhost:5000/newGame";
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(jsonInput);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> externalResponse = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(externalResponse.getBody(), responseHeaders, externalResponse.getStatusCode());
        } catch (RestClientException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching data from external service");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
    @PostMapping("/executeSpin")
    public ResponseEntity<String> getExecuteSpinData(@RequestBody List<SpinRequest> jsonInput) {
        System.out.println("Received request: " + jsonInput);
        try {
            String url = "http://localhost:5000/execute";
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(jsonInput);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> externalResponse = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity<>(externalResponse.getBody(), responseHeaders, externalResponse.getStatusCode());
        } catch (RestClientException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching data from external service");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
