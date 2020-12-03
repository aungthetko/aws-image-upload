package com.atk.awsimageupload.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

    private static final String ACCESS_KEY = "AKIAJWZFEQ7XAIDPA4AA";
    private static final String SECRET_KEY = "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";

    @Bean
    public AmazonS3 s3(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                ACCESS_KEY,
                SECRET_KEY
        );
        return AmazonS3ClientBuilder
                .standard()
                .withRegion("ap-southeast-1")
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
