package edu.eci.arsw.airportsfinder;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("airportFinder")
public class AirportsFinderServices {
    @Autowired
    HttpConnectionService http;

    @Autowired
    AirportsFinderCache cache;

    public JsonNode airpotrsByName(String name) throws UnirestException {
        JsonNode res = cache.getByName(name);
        if(res != null){
            return res;
        }else{
            res = http.airportByName(name);
            cache.addAirport(name,res);
            return res;
        }
    }

}
