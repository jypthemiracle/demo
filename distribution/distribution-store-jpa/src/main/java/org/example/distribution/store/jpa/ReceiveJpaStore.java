package org.example.distribution.store.jpa;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.RequiredArgsConstructor;
import org.example.distribution.domain.Receive;
import org.example.distribution.store.ReceiveStore;
import org.example.distribution.store.jpa.jpo.ReceiveJpo;
import org.example.distribution.store.jpa.repository.ReceiveRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReceiveJpaStore implements ReceiveStore {
    private final ReceiveRepository receiveRepository;

    @Override
    public List<Receive> findAll(String distributionId) {
        return StreamSupport.stream(receiveRepository.findAll().spliterator(), false)
            .map(ReceiveJpo::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void save(Receive receive, String distributionId) {
        receiveRepository.save(new ReceiveJpo(receive, distributionId));
    }

}
