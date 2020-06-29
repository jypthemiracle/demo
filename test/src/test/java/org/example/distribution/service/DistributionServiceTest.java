package org.example.distribution.service;

import static org.junit.Assert.assertNotNull;

import org.example.DemoApplication;
import org.example.distribution.domain.Distribution;
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
public class DistributionServiceTest {
    @Autowired
    private DistributionService distributionService;

    private int ownerUser = 999;

    private String roomKey = "room-111";

    private String distributionId;

    @Before
    public void distribution() {
        distributionId = distributionService.distribution(ownerUser, roomKey, new DistributionCdo(3000, 4));
    }

    @Test
    public void test1_distribution() {
        assertNotNull(distributionId);
    }

    @Test
    public void test2_findDistribution() {
        Distribution distribution = distributionService.findDistribution(distributionId);
        assertNotNull(distribution);
    }
}
