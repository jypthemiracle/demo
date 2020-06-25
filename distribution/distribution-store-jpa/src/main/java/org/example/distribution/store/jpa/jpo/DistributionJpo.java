package org.example.distribution.store.jpa.jpo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.distribution.domain.Distribution;

@Entity
@Table(name = "distribution")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DistributionJpo {

    @EmbeddedId
    private DistributionJpoId distributionJpoId;

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

    public DistributionJpo(Distribution distribution) {
        this(
            new DistributionJpoId(distribution.getToken()),
            distribution.getUserKey(),
            distribution.getRoomKey(),
            distribution.getToken(),
            distribution.getPrice(),
            distribution.getUsersCount(),
            distribution.getCreatedAt()
        );
    }

    public Distribution toDomain() {
        return new Distribution(
            getUserKey(),
            getRoomKey(),
            getToken(),
            getPrice(),
            getUsersCount(),
            getCreatedAt()
        );
    }

}
