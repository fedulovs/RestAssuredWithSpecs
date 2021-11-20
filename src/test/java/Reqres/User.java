package Reqres;

import Reqres.models.UserData;
import org.junit.jupiter.api.Test;

import static Reqres.Specs.request;
import static Reqres.Specs.responseSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class User {
    @Test
    public void userJanetData() {
        UserData data = given()
                .spec(request)
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
    public void userEmmaData() {
        UserData data = given()
                .spec(request)
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
    public void statusCodeTest() {
        UserData data = given()
                .spec(request)
                .get("/users?page=2")
                .then()
                .spec(responseSpec)
                .body("total_pages", is(2))
                .extract().as(UserData.class);
    }

    @Test
    public void noSuchUserTest() {
        given()
                .spec(request)
                .get("/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    public void bodyParameterTest() {

        given()
                .spec(request)
                .get("/users?page=2")
                .then()
                .spec(responseSpec)
                .body("total_pages", is(2));
    }

    @Test
    public void secondObjectTest() {

        given()
                .spec(request)
                .get("/users?page=2")
                .then()
                .spec(responseSpec)
                .body("data[0].id", is(7));
    }

    @Test
    public void singleUserTest() {

        given()
                .spec(request)
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("data.id", is(2))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"));
    }

    @Test
    public void extraInfoTest() {

        given()
                .spec(request)
                .get("/users/2")
                .then()
                .spec(responseSpec)

                .body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }
}

