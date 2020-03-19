package edu.eci.arsw.airportsfinder;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@RunWith(SpringRunner.class)
public class AirportrsFinderTest {

    @Test
    public void testMyEndpoint() throws UnirestException {
        HttpResponse<String> response = Unirest.get("https://cometari-airportsfinder-v1.p.rapidapi.com/api/airports/by-text?text=London")
                .header("x-rapidapi-host", "cometari-airportsfinder-v1.p.rapidapi.com")
                .header("x-rapidapi-key", "34f05cff54msh30ba6f36c91c183p166499jsn555917ef62b8")
                .asString();
        System.out.println(response.getBody());
    }
}
