package com.example.cryptoapi.repos;

import com.example.cryptoapi.models.Coin;
import org.springframework.data.repository.CrudRepository;

public interface CoinRepository extends CrudRepository<Coin, Long> {
    boolean existsBySymbol(String symbol);
}
