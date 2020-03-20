package edu.eci.arsw.airportsfinder;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes={AirportsFinderApplication.class})
public class AirportsFinderTest {

    @Autowired
    AirportsFinderServices afs;

    @Test
    public void testMyEndpoint() throws UnirestException {
        HttpResponse<String> response = Unirest.get("https://cometari-airportsfinder-v1.p.rapidapi.com/api/airports/by-text?text=London")
                .header("x-rapidapi-host", "cometari-airportsfinder-v1.p.rapidapi.com")
                .header("x-rapidapi-key", "34f05cff54msh30ba6f36c91c183p166499jsn555917ef62b8")
                .asString();
        System.out.println(response.getBody());
    }

    @Test
    public void shouldGetAirportByName() throws UnirestException {
        JsonNode s = afs.airpotrsByName("London");
        Assert.assertNotNull(s);
        Assert.assertTrue(s.toString().contains("london"));
    }
}
