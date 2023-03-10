package com.example.cryptoapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.msgpack.util.json.JSON;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class TestService {
    private final String API_KEY = "e16d7f10-94b3-4f69-a820-bd6f10fcd9d4";
    private final String API_URL = "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest?symbol=BTC,ETH,LTC";

    public JSONObject getData(){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(API_URL))
                    .header("X-CMC_PRO_API_KEY", API_KEY)
                    .GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            JSONObject jsonObject = new ObjectMapper().readValue(body, JSONObject.class);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }
}
