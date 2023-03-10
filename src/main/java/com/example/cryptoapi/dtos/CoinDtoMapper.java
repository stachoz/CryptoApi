package com.example.cryptoapi.dtos;

import com.example.cryptoapi.models.Coin;

public class CoinDtoMapper {
    public static Coin map(AddCoinDto dto){
        Coin coin = new Coin();
        coin.setName(dto.getName());
        coin.setSymbol(dto.getSymbol());
        return coin;
    }

    public static CoinDto map(Coin coin){
        CoinDto dto = new CoinDto();
        dto.setId(coin.getId());
        dto.setName(coin.getName());
        dto.setSymbol(coin.getSymbol());
        return dto;
    }
}
