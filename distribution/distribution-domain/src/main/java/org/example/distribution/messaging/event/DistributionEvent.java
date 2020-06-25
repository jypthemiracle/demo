package org.example.distribution.messaging.event;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class DistributionEvent implements Serializable {

    private int userKey;

    private String roomKey;

    private int price;

    private String token;


    public DistributionEvent(int userKey, int price) {
        this.userKey = userKey;
        this.price = price;
    }
}
