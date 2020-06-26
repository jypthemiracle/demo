package org.example.distribution.service;

import java.util.NoSuchElementException;

import lombok.RequiredArgsConstructor;
import org.example.distribution.domain.Distribution;
import org.example.distribution.messaging.publisher.DistributionPublisher;
import org.example.distribution.sdo.DistributionCdo;
import org.example.distribution.store.DistributionStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistributionService {

    private final DistributionStore distributionStore;

    private final DistributionPublisher distributionPublisher;

    @Value("${spring.kafka.use-flag}")
    private boolean kafkaUseFlag;

    private String register(int userKey, String roomKey, DistributionCdo distributionCdo) {
        Distribution distribution = distributionCdo.builder()
            .distributionCdo(distributionCdo)
            .roomKey(roomKey)
            .userKey(userKey)
            .build();

        distributionStore.save(distribution);

        return distribution.getToken();
    }

    public String distribution(int userKey, String roomKey, DistributionCdo distributionCdo) {
        String token = register(userKey, roomKey, distributionCdo);

        if (kafkaUseFlag) {
            distributionPublisher.updateBankBook(userKey, distributionCdo.getTotalPrice());
            distributionPublisher.sendKakaoMessage(userKey, roomKey, distributionCdo.getTotalPrice(), token);
        }

        return token;
    }

    public Distribution findDistribution(String distributionId) {
        return distributionStore.find(distributionId)
            .orElseThrow(() -> new NoSuchElementException("Distribution key not found:" + distributionId));
    }

}
