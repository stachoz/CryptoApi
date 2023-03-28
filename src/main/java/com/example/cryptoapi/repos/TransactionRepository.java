package com.example.cryptoapi.repos;

import com.example.cryptoapi.models.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Optional<List<Transaction>> findAllByCoin_Id(Long id);
}
