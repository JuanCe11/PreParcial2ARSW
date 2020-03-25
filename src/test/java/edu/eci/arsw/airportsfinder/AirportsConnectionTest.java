package edu.eci.arsw.airportsfinder;


import com.mashape.unirest.http.JsonNode;
import edu.eci.arsw.airportsfinder.connection.HttpConnectionService;
import edu.eci.arsw.airportsfinder.controller.AirportsFinderException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirportsConnectionTest {

    @Autowired
    HttpConnectionService http;

    @Test
    public void shouldGetAirportsByName() throws AirportsFinderException {
        JsonNode response = http.airportByName("London");
        assertNotNull(response);
        assertTrue(response.toString().contains("London"));
    }

    @Test
    public void shouldNotGetAirportByName() {
        try {
            JsonNode response = http.airportByName("notfound");
            fail("Debio fallar por consultar aeropuertos por un nombre inexistente");
        }catch (AirportsFinderException e) {
            assertEquals(e.getMessage(),AirportsFinderException.NOT_FOUND);
        }
    }
}
