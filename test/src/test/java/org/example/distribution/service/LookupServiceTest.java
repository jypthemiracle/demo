package org.example.distribution.service;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.example.DemoApplication;
import org.example.distribution.domain.Distribution;
import org.example.distribution.exception.LookupException;
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
public class LookupServiceTest {
    @Autowired
    private DistributionService distributionService;

    @Autowired
    private LookupService lookupService;

    private int ownerUser = 999;

    private int invalidUser = 111;

    private String roomKey = "room-111";

    private String invalidRoomKey = "room-222";

    private Distribution distribution;

    @Before
    public void distribution() {
        String distributionId = distributionService.distribution(ownerUser, roomKey, new DistributionCdo(3000, 4));
        distribution = distributionService.findDistribution(distributionId);
    }

    @Test
    public void test1_validate_success() {
        //정상 Case
        lookupService.validate(ownerUser, roomKey, distribution);
        assertTrue(true);
    }

    @Test(expected = LookupException.class)
    public void test2_validate_owner() {
        //실패 Case - 뿌리기 한 사용자가 아님
        lookupService.validate(invalidUser, roomKey, distribution);
    }

    @Test(expected = LookupException.class)
    public void test3_validate_room() {
        //실패 Case - 다른 방 Key
        lookupService.validate(ownerUser, invalidRoomKey, distribution);
    }

    @Test(expected = LookupException.class)
    public void test4_validate_expired_date() {
        //실패 Case - 조회 시간 만료
        long createdTime = distribution.getCreatedAt();
        long expiredTime = DateUtils.addDays(new Date(createdTime), -8).getTime();
        Distribution invalidDistribution = new Distribution(
            distribution.getUserKey(),
            distribution.getRoomKey(),
            distribution.getToken(),
            distribution.getPrice(),
            distribution.getUsersCount(),
            expiredTime
        );

        lookupService.validate(ownerUser, roomKey, invalidDistribution);
    }
}
