package org.example.distribution.store.jpa.jpo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.distribution.domain.Receive;
import org.example.distribution.domain.ReceiveStatus;

@Entity
@Table(name = "receive")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveJpo {

    @EmbeddedId
    private ReceiveJpoId receiveJpoId;

    private String distributionId;

    //받은 금액
    private int price;

    //받은 User
    private int userKey;

    //상태
    private ReceiveStatus receiveStatus;

    public ReceiveJpo(Receive receive, String token) {
        this(
            new ReceiveJpoId(receive.getReceiveId()),
            token,
            receive.getPrice(),
            receive.getUserKey(),
            receive.getReceiveStatus()
        );
    }

    public Receive toDomain() {
        return new Receive(
            getReceiveJpoId().getReceiveId(),
            getPrice(),
            getUserKey(),
            getReceiveStatus()
        );
    }

}
