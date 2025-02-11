package hexlet.code.controller;

import hexlet.code.util.controller.ControllerUtils;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlChecksRepository;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.util.view.NamedRoutes;
import hexlet.code.util.html.HtmlFetcher;
import hexlet.code.util.validation.UrlValidator;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;

import static io.javalin.rendering.template.TemplateUtil.model;

@Slf4j
public class UrlsController {

    public static void showUrlsPage(Context ctx) {
        try {
            var urls = UrlsRepository.getEntities();
            var urlCheckData = getUrlCheckData(urls);
            var page = new UrlsPage(urlCheckData);
            ControllerUtils.addFlashMessageToPage(ctx, page);

            ctx.render("urls/urls.jte", model("page", page));
        } catch (SQLException e) {
            log.error("Ошибка при получении списка URL: ", e);
            ControllerUtils.errorHandler(ctx, "Ошибка при получении данных. Попробуйте позже.");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

    public static void post(Context ctx) {
        String tempName = ctx.formParam("url");

        try {
            if (UrlValidator.isValid(tempName)) {
                handleValidUrl(ctx, tempName);
            } else {
                ControllerUtils.errorHandler(ctx, "Некорректный URL");
                ctx.redirect(NamedRoutes.rootPath());
            }
        } catch (URISyntaxException | IllegalArgumentException | MalformedURLException e) {
            log.error("Ошибка валидации URL: {}", tempName, e);
            ControllerUtils.errorHandler(ctx, "Некорректный URL");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

    public static void showUrlPage(Context ctx) throws SQLException {
        var pathId = ctx.pathParam("id");
        var url = getUrlById(pathId);
        var checks = UrlChecksRepository.getEntities(url.getId());

        var page = checks.isEmpty() ? UrlPage.createPageWithoutUrlChecks(url)
                : new UrlPage(url, checks);
        ControllerUtils.addFlashMessageToPage(ctx, page);

        ctx.render("urls/url.jte", model("page", page));
    }

    public static void checkUrl(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = getUrlById(id);


        var check = HtmlFetcher.getUrlCheckResult(url);

        if (check == null) {
            log.error("Ошибка при проверке URL: {}", url.getName());
            ControllerUtils.errorHandler(ctx, "Некорректный адрес");
        } else {
            check.setUrlId(url.getId());
            UrlChecksRepository.save(check);
            ControllerUtils.setFlashMessage(ctx, "Страница успешно проверена", "success");
        }
        ctx.redirect(NamedRoutes.urlPath(id));
    }

    private static HashMap<Url, UrlCheck> getUrlCheckData(Iterable<Url> urls) throws SQLException {
        var result = new HashMap<Url, UrlCheck>();
        for (var url : urls) {
            var lastCheck = UrlChecksRepository.getLastCheck(url.getId());
            result.put(url, lastCheck.orElse(null));
        }
        return result;
    }

    private static Url getUrlById(String pathId) {
        try {
            return UrlsRepository.find(pathId)
                    .orElseThrow(() -> {
                        log.error("URL с id = {} не найден", pathId);
                        return new NotFoundResponse("Url with id = " + pathId + " not found");
                    });
        } catch (SQLException e) {
            log.error("Ошибка при поиске URL по id: {}", pathId, e);
            throw new RuntimeException("Ошибка при поиске URL", e);
        }
    }

    private static Url getUrlById(Long urlId) {
        return getUrlById(String.valueOf(urlId));
    }

    private static void handleValidUrl(Context ctx, String tempName) {
        String name = extractBaseUrl(tempName);
        try {
            if (UrlsRepository.findByUrl(name).isEmpty()) {
                var url = Url.createUrlWithTimestampNow(name);
                UrlsRepository.save(url);
                ControllerUtils.setFlashMessage(ctx, "Страница успешно добавлена", "success");
                ctx.redirect(NamedRoutes.urlsPath());
            } else {
                ControllerUtils.setFlashMessage(ctx, "Страница уже существует", "info");
                ctx.redirect(NamedRoutes.urlsPath());
            }
        } catch (SQLException e) {
            log.error("Ошибка при сохранении URL: {}", tempName, e);
            ControllerUtils.errorHandler(ctx, "Ошибка при добавлении страницы");
            ctx.redirect(NamedRoutes.urlsPath());
        }
    }

    private static String extractBaseUrl(String url) {
        try {
            return UrlValidator.extractBaseUrl(url);
        } catch (URISyntaxException | MalformedURLException e) {
            log.error("Ошибка при извлечении базового URL: {}", url, e);
            throw new RuntimeException(e);
        }
    }
}
