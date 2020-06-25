package org.example.distribution.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Receive {

    //받은 금액
    private int price;

    //받은 User
    private int userKey;

    public Receive(int price, int userKey) {
        this.price = price;
        this.userKey = userKey;
    }

}
