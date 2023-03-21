package com.example.cryptoapi.services;

import com.example.cryptoapi.repos.CoinRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CoinApiService {
    private final CoinRepository coinRepository;

    public CoinApiService(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    public boolean isSupportedByApi(String coinSymbol){
        String symbol = coinSymbol.toUpperCase();
        String currency = "PLN";
        String url = "https://rest.coinapi.io/v1/exchangerate/" + symbol + "/" + currency;
        try{
            HttpResponse<String> response = getResponse(url);
            int statusCode = response.statusCode();
            if(statusCode >= 200 && statusCode < 300){
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private HttpResponse<String> getResponse(String url) throws URISyntaxException, IOException, InterruptedException {
        final String API_KEY = "87CB55BE-6DBE-4C3A-8B30-24288766D75B";
        final String HEADER = "X-CoinAPI-Key";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header(HEADER, API_KEY)
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
