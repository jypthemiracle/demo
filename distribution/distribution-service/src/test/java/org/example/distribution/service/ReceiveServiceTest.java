package org.example.distribution.service;

import static org.junit.Assert.assertEquals;

import org.example.distribution.sdo.DistributionCdo;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReceiveServiceTest {
    private static RestTemplate restTemplate;

    private static HttpHeaders requestHeaders;

    private static HttpEntity<Object> requestEntity;

    private static final String BASE_API_URL = "http://localhost:8080/distributions";

    private static int ownerUser = 999;

    private static int user1 = 111;

    private static int user2 = 222;

    private static int user3 = 333;

    private static int user4 = 444;

    private static String roomKey = "room-111";

    private static String invalidRoomKey = "room-222";

    private static String distributionId;

    @BeforeClass
    public static void beforeClass() {
        restTemplate = new RestTemplate();
        requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("X-USER-ID", String.valueOf(ownerUser));
        requestHeaders.add("X-ROOM-ID", roomKey);
        requestEntity = new HttpEntity(new DistributionCdo(3000, 4), requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_API_URL, requestEntity, String.class);
        distributionId = responseEntity.getBody();
    }

    @Before
    public void before() {
        restTemplate = new RestTemplate();
        requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("X-USER-ID", String.valueOf(ownerUser));
        requestHeaders.add("X-ROOM-ID", roomKey);
    }

    @Test
    public void test1_receive_success() {
        //정상 Case
        requestHeaders.set("X-USER-ID", String.valueOf(user1));
        requestEntity = new HttpEntity(requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_API_URL + "/" + distributionId + "/receive", HttpMethod.PUT, requestEntity, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void test2_receive_fail() {
        //실패 Case - 이미 받은 사용자
        requestHeaders.set("X-USER-ID", String.valueOf(user1));
        requestEntity = new HttpEntity(requestHeaders);
        restTemplate.exchange(BASE_API_URL + "/" + distributionId + "/receive", HttpMethod.PUT, requestEntity, String.class);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void test3_receive_fail() {
        //실패 Case - 뿌리기 한 사용자
        requestHeaders.set("X-USER-ID", String.valueOf(ownerUser));
        requestEntity = new HttpEntity(requestHeaders);
        restTemplate.exchange(BASE_API_URL + "/" + distributionId + "/receive", HttpMethod.PUT, requestEntity, String.class);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void test4_receive_fail() {
        //실패 Case - 다른 방 Key
        requestHeaders.set("X-ROOM-ID", invalidRoomKey);
        requestEntity = new HttpEntity(requestHeaders);
        restTemplate.exchange(BASE_API_URL + "/" + distributionId + "/receive", HttpMethod.PUT, requestEntity, String.class);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void test5_receive_fail() {
        //실패 Case - 뿌리기 건의 모든 받기 완료
        requestHeaders.set("X-USER-ID", String.valueOf(user1));
        requestEntity = new HttpEntity(requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_API_URL + "/" + distributionId + "/receive", HttpMethod.PUT, requestEntity, String.class);
        System.out.println(responseEntity.getBody());

        requestHeaders.set("X-USER-ID", String.valueOf(user2));
        requestEntity = new HttpEntity(requestHeaders);
        responseEntity = restTemplate.exchange(BASE_API_URL + "/" + distributionId + "/receive", HttpMethod.PUT, requestEntity, String.class);
        System.out.println(responseEntity.getBody());

        requestHeaders.set("X-USER-ID", String.valueOf(user3));
        requestEntity = new HttpEntity(requestHeaders);
        responseEntity = restTemplate.exchange(BASE_API_URL + "/" + distributionId + "/receive", HttpMethod.PUT, requestEntity, String.class);
        System.out.println(responseEntity.getBody());

        requestHeaders.set("X-USER-ID", String.valueOf(user4));
        requestEntity = new HttpEntity(requestHeaders);
        responseEntity = restTemplate.exchange(BASE_API_URL + "/" + distributionId + "/receive", HttpMethod.PUT, requestEntity, String.class);
    }
}
