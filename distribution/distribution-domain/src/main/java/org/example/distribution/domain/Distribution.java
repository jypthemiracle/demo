package org.example.distribution.domain;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Distribution {

    //뿌린 User
    private int userKey;

    //뿌린 Room
    private String roomKey;

    //고유 Token (예측 불가한 3자리 문자열)
    private String token;

    //뿌릴 금액
    private int price;

    //뿌릴 인원
    private int usersCount;

    //뿌린 시간
    private long createdAt;

    public Distribution(int userKey, String roomKey, int price, int usersCount) {
        this.userKey = userKey;
        this.roomKey = roomKey;
        this.token = randomToken(userKey, roomKey);
        this.price = price;
        this.usersCount = usersCount;
        this.createdAt = System.currentTimeMillis();
    }

    private String randomToken(int userKey, String roomKey) {
        return userKey + "_" + roomKey + "_" + UUID.randomUUID();
    }

}
