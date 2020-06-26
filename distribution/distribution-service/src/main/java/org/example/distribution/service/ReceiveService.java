package org.example.distribution.service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.example.distribution.domain.Distribution;
import org.example.distribution.domain.Receive;
import org.example.distribution.domain.ReceiveStatus;
import org.example.distribution.exception.ReceiveException;
import org.example.distribution.store.ReceiveStore;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiveService {

    private final ReceiveStore receiveStore;

    private void validateHasReceived(int userKey, Distribution distribution) {
        List<Receive> receiveList = findAllReceiveList(distribution.getToken())
            .stream()
            .filter(receive -> userKey == receive.getUserKey())
            .collect(Collectors.toList());

        if (!receiveList.isEmpty()) {
            throw new ReceiveException("Invalid userKey. receive is allowed only one time");
        }
    }

    private void validateOwnerForReceive(int userKey, Distribution distribution) {
        if (userKey == distribution.getUserKey()) {
            throw new ReceiveException("Invalid userKey. receive is not allowed to owner");
        }
    }

    private void validateRoom(String roomKey, Distribution distribution) {
        if (!roomKey.equals(distribution.getRoomKey())) {
            throw new ReceiveException("Invalid roomKey. receive is only allowed to same room");
        }
    }

    private void validateExpiredDateForReceive(Distribution distribution) {
        long createdTime = distribution.getCreatedAt();
        long currentTime = System.currentTimeMillis();
        long expiredTime = DateUtils.addMinutes(new Date(createdTime), 10).getTime();

        if (expiredTime < currentTime) {
            throw new ReceiveException("Invalid receive Time. receive is only 10 minutes available");
        }
    }

    private int randomPrice(int totalPrice) {
        return new Random().nextInt(totalPrice);
    }

    private Receive getRandomReceive(List<Receive> receiveList) {
        return receiveList.get(new Random().nextInt(receiveList.size()));
    }

    private List<Receive> findAllReceiveList(String distributionId) {
        return receiveStore.findAllByDistributionId(distributionId);
    }

    private List<Receive> findNotReceiveList(String distributionId) {
        return receiveStore.findAllByDistributionId(distributionId)
            .stream()
            .filter(receive -> ReceiveStatus.NOT_RECEIVED.equals(receive.getReceiveStatus()))
            .collect(Collectors.toList());
    }

    public void initReceives(Distribution distribution) {
        int residualPrice = distribution.getPrice();
        for (int cnt = 0; cnt < distribution.getUsersCount(); cnt++) {
            int price = residualPrice;
            if (cnt < distribution.getUsersCount() - 1) {
                price = randomPrice(residualPrice);
                residualPrice -= price;
            }

            receiveStore.save(new Receive(price), distribution.getToken());
        }
    }

    public void validateReceive(int userKey, String roomKey, Distribution distribution) {
        validateHasReceived(userKey, distribution);
        validateOwnerForReceive(userKey, distribution);
        validateRoom(roomKey, distribution);
        validateExpiredDateForReceive(distribution);
    }

    public int receive(int userKey, Distribution distribution) {
        List<Receive> nonReceivedList = findNotReceiveList(distribution.getToken());
        if (nonReceivedList.isEmpty()) {
            throw new ReceiveException("Distribution's Receive is end.");
        }

        Receive receive = getRandomReceive(nonReceivedList);
        receive.receive(userKey);
        receiveStore.save(receive, distribution.getToken());

        return receive.getPrice();
    }

    public List<Receive> findReceivedList(String distributionId) {
        return receiveStore.findAllByDistributionId(distributionId)
            .stream()
            .filter(receive -> ReceiveStatus.RECEIVED.equals(receive.getReceiveStatus()))
            .collect(Collectors.toList());
    }
}
