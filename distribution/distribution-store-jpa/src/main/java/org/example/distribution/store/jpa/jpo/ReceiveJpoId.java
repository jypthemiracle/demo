package org.example.distribution.store.jpa.jpo;

import java.io.Serializable;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class ReceiveJpoId implements Serializable {
    private String receiveId;

    public ReceiveJpoId(String receiveId) {
        this.receiveId = receiveId;
    }

}
