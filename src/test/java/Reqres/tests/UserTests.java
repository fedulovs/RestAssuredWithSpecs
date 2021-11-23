package Reqres.tests;

import Reqres.models.UserData;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static Reqres.filters.CustomLogFilter.customLogFilter;
import static Reqres.tests.Specs.request;
import static Reqres.tests.Specs.responseSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests {
    @Test
    @Description("Проверить поля юзера 2")
    public void userJanetData() {
        UserData data = given()
                .spec(request)
                .filter(customLogFilter().withCustomTemplates())
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .extract().as(UserData.class);

        assertEquals(2, data.getUser().getId());
        assertEquals("janet.weaver@reqres.in", data.getUser().getEmail());
        assertEquals("Janet", data.getUser().getFirstName());
        assertEquals("Weaver", data.getUser().getLastName());
    }

    @Test
    @Description("Проверить поля юзера 3") // В дальнейшем можно вынести в степ страницы принимающий номер юзера
    public void userEmmaData() {
        UserData data = given()
                .spec(request)
                .filter(customLogFilter().withCustomTemplates())
                .when()
                .get("/users/3")
                .then()
                .spec(responseSpec)
                .extract().as(UserData.class);

        assertEquals(3, data.getUser().getId());
        assertEquals("emma.wong@reqres.in", data.getUser().getEmail());
        assertEquals("Emma", data.getUser().getFirstName());
        assertEquals("Wong", data.getUser().getLastName());
    }

    @Test
    @Description("Проверить поле support юзера")
    public void extraInfoData() {
        UserData data = given()
                .spec(request)
                .filter(customLogFilter().withCustomTemplates())
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .extract().as(UserData.class);

        assertEquals("https://reqres.in/#support-heading", data.getSupport().getUrl());
        assertEquals("To keep ReqRes free, contributions towards server costs are appreciated!", data.getSupport().getText());
    }

    @Test
    @Description("Проверить что всего 2 страницы")
    public void pagesAmountTest() {
        given()
                .spec(request)
                .filter(customLogFilter().withCustomTemplates())
                .get("/users?page=2")
                .then()
                .spec(responseSpec)
                .body("total_pages", is(2));
    }

    @Test
    @Description("Проверить что нет юзера под номром 23")
    public void noSuchUserTest() {
        given()
                .spec(request)
                .filter(customLogFilter().withCustomTemplates())
                .get("/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    @Description("Проверить что всего в выдаче 2 страницы")
    public void bodyParameterTest() {

        given()
                .spec(request)
                .filter(customLogFilter().withCustomTemplates())
                .get("/users?page=2")
                .then()
                .spec(responseSpec)
                .body("total_pages", is(2));
    }

    @Test
    @Description("Проверить что у первого юзера на второй странице id = 7")
    public void secondObjectTest() {

        given()
                .spec(request)
                .filter(customLogFilter().withCustomTemplates())
                .get("/users?page=2")
                .then()
                .spec(responseSpec)
                .body("data[0].id", is(7));
    }

    @Test
    @DisplayName("")
    @Description("Проверить что страница юзеров под №2 не пустая с использованием groovy")
    public void usersPageIsNotEmpty() {

        given()
                .spec(request)
                .filter(customLogFilter().withCustomTemplates())
                .get("/users?page=2")
                .then()
                .spec(responseSpec)
                .body("data.findAll.flatten()", is(not(empty())));
    }
}

