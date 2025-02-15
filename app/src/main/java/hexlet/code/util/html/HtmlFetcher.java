package hexlet.code.util.html;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.util.parser.ResponseParser;
import lombok.extern.slf4j.Slf4j;

import java.net.IDN;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Slf4j
public final class HtmlFetcher {

    public static UrlCheck getUrlCheckResult(Url url) {
        HttpResponse<String> response = fetchResponse(url);
        if (response == null) {
            return null;
        }

        Map<String, String> parseResult = parseResponse(response);
        return createUrlCheck(response.getStatus(), parseResult);
    }

    public static HttpResponse<String> fetchResponse(Url url) {
        try {
            String normalizedUrl = normalizeUrl(url);
            return Unirest.get(normalizedUrl).asString();
        } catch (UnirestException e) {
            log.error("Ошибка HTTP-запроса: {}", e.getMessage(), e);
            return null;
        } catch (URISyntaxException e) {
            log.error("Некорректный URL: {}", url, e);
            return null;
        }
    }

    private static String normalizeUrl(Url url) throws URISyntaxException {
        URI uri = new URI(url.getName());
        if (!uri.isAbsolute()) {
            throw new URISyntaxException(url.getName(), "Относительный URL недопустим");
        }
        String host = uri.getHost();
        String convertedToAsciiHost = host == null ? null : IDN.toASCII(host);
        return new URI(uri.getScheme(), null, convertedToAsciiHost,
                uri.getPort(), null, null, null)
                .toString();
    }

    private static Map<String, String> parseResponse(HttpResponse<String> response) {
        return ResponseParser.parse(response);
    }

    private static UrlCheck createUrlCheck(int statusCode, Map<String, String> parsedData) {
        String title = parsedData.get("title");
        String h1 = parsedData.get("h1");
        String description = parsedData.get("description");

        return new UrlCheck(statusCode, title, h1, description);
    }
}
