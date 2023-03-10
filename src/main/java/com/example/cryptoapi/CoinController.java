package com.example.cryptoapi;

import com.example.cryptoapi.dtos.AddCoinDto;
import com.example.cryptoapi.dtos.CoinDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinController {
    private final CoinService coinService;

    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping
    ResponseEntity<List<CoinDto>> getCoins(){
        List<CoinDto> coins = coinService.getAllCoins();
        if (coins.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(coins);
    }

    @PostMapping("")
    ResponseEntity<CoinDto> addCoin(@RequestBody AddCoinDto addCoinDto){
        CoinDto savedCompanyDto = coinService.saveCoin(addCoinDto);
        URI savedCompanyUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{coinId}")
                .buildAndExpand(savedCompanyDto.getId())
                .toUri();
        return ResponseEntity.created(savedCompanyUri).body(savedCompanyDto);
    }
}
