package com.example.cryptoapi.controllers;

import com.example.cryptoapi.dtos.coin.CoinDto;
import com.example.cryptoapi.errors.ApiRequestException;
import com.example.cryptoapi.services.CoinApiService;
import com.example.cryptoapi.services.CoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinController {
    private final CoinService coinService;
    private final CoinApiService api;

    public CoinController(CoinService coinService, CoinApiService api) {
        this.coinService = coinService;
        this.api = api;
    }

    @GetMapping("")
    public ResponseEntity<List<CoinDto>> getCoins(){
        List<CoinDto> coins = coinService.getAllCoins();
        if (coins.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(coins);
    }
    @PostMapping
    public ResponseEntity<CoinDto> addCoin(@RequestBody CoinDto dto){
        String coinSymbol = dto.getSymbol();
        if(coinService.existsBySymbol(coinSymbol)){
            throw new ApiRequestException("This Coin is already added", HttpStatus.CONFLICT);
        }
        if(!api.isSupportedByApi(coinSymbol)){
            throw new ApiRequestException(
                    String.format("Coin with symbol {%s} is not supported", coinSymbol),
                    HttpStatus.BAD_REQUEST
            );
        }
        CoinDto savedCompanyDto = coinService.saveCoin(dto);
        URI savedCompanyUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{coinId}")
                .buildAndExpand(savedCompanyDto.getId())
                .toUri();
        return ResponseEntity.created(savedCompanyUri).body(savedCompanyDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoinDto> findById(@PathVariable Long id){
        return coinService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApiRequestException(String.format("Coin with given id (%d) does not exists", id), HttpStatus.NO_CONTENT));
    }
}
