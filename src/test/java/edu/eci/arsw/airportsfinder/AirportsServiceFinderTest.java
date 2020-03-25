package edu.eci.arsw.airportsfinder;

import com.mashape.unirest.http.JsonNode;
import edu.eci.arsw.airportsfinder.controller.AirportsFinderException;
import edu.eci.arsw.airportsfinder.service.AirportsFinderServices;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirportsServiceFinderTest {
    @Autowired
    AirportsFinderServices service;

    @Test
    public void shouldGetAirportsByName(){
        try{
            JsonNode respuesta = service.airpotrsByName("Bogota");
            assertNotNull(respuesta);
            assertTrue(respuesta.toString().contains("Bogota"));
        }catch (AirportsFinderException e){
            fail("Deberia pasar");
        }
    }

    @Test
    public void shoulNotdGetAirportsByName(){
        try{
            JsonNode respuesta = service.airpotrsByName("notfound");
            fail("Deberia fallar");
        }catch (AirportsFinderException e){
            assertEquals(e.getMessage(),AirportsFinderException.NOT_FOUND);
        }
    }
}
