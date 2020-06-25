package com.hright.parser;

import com.hright.test.BaseTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"eureka.client.enabled=false",
                "spring.cloud.config.enabled=false",
                "myhrp.hazelcast.client.enabled=false"})
@ActiveProfiles("local")

public class ApplicationIT extends BaseTest {
    private RestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @BeforeClass
    public static void setUp() throws Exception {
        Thread.sleep(10000);
    }

    @Test
    public void testHealthCheck() throws Exception {
        restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity(getHost() + "/actuator/health", String.class);
        assertThat(result.getBody(), startsWith("{\"status\":\"UP\""));
    }

    @Test
    public void testFileUpload() throws Exception {
        restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(ResourceUtils.getFile("classpath:testdata/Archive.zip")));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(getHost() + "/upload", requestEntity, String.class);
        assertThat(responseEntity.getBody(), equalTo("File Upload successful for total of 5 file(s)"));
    }

    private String getHost() {
        return "http://localhost:" + port;
    }

}
