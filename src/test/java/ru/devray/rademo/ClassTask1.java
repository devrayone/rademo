package ru.devray.rademo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.devray.rademo.dto.Stock;

import java.util.LinkedHashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

/**
 * Задание 17.1 - Волк с Уолл-стрит
 * Stocks API
 * https://financialmodelingprep.com/login
 *
 * Добро пожаловать в мир финансов! Интернет полон данных о котировках акций, которые позволяют
 * в мгновение ока зарабатывать миллионы и терять миллиарды. Точность предоставляемой
 * информации - чертовски важный критерий. Поэтому такой функционал просто обязан быть покрытым тестами.
 *
 * Ваша задача - познакомиться с REST API сервисом по получению финансовой информации. Зарегистрируйтесь на сайте
 * https://financialmodelingprep.com/login
 * Получите API ключ для возможности делать запросы к API.
 * используя библиотеку RestAssured разработать 3 теста на функционал получения цены акций.
 */
public class ClassTask1 {

    public static final String API_KEY = "53ef908ec640538fc602f353f474534a";

    public static RequestSpecification spec;

    public String exampleUrl = "https://financialmodelingprep.com/api/v3/profile/AAPL?apikey=demo";

    @BeforeAll
    static void setUp(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder
                .setBaseUri("https://financialmodelingprep.com/")
                .setBasePath("api/v3/")
                .addQueryParam("apikey", API_KEY)
                .log(LogDetail.ALL);

        spec = builder.build();
    }

    @Test
    void availabilityTest(){
        given().spec(spec)

       .when().get("profile/AAPL")

       .then().statusCode(200);
    }

    @Test
    void validateJsonBodyTest(){
        given().spec(spec)

        .when().get("profile/AAPL")

        .then().log().all()
               .body("[0].symbol", Matchers.equalTo("AAPL"))
               .body("[0].price", Matchers.isA(Number.class))
               .body("[0].currency", Matchers.equalTo("USD"));
    }

    /**
     * Альтернативное написание теста validateJsonBodyTest
     */
    @Test
    void validateJsonBodyTestAnotherApproach(){
        Response response =

        given().spec(spec)

        .when().get("profile/AAPL");

        JsonPath jsonPath = response.jsonPath();

        Assertions.assertEquals("AAPL", jsonPath.get("[0].symbol"), "Некорректный тикер акции!");
        Assertions.assertTrue(jsonPath.get("[0].price") instanceof Number, "Некорректный тип данных в поле цена!");
        Assertions.assertEquals("USD", jsonPath.get("[0].currency"), "Некорректный тикер акции!");
    }

    @Test
    @DisplayName("Test stock news")
    void testStockNews(){
        String url = "https://financialmodelingprep.com/api/v3/stock_news?tickers=AAPL,FB,GOOG,AMZN&limit=50&apikey=demo";

        Response response = RestAssured.given()
                .spec(spec).
                queryParams("tickers", "AAPL,FB,GOOG,AMZN").

                when().get("stock_news");

        JsonPath jsonPath = response.jsonPath();
        List<LinkedHashMap> elements = jsonPath.get("$.");
        for(LinkedHashMap element : elements){
            Assertions.assertTrue(element.get("url").toString().contains("https://"));
        }
    }

    @Test
    void testStockPrice(){
        //https://financialmodelingprep.com/api/v3/quote-short/AAPL?apikey=demo
        Stock[] actualStock = given().spec(spec)
        .when().get("quote-short/AAPL").
//        .then().body("[0].symbol", Matchers.isA(String.class))
//                .body("[0].price", Matchers.isA(Float.class))
//                .body("[0].volume", Matchers.isA(Integer.class))
//                .body("[0].volume", Matchers.greaterThan(0));
        body().as(Stock[].class);

        Stock expectedStock = new Stock("AAPL", 102.5, 754843598);

        Assertions.assertEquals(expectedStock, actualStock[0]);
    }



}
