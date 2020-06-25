package org.example.distribution.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.example.distribution.domain.Distribution;
import org.example.distribution.domain.Receive;
import org.example.distribution.store.ReceiveStore;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiveService {

    private final ReceiveStore receiveStore;

    private void validateHasReceived(int userKey, Distribution distribution) {
        List<Receive> receiveList = findReceiveList(distribution.getToken());
        for (Receive receive : receiveList) {
            if (receive.getUserKey() == userKey) {
                throw new RuntimeException("Invalid userKey. receive is allowed only one time");
            }
        }
    }

    private void validateOwnerForReceive(int userKey, Distribution distribution) {
        if (userKey == distribution.getUserKey()) {
            throw new RuntimeException("Invalid userKey. receive is not allowed to owner");
        }
    }

    private void validateRoom(String roomKey, Distribution distribution) {
        if (!roomKey.equals(distribution.getRoomKey())) {
            throw new RuntimeException("Invalid roomKey. receive is only allowed to same room");
        }
    }

    private void validateExpiredDateForReceive(Distribution distribution) {
        long createdTime = distribution.getCreatedAt();
        long currentTime = System.currentTimeMillis();
        long expiredTime = DateUtils.addMinutes(new Date(createdTime), 10).getTime();

        if (expiredTime < currentTime) {
            throw new RuntimeException("Invalid receive Time. receive is only 10 minutes available");
        }
    }

    private int residualPrice(int totalPrice, List<Receive> receiveList) {
        int residualPrice = totalPrice;
        for (Receive receive : receiveList) {
            residualPrice -= receive.getPrice();
        }

        return residualPrice;
    }

    private int randomPrice(int totalPrice) {
        return new Random().nextInt(totalPrice);
    }

    public void validateReceive(int userKey, String roomKey, Distribution distribution) {
        validateHasReceived(userKey, distribution);
        validateOwnerForReceive(userKey, distribution);
        validateRoom(roomKey, distribution);
        validateExpiredDateForReceive(distribution);
    }

    public List<Receive> findReceiveList(String distributionId) {
        return receiveStore.findAll(distributionId);
    }

    public int receive(int userKey, Distribution distribution) {
        List<Receive> receiveList = findReceiveList(distribution.getToken());
        int residualPrice = residualPrice(distribution.getPrice(), receiveList);
        int receivePrice = randomPrice(residualPrice);

        receiveStore.save(new Receive(receivePrice, userKey), distribution.getToken());

        return receivePrice;
    }

}
