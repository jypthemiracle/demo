package org.example.distribution.sdo;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.distribution.domain.Distribution;
import org.example.distribution.domain.Receive;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DistributionRdo {
    //뿌린 시간
    private long createdAt;

    //뿌린 금액
    private int distributionPrice;

    //받기 완료된 금액
    private int receivedPrice;

    //받기 완료된 정보 [받은 금액, 받은 사용자 아이디] 리스트
    private List<Receive> receiveList;

    public DistributionRdo(Distribution distribution, List<Receive> receives) {
        createdAt = distribution.getCreatedAt();
        distributionPrice = distribution.getPrice();
        receivedPrice = calculateReceivedPrice(receives);
        receiveList = receives;
    }

    private int calculateReceivedPrice(List<Receive> receiveList) {
        int price = 0;
        for (Receive receive : receiveList) {
            price += receive.getPrice();
        }

        return price;
    }
}
