package edu.ucsb.cs156.spring.backenddemo.services;

import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class PublicHolidayQueryService {


    private final RestTemplate restTemplate;

    public PublicHolidayQueryService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public static final String ENDPOINT = "https://date.nager.at/api/v2/publicholidays/{year}/{countryCode}?format=json";

    public String getJSON(String year, String countryCode) throws HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        Map<String, String> uriVariables = Map.of("year", year, "countryCode", countryCode);
        ResponseEntity<String> response = restTemplate.exchange(ENDPOINT, HttpMethod.GET, entity, String.class, uriVariables);

        if(response.getStatusCode() == HttpStatusCode.valueOf(200))
            return response.getBody();
        
        //If we didn't get a 2XX response
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Ensure the year: " + year + " | and the country code: " + countryCode + " | are valid.");
    }

   

}