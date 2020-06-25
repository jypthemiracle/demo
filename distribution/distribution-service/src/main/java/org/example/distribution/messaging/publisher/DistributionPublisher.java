package org.example.distribution.messaging.publisher;

import java.io.Serializable;

import lombok.RequiredArgsConstructor;
import org.example.distribution.messaging.event.DistributionEvent;
import org.example.distribution.messaging.topic.DistributionTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DistributionPublisher {
    private final KafkaTemplate<String, Serializable> kafkaTemplate;

    public void updateBankBook(int userKey, int price) {
        kafkaTemplate.send(DistributionTopics.DISTRIBUTION_BANKBOOK_EVENT, new DistributionEvent(userKey, price));
    }

    public void sendKakaoMessage(int userKey, String roomKey, int price, String token) {
        kafkaTemplate.send(DistributionTopics.DISTRIBUTION_KAKAO_MESSAGE_EVENT, new DistributionEvent(userKey, roomKey, price, token));
    }

}
