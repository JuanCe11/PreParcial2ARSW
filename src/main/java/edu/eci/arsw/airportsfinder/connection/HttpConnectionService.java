package edu.eci.arsw.airportsfinder.connection;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.eci.arsw.airportsfinder.controller.AirportsFinderException;
import org.springframework.stereotype.Service;

@Service("connection")
public class HttpConnectionService {

    public JsonNode airportByName(String name) throws AirportsFinderException {
        try {
            HttpResponse<JsonNode> response = Unirest.get("https://cometari-airportsfinder-v1.p.rapidapi.com/api/airports/by-text?text=+" + name)
                    .header("x-rapidapi-host", "cometari-airportsfinder-v1.p.rapidapi.com")
                    .header("x-rapidapi-key", "34f05cff54msh30ba6f36c91c183p166499jsn555917ef62b8")
                    .asJson();
            if (response.getBody().getArray().length() == 0) {
                throw new AirportsFinderException(AirportsFinderException.NOT_FOUND);
            }
            return response.getBody();
        }catch (UnirestException e){
            throw new AirportsFinderException(AirportsFinderException.CONNECTION_FAILED);
        }
    }
}