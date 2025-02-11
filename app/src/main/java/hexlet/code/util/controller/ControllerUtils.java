package hexlet.code.util.controller;

import hexlet.code.dto.BasePage;
import hexlet.code.dto.Flash;
import io.javalin.http.Context;

public class ControllerUtils {
    public static void addFlashMessageToPage(Context ctx, BasePage page) {
        String flashMessage = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");
        if (flashMessage != null && flashType != null) {
            page.setFlash(new Flash(flashMessage, flashType));
        }
    }

    public static void setFlashMessage(Context ctx, String message, String type) {
        ctx.sessionAttribute("flash", message);
        ctx.sessionAttribute("flashType", type);
    }

    public static void errorHandler(Context ctx, String errorMessage) {
        ControllerUtils.setFlashMessage(ctx, errorMessage, "danger");
    }
}
