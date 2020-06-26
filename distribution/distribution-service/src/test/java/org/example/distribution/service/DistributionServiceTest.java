package org.example.distribution.service;

import static org.junit.Assert.assertEquals;

import org.example.distribution.sdo.DistributionCdo;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DistributionServiceTest {
    private RestTemplate restTemplate;

    private HttpHeaders requestHeaders;

    private HttpEntity<Object> requestEntity;

    private final String BASE_API_URL = "http://localhost:8080/distributions";

    private int ownerUser = 999;

    private String roomKey = "room-111";

    @Before
    public void before() {
        restTemplate = new RestTemplate();
        requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("X-USER-ID", String.valueOf(ownerUser));
        requestHeaders.add("X-ROOM-ID", roomKey);
    }

    @Test
    public void test1_distribution() {
        requestEntity = new HttpEntity(new DistributionCdo(3000, 4), requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_API_URL, requestEntity, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }
}
