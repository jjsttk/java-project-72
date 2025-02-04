package hexlet.code.util;

public class NamedRoutes {
    public static String rootPath() {
        return "/";
    }
    public static String urlsPath() {
        return rootPath() + "urls";
    }
    public static String urlPath() {
        return rootPath() + "urls/{id}";
    }
    public static String urlPath(String id) {
        return rootPath() + "urls/" + id;
    }
}
