package hexlet.code.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePage {
    private String flashMessage;
    private String flashType; // alert type = danger/info/success

    /**
     * Sets a flash message and its type in the session.
     * <p>
     * This method is used to add flash messages that will be displayed on the next page after a redirect.
     * The flash messages are stored in the session and can be retrieved using
     * {@code ctx.consumeSessionAttribute("flash")}.
     * The message type is also stored in the session and can be used for styling or
     * controlling how the message is displayed.
     * <p>
     *
     * @param message The message to be displayed on the next page.
     * @param type The type of the flash message (e.g., "success", "info", "danger").
     */
    public void setFlash(String message, String type) {
        this.flashMessage = message;
        this.flashType = type;
    }
}
