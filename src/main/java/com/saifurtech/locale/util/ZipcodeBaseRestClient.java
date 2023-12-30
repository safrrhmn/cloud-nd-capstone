package com.saifurtech.locale.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Configuration
public class ZipcodeBaseRestClient {

    @Value("${zipcodebase.apikey}")
    private String apikey;


    @Autowired
    RestTemplate restTemplate;

    public <T> ResponseEntity<T> get(String url, Map<String, String> params, T responseType) {
        RequestEntity<?> requestEntity = null;
        try {
            HttpHeaders header = new HttpHeaders();
            header.set("apiKey", apikey);
            requestEntity = new RequestEntity<>(header, HttpMethod.GET, new URI(url));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }
        ResponseEntity<?> exchange = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET, requestEntity, responseType.getClass());

        return (ResponseEntity<T>) exchange;
    }

}
