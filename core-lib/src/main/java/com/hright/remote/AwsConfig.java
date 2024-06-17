package com.hright.remote;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AwsConfig {

    @Value("${aws.s3.resume-bucket.accessKey}")
    private String accessKey;

    @Value("${aws.s3.resume-bucket.secretAccessKey:}")
    private String secretAccessKey;

    @Value("${aws.s3.resume-bucket.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.resume-bucket.region}")
    private String regionName;

    @Bean(value = "resumeS3Client")
    public AmazonS3Client amazonS3() {

        AWSCredentials awsCredentials = new BasicAWSCredentials(this.accessKey, this.secretAccessKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(this.regionName)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withPathStyleAccessEnabled(true)
                .build();
    }
}
