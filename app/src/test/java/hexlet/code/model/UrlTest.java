package hexlet.code.model;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UrlTest {

    @Test
    void testCreateUrl() {
        String urlName = "https://example.com";
        Url url = Url.createUrl(urlName);

        assertEquals(urlName, url.getName());
        assertNull(url.getCreatedAt());
    }

    @Test
    void testConstructorWithUrl() {
        String urlName = "https://example.com";
        Url url = Url.createUrl(urlName);

        assertEquals(urlName, url.getName());
        assertNull(url.getCreatedAt());
    }

    @Test
    void testLombokGeneratedMethods() {
        String urlName = "https://example.com";
        Url url1 = new Url(1L, urlName, Timestamp.valueOf(LocalDateTime.now()));
        Url url2 = new Url(1L, urlName, url1.getCreatedAt());

        assertEquals(url1, url2);
        assertEquals(url1.hashCode(), url2.hashCode());
        assertTrue(url1.toString().contains(urlName));
    }
}
