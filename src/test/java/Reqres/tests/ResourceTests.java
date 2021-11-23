package Reqres.tests;

import Reqres.models.ResourceData;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

import static Reqres.filters.CustomLogFilter.customLogFilter;
import static Reqres.tests.Specs.request;
import static Reqres.tests.Specs.responseSpec;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ResourceTests {
    @Test
    @Description("Проверить поля ресурса 2")
    public void resourceDataTest() {
        ResourceData data = given()
                .spec(request)
                .filter(customLogFilter().withCustomTemplates())
                .when()
                .get("/unknown/2")
                .then()
                .spec(responseSpec)
                .extract().as(ResourceData.class);

        assertEquals(2, data.getResource().getId());
        assertEquals("fuchsia rose", data.getResource().getName());
        assertEquals(2001, data.getResource().getYear());
        assertEquals("#C74375", data.getResource().getColor());
        assertEquals("17-2031", data.getResource().getPantoneValue());
    }

    @Test
    @Description("Проверить поля ресурса 3")
    public void secondResourceDataTest() {
        ResourceData data = given()
                .spec(request)
                .filter(customLogFilter().withCustomTemplates())
                .when()
                .get("/unknown/3")
                .then()
                .spec(responseSpec)
                .extract().as(ResourceData.class);

        assertEquals(3, data.getResource().getId());
        assertEquals("true red", data.getResource().getName());
        assertEquals(2002, data.getResource().getYear());
        assertEquals("#BF1932", data.getResource().getColor());
        assertEquals("19-1664", data.getResource().getPantoneValue());
    }
}