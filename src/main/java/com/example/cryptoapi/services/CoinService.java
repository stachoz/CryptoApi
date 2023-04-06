package com.example.cryptoapi.services;

import com.example.cryptoapi.dtos.coin.CoinDto;
import com.example.cryptoapi.dtos.coin.CoinDtoMapper;
import com.example.cryptoapi.models.Coin;
import com.example.cryptoapi.repos.CoinRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoinService {
    private final CoinRepository coinRepository;

    public CoinService(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    public CoinDto saveCoin(CoinDto dto){
        Coin coin = CoinDtoMapper.map(dto);
        Coin savedCompany = coinRepository.save(coin);
        return CoinDtoMapper.map(savedCompany);
    }

    public List<CoinDto> getAllCoins(){
        Iterable<Coin> findAll = coinRepository.findAll();
        List<CoinDto> coins = new ArrayList<>();
        findAll.forEach(
                coin -> coins.add(CoinDtoMapper.map(coin))
        );
        return coins;
    }

    public Optional<CoinDto> findById(Long id){
        return coinRepository.findById(id)
                .map(CoinDtoMapper::map);
    }

    public boolean existsBySymbol(String coinSymbol){
        return coinRepository.existsBySymbol(coinSymbol);
    }

    public boolean existsById(Long id){
        return coinRepository.existsById(id);
    }



}
