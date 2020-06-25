package org.example.distribution.messaging.subscriber;

import java.io.Serializable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.distribution.messaging.event.DistributionEvent;
import org.example.distribution.messaging.topic.DistributionTopics;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DistributionSubscriber {

    @KafkaListener(
        topics = DistributionTopics.DISTRIBUTION_BANKBOOK_EVENT,
        groupId = DistributionTopics.DISTRIBUTION_BANKBOOK_EVENT + "-subscriber"
    )
    public void updateBankBook(DistributionEvent distributionEvent) {
        log.info("{} 통장에서 {}만큼 잔액 감소", distributionEvent.getUserKey(), distributionEvent.getPrice());
    }

    @KafkaListener(
        topics = DistributionTopics.DISTRIBUTION_KAKAO_MESSAGE_EVENT,
        groupId = DistributionTopics.DISTRIBUTION_KAKAO_MESSAGE_EVENT + "-subscriber"
    )
    public void sendKakaoMessage(DistributionEvent distributionEvent) {
        log.info(
            "{}님이 {} 대화방에 {} 뿌리기 메세지 전송 :: 바로가기Token[{}]",
            distributionEvent.getUserKey(),
            distributionEvent.getRoomKey(),
            distributionEvent.getPrice(),
            distributionEvent.getToken()
        );
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Serializable serializable) {
        log.warn("Received unknown: " + serializable.toString());
    }
}
