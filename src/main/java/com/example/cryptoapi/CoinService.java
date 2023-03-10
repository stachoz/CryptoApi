package com.example.cryptoapi;

import com.example.cryptoapi.dtos.AddCoinDto;
import com.example.cryptoapi.dtos.CoinDto;
import com.example.cryptoapi.dtos.CoinDtoMapper;
import com.example.cryptoapi.models.Coin;
import com.example.cryptoapi.repos.CoinRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoinService {
    private final CoinRepository coinRepository;

    public CoinService(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    CoinDto saveCoin(AddCoinDto addCoinDto){
        Coin coin = CoinDtoMapper.map(addCoinDto);
        Coin savedCompany = coinRepository.save(coin);
        return CoinDtoMapper.map(savedCompany);
    }

    List<CoinDto> getAllCoins(){
        Iterable<Coin> findAll = coinRepository.findAll();
        List<CoinDto> coins = new ArrayList<>();
        findAll.forEach(
                coin -> coins.add(CoinDtoMapper.map(coin))
        );
        return coins;
    }
}
