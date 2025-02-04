package hexlet.code.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class UrlValidator {
    public static boolean isValid(String url) throws URISyntaxException, MalformedURLException {
        if (url == null || url.isBlank()) {
            return false;
        }
        var temp = new URI(url).toURL();
        return true; // Haven't exceptions - true
    }
    public static String extractBaseUrl(String url) throws URISyntaxException, MalformedURLException {
        var javaUrl = new URI(url).toURL(); // URI → URL
        // collect str
        String protocol = javaUrl.getProtocol();
        String host = javaUrl.getHost();
        int port = javaUrl.getPort();
        return (port == -1) ? String.format("%s://%s", protocol, host)
                : String.format("%s://%s:%d", protocol, host, port);
    }
}
