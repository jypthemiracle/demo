package org.example.distribution.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.example.distribution.domain.Distribution;
import org.example.distribution.domain.Receive;
import org.example.distribution.sdo.DistributionCdo;
import org.example.distribution.sdo.DistributionRdo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlowService {

    private final DistributionService distributionService;

    private final ReceiveService receiveService;

    private final LookupService lookupService;

    private DistributionRdo enrichment(Distribution distribution, List<Receive> receiveList) {
        return new DistributionRdo(distribution, receiveList);
    }

    public String distribution(int userKey, String roomKey, DistributionCdo distributionCdo) {
        String distributionId = distributionService.distribution(userKey, roomKey, distributionCdo);
        Distribution distribution = distributionService.findDistribution(distributionId);
        receiveService.initReceives(distribution);

        return distributionId;
    }

    public DistributionRdo lookup(int userKey, String roomKey, String token) {
        Distribution distribution = distributionService.findDistribution(token);
        lookupService.validate(userKey, roomKey, distribution);
        List<Receive> receiveList = receiveService.findReceivedList(distribution.getToken());

        return enrichment(distribution, receiveList);
    }

    public int receive(int userKey, String roomKey, String token) {
        Distribution distribution = distributionService.findDistribution(token);
        receiveService.validateReceive(userKey, roomKey, distribution);

        return receiveService.receive(userKey, distribution);
    }

}
