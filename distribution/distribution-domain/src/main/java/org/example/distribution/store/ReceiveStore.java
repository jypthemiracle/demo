package org.example.distribution.store;

import java.util.List;

import org.example.distribution.domain.Receive;

public interface ReceiveStore {
    List<Receive> findAllByDistributionId(String distributionId);

    void save(Receive receive, String distributionId);

}
