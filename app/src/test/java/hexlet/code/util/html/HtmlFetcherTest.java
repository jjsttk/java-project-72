package hexlet.code.util.html;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.util.file.FileReader;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HtmlFetcherTest {
    private static MockWebServer mockServer;

    @BeforeAll
    static void startServer() throws IOException {
        mockServer = new MockWebServer();
        mockServer.start();
    }

    @AfterAll
    static void stopServer() throws IOException {
        mockServer.shutdown();
    }

    @Test
    void testFetchValidHtmlPage() throws IOException {
        String mockHtmlBody = FileReader.readResourceFile("fixtures/testHtmlFetcher.html");

        mockServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mockHtmlBody));

        String mockUrl = mockServer.url("/").toString();
        Url url = Url.createUrlWithTimestampNow(mockUrl);
        UrlCheck result = HtmlFetcher.getUrlCheckResult(url);

        assertNotNull(result);
        assertThat(result.getStatusCode()).isEqualTo(200);
        assertThat(result.getTitle()).isEqualTo("Test Page");
        assertThat(result.getH1()).isEqualTo("Test Heading");
        assertThat(result.getDescription()).isEqualTo("Test Description");
    }

    @Test
    void testFetchNotFoundPage() {
        mockServer.enqueue(new MockResponse().setResponseCode(404));

        String mockUrl = mockServer.url("/notfound").toString();
        Url url = Url.createUrlWithTimestampNow(mockUrl);
        UrlCheck result = HtmlFetcher.getUrlCheckResult(url);
        assertNotNull(result);
        assertThat(result.getStatusCode()).isEqualTo(404);
        assertThat(result.getTitle()).isNull();
        assertThat(result.getH1()).isNull();
        assertThat(result.getDescription()).isNull();
    }
}
