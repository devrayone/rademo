package ru.devray.rademo;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class BDDTest {
    @Test
    void simpleTest(){
        given().baseUri("https://ya.ru").
                basePath("/").

        when().get().

        then().statusCode(200);
    }

    @Test
    void testHeaders(){
        given().baseUri("https://www.ya.ru").
                basePath("/").

        when().get().

        then().header("Content-Encoding", Matchers.equalTo("gzip"))
              .header("Content-Type", Matchers.equalTo("text/html; charset=UTF-8"));
    }

    @Test
    void testBody(){
        given().baseUri("https://www.ya.ru").
                basePath("/").

        when().get().

        then().body(Matchers.not(Matchers.empty()))
              .body(Matchers.containsString("yandex"));
    }

    @Test
    @Disabled
    void testFinancialAPI(){
        String URL = "https://financialmodelingprep.com/api/v3/market-capitalization/AAPL?apikey=";

        RestAssured.given().baseUri(URL)
                .when().get()
                .then().body(Matchers.not(Matchers.empty()))
                .body(Matchers.containsString("marketCap"));
    }
}
