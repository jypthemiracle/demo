package org.example.distribution.store.jpa.jpo;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveJpoId implements Serializable {
    private String receiveId;

    public ReceiveJpoId newReceiveKey() {
        return new ReceiveJpoId(UUID.randomUUID().toString());
    }

}
