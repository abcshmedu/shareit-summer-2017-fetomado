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

    @Test
    public void testUpdatingBook() {
        String isbn = "9783548376233";
        JSONObject book = new JSONObject()
                .put("title", "Die Kaenguru-Chroniken")
                .put("author", "Marc-Uwe Kling")
                .put("isbn", isbn);
        String token = given().contentType(ContentType.JSON).body(login.toString()).when().post("/users/login").then()
                .statusCode(200)
                .extract().jsonPath().getString("token");
        book.put("token", token);
        given().contentType(ContentType.JSON).body(book.toString()).when().post("/media/books").then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        get("/media/books/" + isbn).then()
                .statusCode(200)
                .body("title", is("Die Kaenguru-Chroniken"))
                .body("author", is("Marc-Uwe Kling"));

        JSONObject body = new JSONObject().put("author", "J.K. Rowling").put("token", token);
        given().contentType(ContentType.JSON).body(body.toString())
                .when().put("/media/books/" + isbn).then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        get("/media/books/" + isbn).then()
                .statusCode(200)
                .body("title", is("Die Kaenguru-Chroniken"))
                .body("author", is("J.K. Rowling"));

        body.put("title", "Harry Potter").remove("author");
        given().contentType(ContentType.JSON).body(body.toString())
                .when().put("/media/books/" + isbn).then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        get("/media/books/" + isbn).then()
                .statusCode(200)
                .body("title", is("Harry Potter"))
                .body("author", is("J.K. Rowling"));

        body.put("title", "Die Kaenguru-Chroniken").put("author", "Marc-Uwe Kling");
        given().contentType(ContentType.JSON).body(body.toString())
                .when().put("/media/books/" + isbn).then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        get("/media/books/" + isbn).then()
                .statusCode(200)
                .body("title", is("Die Kaenguru-Chroniken"))
                .body("author", is("Marc-Uwe Kling"));
    }

}
