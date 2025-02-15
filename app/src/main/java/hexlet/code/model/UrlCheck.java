package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class UrlCheck {
    private long id;
    private int statusCode;
    private String title;
    private String h1;
    private String description;
    private long urlId;
    private Timestamp createdAt;


    public static UrlCheck forLastCheck(int statusCode, Timestamp createdAt) {
        return new UrlCheck(statusCode, createdAt);
    }

    private UrlCheck(int checkStatusCode, Timestamp checkCreatedAt) {
        this.statusCode = checkStatusCode;
        this.createdAt = checkCreatedAt;
    }

    public UrlCheck(int checkStatusCode, String checkTitle, String checkH1, String checkDescription) {
        this.statusCode = checkStatusCode;
        this.title = checkTitle;
        this.h1 = checkH1;
        this.description = checkDescription;
    }

}
