package org.example.distribution.domain;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Receive {

    private String receiveId;

    //받은 금액
    private int price;

    //받은 User
    private int userKey;

    //상태
    private ReceiveStatus receiveStatus;

    public Receive(int price) {
        this.receiveId = UUID.randomUUID().toString();
        this.price = price;
        this.receiveStatus = ReceiveStatus.NOT_RECEIVED;
    }

    public Receive(String receiveId, int price, int userKey, ReceiveStatus receiveStatus) {
        this.receiveId = receiveId;
        this.price = price;
        this.userKey = userKey;
        this.receiveStatus = receiveStatus;
    }

    public void receive(int userKey) {
        this.userKey = userKey;
        this.receiveStatus = ReceiveStatus.RECEIVED;
    }

}
