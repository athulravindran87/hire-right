package com.hright.processor;

import com.hright.test.BaseTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.kafka.consumer.enabled=false"})
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

    private String getHost() {
        return "http://localhost:" + port;
    }

}
