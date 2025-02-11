package hexlet.code.util.validation;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class UrlValidator {
    public static boolean isValid(String url) throws URISyntaxException, MalformedURLException {
        if (url == null || url.isBlank()) {
            return false;
        }
        var temp = new URI(url).toURL();
        return true;
    }
    public static String extractBaseUrl(String url) throws URISyntaxException, MalformedURLException {
        var javaUrl = new URI(url).toURL();

        String protocol = javaUrl.getProtocol();
        String host = javaUrl.getHost();
        int port = javaUrl.getPort();

        return portIsPresent(port)
                ? String.format("%s://%s:%d", protocol, host, port)
                : String.format("%s://%s", protocol, host);
    }

    private static boolean portIsPresent(int port) {
        return port != -1;
    }
}
