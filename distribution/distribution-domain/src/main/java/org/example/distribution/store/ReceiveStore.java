package org.example.distribution.store;

import java.util.List;

import org.example.distribution.domain.Receive;

public interface ReceiveStore {
    List<Receive> findAll(String distributionId);

    void save(Receive receive, String distributionId);

}
