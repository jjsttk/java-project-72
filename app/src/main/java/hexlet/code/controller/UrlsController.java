package hexlet.code.controller;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.util.NamedRoutes;
import hexlet.code.util.UrlValidator;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {

    public static void index(Context ctx) throws SQLException {
        var urls = UrlsRepository.getEntities();
        var page = new UrlsPage(urls);

        String flashMessage = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");

        if (flashMessage != null && flashType != null) {
            page.setFlash(flashMessage, flashType);
        }
        ctx.render("urls/urls.jte", model("page", page));
    }

    public static void post(Context ctx) throws SQLException {
        var tempName = ctx.formParam("url");

        try {
            if (UrlValidator.isValid(tempName)) {
                var name = UrlValidator.extractBaseUrl(tempName);
                if (UrlsRepository.findByUrl(name).isEmpty()) {
                    var url = new Url(name);
                    UrlsRepository.save(url);
                    ctx.sessionAttribute("flash", "Страница успешно добавлена");
                    ctx.sessionAttribute("flashType", "success");
                    ctx.redirect(NamedRoutes.urlsPath());
                } else {
                    ctx.sessionAttribute("flash", "Страница уже существует");
                    ctx.sessionAttribute("flashType", "info");
                    ctx.redirect(NamedRoutes.urlsPath());
                }
            }
        } catch (IllegalArgumentException | URISyntaxException | MalformedURLException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "danger");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

    public static void showUrlPage(Context ctx) throws SQLException {
        var pathId = ctx.pathParam("id");
        var url = UrlsRepository.find(pathId)
                .orElseThrow(() -> new NotFoundResponse("Url with id = " + pathId + " not found"));
        var page = new UrlPage(url);
        ctx.render("urls/url.jte", model("page", page));
    }
}
