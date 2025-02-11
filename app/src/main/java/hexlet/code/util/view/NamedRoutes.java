package hexlet.code.util.view;

public class NamedRoutes {
    public static String rootPath() {
        return "/";
    }
    public static String urlsPath() {
        return rootPath() + "urls";
    }
    public static String urlPath(Long id) {
        return urlPath(id.toString());
    }
    public static String urlPath(String id) {
        return rootPath() + "urls/" + id;
    }
    public static String urlChecksPath(String id) {
        return urlPath(id) + "/checks";
    }
    public static String urlChecksPath(Long id) {
        return urlChecksPath(id.toString());
    }
}
