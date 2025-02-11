package hexlet.code;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.controller.RootController;
import hexlet.code.controller.UrlsController;
import hexlet.code.config.db.DatabaseConfig;
import hexlet.code.util.view.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.SQLException;

@Slf4j
public class App {
    public static Javalin getApp() throws SQLException, IOException {
        DatabaseConfig.configure();

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        // ctx
        app.get(NamedRoutes.rootPath(), RootController::showIndexPage);
        app.get(NamedRoutes.urlsPath(), UrlsController::showUrlsPage);
        app.get(NamedRoutes.urlPath("{id}"), UrlsController::showUrlPage);
        app.post(NamedRoutes.urlsPath(), UrlsController::post);
        app.post(NamedRoutes.urlChecksPath("{id}"), UrlsController::checkUrl);

        return app;
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    public static void main(String[] args) throws SQLException, IOException {
        Javalin app = getApp();
        app.start(getPort());
    }
}
