package hexlet.code.util.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.mashape.unirest.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ResponseParser {

    public static Map<String, String> parse(HttpResponse<String> response) {
        String htmlBody = response.getBody();
        Document document = Jsoup.parse(htmlBody);

        String title = extractElementText(document, "title");
        String h1 = extractElementText(document, "h1");
        String description = extractMetaContent(document, "description");

        return buildResultMap(title, h1, description);
    }

    private static String extractElementText(Document document, String tagName) {
        Element element = document.getElementsByTag(tagName).first();
        return Optional.ofNullable(element)
                .map(Element::text)
                .orElse(null);
    }

    private static String extractMetaContent(Document document, String metaTagName) {
        return document.getElementsByTag("meta")
                .stream()
                .filter(element -> metaTagName.equals(element.attr("name")))
                .findFirst()
                .map(element -> element.attr("content"))
                .orElse(null);
    }

    private static Map<String, String> buildResultMap(String title, String h1, String description) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("title", title);
        resultMap.put("h1", h1);
        resultMap.put("description", description);
        return resultMap;
    }

}
