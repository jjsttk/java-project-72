package hexlet.code.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePage {
    private String flashMessage;
    private String flashType; // alert type = danger/info/success

    public void setFlash(String message, String type) {
        this.flashMessage = message;
        this.flashType = type;
    }
}
