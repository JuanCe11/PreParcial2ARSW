package edu.eci.arsw.airportsfinder.cache;

import org.springframework.stereotype.Service;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import com.mashape.unirest.http.JsonNode;

@Service
public class AirportsFinderCache {
    private ConcurrentHashMap<String, JsonNode> airports = new ConcurrentHashMap<>();

    public JsonNode getByName(String name){
        if(airports.containsKey(name)){
            return airports.get(name);
        }else{
            return null;
        }
    }

    public void addAirport(String name, JsonNode data){
        Timer nuevo = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                airports.remove(name);
                nuevo.cancel();
            }
        };
        airports.put(name,data);
        nuevo.schedule(task, 300000, 1);
    }
}
