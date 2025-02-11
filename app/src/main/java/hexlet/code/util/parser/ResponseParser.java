package hexlet.code.util.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.mashape.unirest.http.HttpResponse;

public class ResponseParser {
    private static final String REGEX_EXTRACT_TITLE_RULE = "<title>(.*?)</title>";
    private static final String REGEX_EXTRACT_H1_RULE = "<h1.*?>(.*?)</h1>";
    private static final String REGEX_DESCRIPTION_RULE =
            "<meta[^>]*name=[\"']description[\"'][^>]*content=[\"'](.*?)[\"']";

    public static Map<String, String> parse(HttpResponse<String> response) {
        var htmlBody = response.getBody();

        String title = extractTagContent(htmlBody, REGEX_EXTRACT_TITLE_RULE);
        String h1 = extractTagContent(htmlBody, REGEX_EXTRACT_H1_RULE);
        String description = extractTagContent(htmlBody, REGEX_DESCRIPTION_RULE);

        Map<String, String> parseResult = new HashMap<>();
        parseResult.put("title", title);
        parseResult.put("h1", h1);
        parseResult.put("description", description);

        return parseResult;
    }

    private static String extractTagContent(String html, String regexRule) {
        Pattern pattern = Pattern.compile(regexRule, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        return matcher.find() ? matcher.group(1).trim() : null;
    }

}
