package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.util.view.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class AppTest {
    private static Javalin app;

    @BeforeEach
    public void setUp() throws Exception {
        app = App.getApp();
    }

    @Test
    void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.rootPath());
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath());
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testAddNewSite() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://google.com:8080";
            try (var postResponse = client.post(NamedRoutes.urlsPath(), requestBody)) {
                assertThat(postResponse.code()).isEqualTo(200);
            }

            var getResponse = client.get(NamedRoutes.urlsPath());
            assertThat(getResponse.code()).isEqualTo(200);
            assertNotNull(getResponse.body());
            String responseBody = getResponse.body().string();
            assertThat(responseBody).contains("https://google.com:8080");
        });
    }

    @Test
    public void testWrongSyntaxInAddNewSite() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=wws://google.com";
            try (var postResponse = client.post(NamedRoutes.urlsPath(), requestBody)) {
                assertThat(postResponse.code()).isEqualTo(200);
            }

            var getResponse = client.get(NamedRoutes.urlsPath());
            assertThat(getResponse.code()).isEqualTo(200);
            assertNotNull(getResponse.body());
            String responseBody = getResponse.body().string();
            assertFalse(responseBody.contains("wws://google.com"));
        });
    }

    @Test
    public void testAddDuplicateSite() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://google.com:8080";

            try (var postResponse1 = client.post(NamedRoutes.urlsPath(), requestBody)) {
                assertThat(postResponse1.code()).isEqualTo(200);
            }

            var getResponse1 = client.get(NamedRoutes.urlsPath());
            assertThat(getResponse1.code()).isEqualTo(200);
            assertNotNull(getResponse1.body());
            String responseBody1 = getResponse1.body().string();
            assertThat(responseBody1).contains("https://google.com:8080");

            try (var postResponse2 = client.post(NamedRoutes.urlsPath(), requestBody)) {
                assertThat(postResponse2.code()).isEqualTo(200);
            }


            var getResponse2 = client.get(NamedRoutes.urlsPath());
            assertThat(getResponse2.code()).isEqualTo(200);
            assertNotNull(getResponse2.body());
            String responseBody2 = getResponse2.body().string();


            int count = responseBody2.split("https://google.com:8080").length - 1;
            assertThat(count).isEqualTo(1);
        });
    }

    @Test
    public void testUrlPage() throws SQLException {
        var url = Url.createUrlWithTimestampNow("https://google.com");
        UrlsRepository.save(url);
        var id = url.getId();
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlPath(id));
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    void testUrlNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlPath("9999"));
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    void testRandomPageNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/hello/yandex");
            assertThat(response.code()).isEqualTo(404);
        });
    }
}
