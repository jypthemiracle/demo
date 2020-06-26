package org.example.distribution.store.jpa.repository;

import org.example.distribution.store.jpa.jpo.ReceiveJpo;
import org.example.distribution.store.jpa.jpo.ReceiveJpoId;
import org.springframework.data.repository.CrudRepository;

public interface ReceiveRepository extends CrudRepository<ReceiveJpo, ReceiveJpoId> {
    Iterable<ReceiveJpo> findAllByDistributionId(String distributionId);
}
