package org.example.distribution.store.jpa;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.example.distribution.domain.Distribution;
import org.example.distribution.store.DistributionStore;
import org.example.distribution.store.jpa.jpo.DistributionJpo;
import org.example.distribution.store.jpa.jpo.DistributionJpoId;
import org.example.distribution.store.jpa.repository.DistributionRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DistributionJpaStore implements DistributionStore {
    private final DistributionRepository distributionRepository;

    @Override
    public Optional<Distribution> find(String distributionId) {
        return distributionRepository.findById(new DistributionJpoId(distributionId))
            .map(DistributionJpo::toDomain);
    }

    @Override
    public void save(Distribution distribution) {
        distributionRepository.save(new DistributionJpo(distribution));
    }

}
