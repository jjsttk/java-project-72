package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
@AllArgsConstructor
public final class Url {
    private long id;
    private String name;
    private Timestamp createdAt;

    public static Url createUrl(String url) {
        return new Url(url);
    }

    private Url(String url) {
        this.name = url;
    }
}
