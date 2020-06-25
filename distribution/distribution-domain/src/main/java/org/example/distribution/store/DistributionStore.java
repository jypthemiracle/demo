package org.example.distribution.store;

import java.util.Optional;

import org.example.distribution.domain.Distribution;

public interface DistributionStore {
    Optional<Distribution> find(String distributionId);

    void save(Distribution distribution);

}
