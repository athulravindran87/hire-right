package com.hright.remote;

import com.amazonaws.services.s3.AmazonS3Client;
import com.hright.test.BaseTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AwsConfigTest extends BaseTest {

    private AwsConfig testObj;

    @Before
    public void setUp() throws Exception {
        this.testObj = new AwsConfig();
        ReflectionTestUtils.setField(this.testObj, "accessKey", "123");
        ReflectionTestUtils.setField(this.testObj, "secretAccessKey", "456");
        ReflectionTestUtils.setField(this.testObj, "bucketName", "bucket1");
        ReflectionTestUtils.setField(this.testObj, "regionName", "region1");
    }

    @Test
    public void getters() {
        assertThat(this.testObj.getAccessKey(), equalTo("123"));
        assertThat(this.testObj.getSecretAccessKey(), equalTo("456"));
        assertThat(this.testObj.getBucketName(), equalTo("bucket1"));
        assertThat(this.testObj.getRegionName(), equalTo("region1"));


    }

    @Test
    @Ignore
    public void amazonS3() {
        AmazonS3Client amazonS3Client = this.testObj.amazonS3();
        assertThat(amazonS3Client.getRegionName(), equalTo("region1"));
    }
}