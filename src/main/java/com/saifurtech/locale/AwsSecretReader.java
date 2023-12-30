package com.saifurtech.locale;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.util.Map;

@Component
public class AwsSecretReader {
    @Value("${amazon.aws.region}")
    private String region;

    @Value("${amazon.aws.secret.name}")
    private String secretName;

    public Map<String, String> getSecrets() {

        Region re = Region.of(region);

        // Create a Secrets Manager client
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(re)
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't got secret from AWS secret managers. {}", e);
        }

        String secret = getSecretValueResponse.secretString();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(secret, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
