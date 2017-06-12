package edu.hm.shareit.integration;

import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class MediaIntegrationTest extends IntegrationTestWithJetty {

    private final JSONObject login = new JSONObject()
            .put("username", "testuser")
            .put("password", "Test123");

    @Test
    public void testLoginAndAddBook() {
        JSONObject book = new JSONObject()
                .put("title", "what if?")
                .put("author", "Randall Munroe")
                .put("isbn", "978-3-8135-0625-5");
        int countBefore = get("/media/books").then()
                .statusCode(200)
                .extract().jsonPath().getList("").size();
        given().contentType(ContentType.JSON).body(book.toString()).when().post("/media/books").then()
                .statusCode(401)
                .body("detail", is("Keine Berechtigung."));
        String token = given().contentType(ContentType.JSON).body(login.toString()).when().post("/users/login").then()
                .statusCode(200)
                .extract().jsonPath().getString("token");
        book.put("token", token);
        given().contentType(ContentType.JSON).body(book.toString()).when().post("/media/books").then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        assertEquals(countBefore + 1, get("/media/books").then()
                .statusCode(200)
                .extract().jsonPath().getList("").size());
    }

}
