package org.example.distribution.store.jpa.repository;

import org.example.distribution.store.jpa.jpo.DistributionJpo;
import org.example.distribution.store.jpa.jpo.DistributionJpoId;
import org.springframework.data.repository.CrudRepository;

public interface DistributionRepository extends CrudRepository<DistributionJpo, DistributionJpoId> {
}
