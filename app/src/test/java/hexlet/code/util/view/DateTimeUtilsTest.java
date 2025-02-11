package hexlet.code.util.view;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeUtilsTest {

    @Test
    void testDateTimeUtils() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 5, 14, 30);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        String formatted = DateTimeUtils.formatTimestamp(timestamp);
        assertEquals("05/02/2025 14:30", formatted);

        formatted = DateTimeUtils.formatTimestamp(null);
        assertEquals("", formatted);
    }
}
