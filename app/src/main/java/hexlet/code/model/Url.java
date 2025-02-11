package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
public final class Url {
    private long id;
    private String name;
    private Timestamp createdAt;

    public static Url createUrlWithTimestampNow(String url) {
        return new Url(url);
    }

    private Url(String url) {
        this.name = url;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
