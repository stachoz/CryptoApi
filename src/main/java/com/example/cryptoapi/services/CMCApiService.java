package com.example.cryptoapi.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CMCApiService {


    public boolean isSupportedByApi(String coinSymbol){
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/map?symbol=" + coinSymbol;
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
        final String API_KEY = "e16d7f10-94b3-4f69-a820-bd6f10fcd9d4";
        final String HEADER = "X-CMC_PRO_API_KEY";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header(HEADER, API_KEY)
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
