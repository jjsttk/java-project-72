package hexlet.code.model;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UrlCheckTest {

    @Test
    void testForLastCheck() {
        int statusCode = 200;
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        UrlCheck check = UrlCheck.forLastCheck(statusCode, timestamp);

        assertEquals(statusCode, check.getStatusCode());
        assertEquals(timestamp, check.getCreatedAt());
    }

    @Test
    void testConstructorWithFourArgs() {
        int statusCode = 200;
        String title = "Test Title";
        String h1 = "Test H1";
        String description = "Test Description";

        UrlCheck check = new UrlCheck(statusCode, title, h1, description);

        assertEquals(statusCode, check.getStatusCode());
        assertEquals(title, check.getTitle());
        assertEquals(h1, check.getH1());
        assertEquals(description, check.getDescription());
        assertNull(check.getCreatedAt());
    }

    @Test
    void testLombokGeneratedMethods() {
        UrlCheck check1 = new UrlCheck(
                1, 200,
                "Title", "H1",
                "Description", 123,
                Timestamp.valueOf(LocalDateTime.now()));
        UrlCheck check2 = new UrlCheck(1, 200,
                "Title", "H1",
                "Description", 123,
                check1.getCreatedAt());

        assertEquals(check1, check2);
        assertEquals(check1.hashCode(), check2.hashCode());
        assertTrue(check1.toString().contains("Title"));
    }

    @Test
    void testSetters() {
        UrlCheck urlCheck = new UrlCheck(200, "Title", "H1", "Description");

        assertEquals(200, urlCheck.getStatusCode());
        assertEquals("Title", urlCheck.getTitle());
        assertEquals("H1", urlCheck.getH1());
        assertEquals("Description", urlCheck.getDescription());

        urlCheck.setStatusCode(404);
        urlCheck.setTitle("Updated Title");
        urlCheck.setH1("Updated H1");
        urlCheck.setDescription("Updated Description");

        assertEquals(404, urlCheck.getStatusCode());
        assertEquals("Updated Title", urlCheck.getTitle());
        assertEquals("Updated H1", urlCheck.getH1());
        assertEquals("Updated Description", urlCheck.getDescription());

        urlCheck.setId(123L);
        assertEquals(123L, urlCheck.getId());

        urlCheck.setUrlId(456L);
        assertEquals(456L, urlCheck.getUrlId());

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        urlCheck.setCreatedAt(timestamp);
        assertEquals(timestamp, urlCheck.getCreatedAt());

        UrlCheck lastCheck = UrlCheck.forLastCheck(500, timestamp);
        assertEquals(500, lastCheck.getStatusCode());
        assertEquals(timestamp, lastCheck.getCreatedAt());
        assertNull(lastCheck.getTitle());
        assertNull(lastCheck.getH1());
        assertNull(lastCheck.getDescription());

        UrlCheck urlCheckWithoutArgs = new UrlCheck();
        assertEquals(0, urlCheckWithoutArgs.getId());
        assertEquals(0, urlCheckWithoutArgs.getUrlId());
        assertNull(urlCheckWithoutArgs.getTitle());
        assertNull(urlCheckWithoutArgs.getH1());
        assertNull(urlCheckWithoutArgs.getDescription());
        assertNull(urlCheckWithoutArgs.getCreatedAt());
    }
}
