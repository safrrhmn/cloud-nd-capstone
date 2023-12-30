package com.saifurtech.locale;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class DynamoDBConfig {

    @Autowired
    private AwsSecretReader awsSecretReader;

    @Value("${amazon.aws.region}")
    private String region;

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;
    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(dynamoDBMapperConfig());
    }

    private AmazonDynamoDB dynamoDBMapperConfig() {
        Map<String, String> secrets = awsSecretReader.getSecrets();
        return AmazonDynamoDBAsyncClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder
                        .EndpointConfiguration(amazonDynamoDBEndpoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(secrets.get("amazon.aws.accesskey"),
                                secrets.get("amazon.aws.secretkey"))))
                .build();
    }


}
