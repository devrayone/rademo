package ru.devray.rademo;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SampleTest {

    @Test
    void simpleTest(){
        Response response = RestAssured.get("https://ya.ru");

        System.out.println(response.asString());
        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    void testHeaders(){
        Response response = RestAssured.get("https://www.ya.ru");

        //получаем список всех заголовков
        List<Header> headers = response.headers().asList();

        //Выводим на экран все заголовки
        System.out.println(headers);

        //Проверяем заголовок Content-Encoding
        Assertions.assertEquals(
                "gzip",
                response.header("Content-Encoding"),
                "Используется некорректный алгоритм сжатия!");

        //Проверяем заголовок Content-Type
        Assertions.assertEquals(
                "text/html; charset=UTF-8",
                response.header("Content-Type"),
                "Тип передаваемого контента не соответствует ожидаемому!");

    }

    @Test
    void testBody(){
        Response response = RestAssured.get("https://www.ya.ru");

        //проверяем, что тело ответа не пустое.
        Assertions.assertTrue(!response.body().asString().isEmpty(), "Тело ответа пустое!");

        //проверяем, что тело ответа содержит слово "yandex"
        Assertions.assertTrue(
                response.body().asString().contains("yandex"),
                "Слово 'yandex' не было найдено в теле ответа!");
    }

    @Test
    void testFinancialAPI(){
        String URL = "https://financialmodelingprep.com/api/v3/market-capitalization/AAPL?apikey=demo";

        Response response = RestAssured.get(URL);
        System.out.println(response.asString());
        System.out.println(response.statusLine());
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.body().asString().contains("marketCap"));
    }
}
