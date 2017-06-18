package edu.hm.shareit.integration;


import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class CopyIntegrationTest extends IntegrationTestWithJetty {

    private final JSONObject login = new JSONObject()
            .put("username", "testuser")
            .put("password", "Test123");

    private final JSONObject book = new JSONObject()
            .put("title", "Das Leben des Brian")
            .put("author", "Peter Lustig")
            .put("isbn", "9795648377233");

    private final JSONObject disc = new JSONObject()
            .put("title", "Source Code")
            .put("barcode", "1101110010")
            .put("director", "Matze Thomson")
            .put("fsk", "18");

    @Test
    public void testLoginAndAddCopy() {
        JSONObject copy = new JSONObject()
                .put("owner", "Egon")
                .put("medium", "9795648377233");
        int countBefore = get("/copies").then()
                .statusCode(200)
                .extract().jsonPath().getList("").size();
        given().contentType(ContentType.JSON).body(copy.toString()).when().post("/copies").then()
                .statusCode(401)
                .body("detail", is("Keine Berechtigung."));
        String token = given().contentType(ContentType.JSON).body(login.toString()).when().post("/users/login").then()
                .statusCode(200)
                .extract().jsonPath().getString("token");
        copy.put("token", token);
        book.put("token", token);
        given().contentType(ContentType.JSON).body(book.toString()).when().post("/media/books").then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        given().contentType(ContentType.JSON).body(copy.toString()).when().post("/copies").then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        assertEquals(countBefore + 1, get("/copies").then()
                .statusCode(200)
                .extract().jsonPath().getList("").size());
    }

    @Test
    public void testUpdatingCopy() {
        int id = 1;
        JSONObject copy = new JSONObject()
                .put("owner", "Hans")
                .put("medium", "1101110010");
        String token = given().contentType(ContentType.JSON).body(login.toString()).when().post("/users/login").then()
                .statusCode(200)
                .extract().jsonPath().getString("token");
        copy.put("token", token);
        disc.put("token", token);
        given().contentType(ContentType.JSON).body(disc.toString()).when().post("/media/discs").then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        get("/copies/" + id).then()
                .statusCode(404)
                .body("detail", is("Medium nicht gefunden."));
        given().contentType(ContentType.JSON).body(copy.toString()).when().post("/copies").then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        get("/copies/" + id).then()
                .statusCode(200)
                .body("owner", is("Hans"))
                .body("medium.director", is("Matze Thomson"));

        JSONObject body = new JSONObject().put("owner", "Fritz").put("token", token);
        given().contentType(ContentType.JSON).body(body.toString())
                .when().put("/copies/" + id).then()
                .statusCode(200)
                .body("detail", is("Erfolgreich."));
        get("/copies/" + id).then()
                .statusCode(200)
                .body("owner", is("Fritz"));
    }
}
