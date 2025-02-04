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

    public Url(String url, Timestamp createdAt) {
        this.url = url;
        this.createdAt = createdAt;
    }
}
