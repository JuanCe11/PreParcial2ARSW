package edu.eci.arsw.airportsfinder.service;

import com.mashape.unirest.http.JsonNode;

import edu.eci.arsw.airportsfinder.cache.AirportsFinderCache;
import edu.eci.arsw.airportsfinder.connection.HttpConnectionService;
import edu.eci.arsw.airportsfinder.controller.AirportsFinderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("airportFinder")
public class AirportsFinderServices {
    @Autowired
    HttpConnectionService http;

    @Autowired
    AirportsFinderCache cache;

    public JsonNode airpotrsByName(String name) throws AirportsFinderException {
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
