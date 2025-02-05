package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlsRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

public final class AppTest {
    private static Javalin app;

    @BeforeEach
    public void setUp() throws Exception {
        app = App.getApp();
    }

    @Test
    void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testAddNewSite() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://google.com:8080";
            var postResponse = client.post("/urls", requestBody);
            assertThat(postResponse.code()).isEqualTo(200);

            var getResponse = client.get("/urls");
            assertThat(getResponse.code()).isEqualTo(200);
            String responseBody = getResponse.body().string();
            assertThat(responseBody).contains("https://google.com:8080");
        });
    }

    @Test
    public void testWrongSyntaxInAddNewSite() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=wws://google.com";
            var postResponse = client.post("/urls", requestBody);
            assertThat(postResponse.code()).isEqualTo(200);

            var getResponse = client.get("/urls");
            assertThat(getResponse.code()).isEqualTo(200);
            String responseBody = getResponse.body().string();
            assertFalse(responseBody.contains("wws://google.com"));
        });
    }

    @Test
    public void testAddDuplicateSite() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://google.com:8080";

            // Add the first URL
            var postResponse1 = client.post("/urls", requestBody);
            assertThat(postResponse1.code()).isEqualTo(200);

            // Verify the URL was added
            var getResponse1 = client.get("/urls");
            assertThat(getResponse1.code()).isEqualTo(200);
            String responseBody1 = getResponse1.body().string();
            assertThat(responseBody1).contains("https://google.com:8080");

            // Try to add the same URL again
            var postResponse2 = client.post("/urls", requestBody);
            assertThat(postResponse2.code()).isEqualTo(200);

            // Verify the URL is not added
            var getResponse2 = client.get("/urls");
            assertThat(getResponse2.code()).isEqualTo(200);
            String responseBody2 = getResponse2.body().string();

            // Ensure that the URL only appears once
            int count = responseBody2.split("https://google.com:8080").length - 1;
            assertThat(count).isEqualTo(1);
        });
    }

    @Test
    public void testUrlPage() throws SQLException {
        var url = new Url("https://google.com");
        UrlsRepository.save(url);
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    void testRandomPageNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/hello/yandex");
            assertThat(response.code()).isEqualTo(404);
        });
    }
}
