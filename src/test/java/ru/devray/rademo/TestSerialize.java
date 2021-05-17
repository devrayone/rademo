package ru.devray.rademo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.devray.rademo.dto.Stock;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class TestSerialize {

    public static final String API_KEY = "53ef908ec640538fc602f353f474534a";

    public String exampleUrl = "https://financialmodelingprep.com/api/v3/profile/AAPL?apikey=demo";


    /**
     * Stock-price request
     * https://financialmodelingprep.com/api/v3/quote-short/AAPL?apikey=53ef908ec640538fc602f353f474534a
     */
    @Test
    void testWorkWithSerializedObject(){
        Stock[] actualStock =
                given().baseUri("https://financialmodelingprep.com/")
                .basePath("api/v3/")
                .queryParam("apikey", API_KEY)
                .log().all()
                .when().get("quote-short/AAPL/").then().extract().body().as(Stock[].class);

        Stock expectedStock = new Stock("AAPL", 0, 0);

        Assertions.assertEquals(expectedStock, actualStock[0]);
    }

}
