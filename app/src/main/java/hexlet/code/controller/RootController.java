package hexlet.code.controller;

import hexlet.code.dto.IndexPage;
import io.javalin.http.Context;

import static io.javalin.rendering.template.TemplateUtil.model;

public class RootController {
    public static void index(Context ctx) {
        String flashMessage =ctx.consumeSessionAttribute("flash");
        String flashType = ctx.sessionAttribute("flashType");
        var page = new IndexPage();
        if (flashMessage != null) {
            page.setFlash(flashMessage, flashType);
        }
        ctx.render("index.jte", model("page", page));
    }
}
