package edu.hm.shareit.integration;

import org.junit.Test;

import static io.restassured.RestAssured.*;

public class MediaIntegrationTest extends IntegrationTestWithJetty {

    @Test
    public void testLoginAndAddBook() {
        get("/media/books").then().statusCode(200);
    }

}
