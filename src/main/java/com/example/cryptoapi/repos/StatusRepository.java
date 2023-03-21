package com.example.cryptoapi.repos;

import com.example.cryptoapi.models.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends CrudRepository<Status, Long> {
    Optional<Status> findTopByCoin_IdOrderByIdDesc(Long id);
}
