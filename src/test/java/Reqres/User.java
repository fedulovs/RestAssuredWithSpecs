package Reqres;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static Reqres.Specs.*;

public class User {
    @Test
    public void singleUser() {
        given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .log().body();
    }

    @Test
    public void statusCodeTest() {
        given()
                .spec(request)
                .get("/users?page=2")
                .then()
                .spec(responseSpec)
                .body("total_pages", is(2));
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

