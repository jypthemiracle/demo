package org.example.distribution.sdo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.distribution.domain.Distribution;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DistributionCdo {
    //뿌릴 금액
    private int totalPrice;

    //뿌릴 인원
    private int usersCount;

    @Builder
    public static Distribution toDomainBuilder(int userKey, String roomKey, DistributionCdo distributionCdo) {
        return new Distribution(
            userKey,
            roomKey,
            distributionCdo.getTotalPrice(),
            distributionCdo.getUsersCount()
        );
    }
}
