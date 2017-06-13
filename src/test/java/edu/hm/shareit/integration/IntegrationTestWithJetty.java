package edu.hm.shareit.integration;

import io.restassured.RestAssured;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class IntegrationTestWithJetty {

    public static final String WEBAPP_DIR = "./src/main/webapp/";
    public static final int PORT = 8282;
    public static final String APP_URL = "/";

    private static Server server;

    @BeforeClass
    public static void startApplicationWithJetty() throws Exception {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = PORT;
        RestAssured.basePath = "/shareit/";

        server = new Server(PORT);
        server.setHandler(new WebAppContext(WEBAPP_DIR, APP_URL));
        server.start();
    }

    @AfterClass
    public static void stopJetty() throws Exception {
        server.stop();
        server.join();
    }

}
