package ru.devray.rademo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class BDDRefactorTest {

    static RequestSpecification spec;

    /**
     * Выносим общие параметры выполняемых запросов в единую точку
     */
    @BeforeAll
    static void setUp(){
        RequestSpecBuilder builder = new RequestSpecBuilder();

        builder.setBaseUri("https://www.ya.ru");
        builder.setBasePath("/");
        builder.addHeader("Content-Type", "text/html");

        spec = builder.build();
    }

    @Test
    void simpleTest(){
        given().with().spec(spec).

        when().get().

        then().statusCode(200);
    }

    @Test
    void testHeaders(){
        given().with().spec(spec).

        when().get().

        then().header("Content-Encoding", Matchers.equalTo("gzip"))
              .header("Content-Type", Matchers.equalTo("text/html; charset=UTF-8"));
    }

    @Test
    void testBody(){
        given().with().spec(spec).

        when().get().

        then().body(Matchers.not(Matchers.empty()))
              .body(Matchers.containsString("yandex"));
    }

}
