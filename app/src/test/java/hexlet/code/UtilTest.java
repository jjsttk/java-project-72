package hexlet.code;

import hexlet.code.util.DateTimeUtils;
import hexlet.code.util.FileReader;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UtilTest {

    @Test
    void testDateTimeUtils() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 5, 14, 30);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        String formatted = DateTimeUtils.formatTimestamp(timestamp);
        assertEquals("05/02/2025 14:30", formatted);

        formatted = DateTimeUtils.formatTimestamp(null);
        assertEquals("", formatted);
    }

    @Test
    void testReadResourceFile() throws IOException {
        String content = FileReader.readResourceFile("fixtures/testFile.txt");
        assertEquals("Hello, world!", content);

        assertThrows(FileNotFoundException.class, () -> {
            FileReader.readResourceFile("fixtures/nonExistentFile.txt");
        });

        String emptyContent = FileReader.readResourceFile("fixtures/emptyFile.txt");
        assertEquals("", emptyContent);

        String multiLineContent = FileReader.readResourceFile("fixtures/multiLineFile.txt");
        assertEquals("Line 1\nLine 2", multiLineContent);

    }
}
