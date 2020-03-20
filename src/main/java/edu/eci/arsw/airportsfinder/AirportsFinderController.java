package edu.eci.arsw.airportsfinder;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/airports")
public class AirportsFinderController {
    @Autowired
    AirportsFinderServices airportrsFinderServices;

    @RequestMapping(path ="/{name}",method = RequestMethod.GET)
    public ResponseEntity<?> GetAllAirports(@PathVariable("name")String name){
        try {
            JsonNode res = airportrsFinderServices.airpotrsByName(name);
            return new ResponseEntity<>(res.toString(), HttpStatus.ACCEPTED);
        } catch (UnirestException e) {
            Logger.getLogger(AirportsFinderController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
