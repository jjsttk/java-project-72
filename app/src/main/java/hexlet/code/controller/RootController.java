package hexlet.code.controller;

import hexlet.code.dto.index.IndexPage;
import hexlet.code.util.controller.ControllerUtils;
import io.javalin.http.Context;

import static io.javalin.rendering.template.TemplateUtil.model;

public class RootController {
    public static void showIndexPage(Context ctx) {
        var page = new IndexPage();
        ControllerUtils.addFlashMessageToPage(ctx, page);
        ctx.render("index.jte", model("page", page));
    }
}
