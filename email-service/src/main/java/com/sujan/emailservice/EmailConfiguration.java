package com.sujan.emailservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

import java.net.URI;

@Configuration
public class EmailConfiguration {
    @Value("${ses.url}")
    private String sesUrl;
    @Bean
    public SesClient sesClient(){
        /*
        * SSL needs to be created in production, below credential provider is for local development use
        */
        return SesClient.builder()
                .endpointOverride(URI.create(sesUrl))
                .region(Region.US_EAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(AwsBasicCredentials.create("email-test", "email-test"))
                )
                .build();
    }
}
