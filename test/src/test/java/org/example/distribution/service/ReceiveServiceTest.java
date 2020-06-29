package org.example.distribution.service;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.example.DemoApplication;
import org.example.distribution.domain.Distribution;
import org.example.distribution.exception.ReceiveException;
import org.example.distribution.sdo.DistributionCdo;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReceiveServiceTest {
    @Autowired
    private DistributionService distributionService;

    @Autowired
    private ReceiveService receiveService;

    private static int ownerUser = 999;

    private static int validUser1 = 111;

    private static int validUser2 = 222;

    private static int validUser3 = 333;

    private static int validUser4 = 444;

    private static String roomKey = "room-111";

    private static String invalidRoomKey = "room-222";

    private Distribution distribution;

    @Before
    public void distribution() {
        String distributionId = distributionService.distribution(ownerUser, roomKey, new DistributionCdo(3000, 3));
        distribution = distributionService.findDistribution(distributionId);
    }

    @Test(expected = ReceiveException.class)
    public void test1_validate_has_received() {
        //실패 Case - 이미 받은 사용자
        receiveService.receive(validUser1, distribution);
        receiveService.validateReceive(validUser1, roomKey, distribution);
    }

    @Test(expected = ReceiveException.class)
    public void test2_validate_owner() {
        //실패 Case - 뿌리기 한 사용자
        receiveService.validateReceive(ownerUser, roomKey, distribution);
    }

    @Test(expected = ReceiveException.class)
    public void test3_validate_room() {
        //실패 Case - 다른 방 Key
        receiveService.validateReceive(validUser1, invalidRoomKey, distribution);
    }

    @Test(expected = ReceiveException.class)
    public void test4_validate_expired_date() {
        //실패 Case - 받기 시간 만료
        long createdTime = distribution.getCreatedAt();
        long expiredTime = DateUtils.addMinutes(new Date(createdTime), -11).getTime();
        Distribution invalidDistribution = new Distribution(
            distribution.getUserKey(),
            distribution.getRoomKey(),
            distribution.getToken(),
            distribution.getPrice(),
            distribution.getUsersCount(),
            expiredTime
        );

        receiveService.validateReceive(validUser1, roomKey, invalidDistribution);
    }

    @Test(expected = ReceiveException.class)
    public void test5_receive_ended() {
        //실패 Case - 뿌리기 건의 모든 받기 완료
        receiveService.receive(validUser1, distribution);
        receiveService.receive(validUser2, distribution);
        receiveService.receive(validUser3, distribution);
        receiveService.receive(validUser4, distribution);
    }
}
