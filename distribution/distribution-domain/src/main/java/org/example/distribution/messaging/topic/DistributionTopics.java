package org.example.distribution.messaging.topic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DistributionTopics {
    public static final String DISTRIBUTION_BANKBOOK_EVENT = "DISTRIBUTION_BANKBOOK_EVENT";

    public static final String DISTRIBUTION_KAKAO_MESSAGE_EVENT = "DISTRIBUTION_KAKAO_MESSAGE_EVENT";
}
