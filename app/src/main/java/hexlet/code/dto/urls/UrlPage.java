package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public final class UrlPage extends BasePage {
    private Url url;
    private List<UrlCheck> result;

    public static UrlPage createPageWithoutUrlChecks(Url urlObject) {
        return new UrlPage(urlObject);
    }

    private UrlPage(Url urlObject) {
        this.url = urlObject;
    }
}
