package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class Url {
    private long id;
    private String url;
    private Timestamp createdAt;

    public Url(String urlString, Timestamp timestamp) {
        this.url = urlString;
        this.createdAt = timestamp;
    }

    public Url(String urlString) {
        this.url = urlString;
    }
}
