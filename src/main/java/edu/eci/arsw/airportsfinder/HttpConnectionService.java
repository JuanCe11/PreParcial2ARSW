package edu.eci.arsw.airportsfinder;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

@Service("connection")
public class HttpConnectionService {

    public JsonNode airportByName(String name) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("https://cometari-airportsfinder-v1.p.rapidapi.com/api/airports/by-text?text=+"+name)
                .header("x-rapidapi-host", "cometari-airportsfinder-v1.p.rapidapi.com")
                .header("x-rapidapi-key", "34f05cff54msh30ba6f36c91c183p166499jsn555917ef62b8")
                .asJson();
        return response.getBody();
    }
}