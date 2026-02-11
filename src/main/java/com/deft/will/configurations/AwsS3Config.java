package com.deft.will.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

@Configuration
public class AwsS3Config {

    @Bean
    public S3AsyncClient s3AsyncClient(@Value("${aws.access-key}") String accessKey,
                                       @Value("${aws.secret-key}") String secretKey){
          return S3AsyncClient.builder()
                   .region(Region.US_EAST_1)
                  .credentialsProvider(
                          StaticCredentialsProvider.create(
                                  AwsBasicCredentials.create(accessKey, secretKey)
                          )
                  )
                   .build();
    }
}
