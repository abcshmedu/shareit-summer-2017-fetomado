package edu.hm.shareit.integration;

import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
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
    public void testLoginAndAddDisc() {
        JSONObject disc = new JSONObject()
                .put("title", "ValidDisc")
                .put("barcode", "111111111")
                .put("director", "Director")
                .put("fsk", "0");
        int countBefore = get("/media/discs").then()
                .statusCode(200)
                .extract().jsonPath().getList("").size();
        given().contentType(ContentType.JSON).body(disc.toString()).when().post("/media/discs").then()
                .statusCode(401)
                .body("detail", is("Keine Berechtigung."));
        String token = given().contentType(ContentType.JSON).body(login.toString()).when().post("/users/login").then()
                .statusCode(200)
                .extract().jsonPath().getString("token");
        disc.put("token", token);
        given().contentType(ContentType.JSON).body(disc.toString()).when().post("/media/discs").then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        assertEquals(countBefore + 1, get("/media/discs").then()
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

        get("/media/books/" + isbn).then()
                .statusCode(404)
                .body("detail", is("Medium nicht gefunden."));

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

    @Test
    public void testUpdatingDisc() {
        String barcode = "456789123";
        JSONObject disc = new JSONObject()
                .put("title", "Deadpool")
                .put("barcode", "456789123")
                .put("director", "Tim Miller")
                .put("fsk", "16");
        String token = given().contentType(ContentType.JSON).body(login.toString()).when().post("/users/login").then().log().all()
                .statusCode(200)
                .extract().jsonPath().getString("token");
        disc.put("token", token);

        get("/media/discs/" + barcode).then()
                .statusCode(404)
                .body("detail", is("Medium nicht gefunden."));

        given().contentType(ContentType.JSON).body(disc.toString()).when().post("/media/discs").then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        get("/media/discs/" + barcode).then()
                .statusCode(200)
                .body("title", is("Deadpool"))
                .body("director", is("Tim Miller"))
                .body("fsk", is(16));

        JSONObject body = new JSONObject().put("director", "J.K. Rowling").put("token", token);
        given().contentType(ContentType.JSON).body(body.toString())
                .when().put("/media/discs/" + barcode).then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        get("/media/discs/" + barcode).then()
                .statusCode(200)
                .body("title", is("Deadpool"))
                .body("director", is("J.K. Rowling"))
                .body("fsk", is(16));

        body.put("title", "Harry Potter").remove("director");
        given().contentType(ContentType.JSON).body(body.toString())
                .when().put("/media/discs/" + barcode).then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        get("/media/discs/" + barcode).then()
                .statusCode(200)
                .body("title", is("Harry Potter"))
                .body("director", is("J.K. Rowling"))
                .body("fsk", is(16));

        body.put("fsk", "18").remove("title");
        given().contentType(ContentType.JSON).body(body.toString())
                .when().put("/media/discs/" + barcode).then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        get("/media/discs/" + barcode).then()
                .statusCode(200)
                .body("title", is("Harry Potter"))
                .body("director", is("J.K. Rowling"))
                .body("fsk", is(18));

        body.put("title", "Dominik").put("director", "Tobi").remove("fsk");
        given().contentType(ContentType.JSON).body(body.toString())
                .when().put("/media/discs/" + barcode).then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        get("/media/discs/" + barcode).then()
                .statusCode(200)
                .body("title", is("Dominik"))
                .body("director", is("Tobi"))
                .body("fsk", is(18));

        body.put("title", "Felix").put("director", "Tobi").put("fsk", "10");
        given().contentType(ContentType.JSON).body(body.toString())
                .when().put("/media/discs/" + barcode).then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        get("/media/discs/" + barcode).then()
                .statusCode(200)
                .body("title", is("Felix"))
                .body("director", is("Tobi"))
                .body("fsk", is(10));

        body.put("title", "Felix").put("director", "Marita").put("fsk", "18");
        given().contentType(ContentType.JSON).body(body.toString())
                .when().put("/media/discs/" + barcode).then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        get("/media/discs/" + barcode).then()
                .statusCode(200)
                .body("title", is("Felix"))
                .body("director", is("Marita"))
                .body("fsk", is(18));

        body.put("title", "Deadpool").put("director", "Tim Miller").put("fsk", "16");
        given().contentType(ContentType.JSON).body(body.toString())
                .when().put("/media/discs/" + barcode).then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        get("/media/discs/" + barcode).then()
                .statusCode(200)
                .body("title", is("Deadpool"))
                .body("director", is("Tim Miller"))
                .body("fsk", is(16));
    }

}
