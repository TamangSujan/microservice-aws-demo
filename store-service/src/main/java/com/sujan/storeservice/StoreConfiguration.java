package com.sujan.storeservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class StoreConfiguration {
    private static final Logger log = LoggerFactory.getLogger(StoreConfiguration.class);
    @Value("${s3.url}")
    private String s3Url;
    @Bean
    public S3Client s3Client() {
        log.info("S3 Url: "+s3Url);
        return S3Client.builder()
                .endpointOverride(URI.create(s3Url))
                .region(Region.US_EAST_1)
                .forcePathStyle(true)
                .build();
    }
}
