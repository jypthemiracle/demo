package org.example.distribution.store.jpa.jpo;

import java.io.Serializable;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class DistributionJpoId implements Serializable {
    private String distributionId;

    public DistributionJpoId(String distributionId) {
        this.distributionId = distributionId;
    }

}
