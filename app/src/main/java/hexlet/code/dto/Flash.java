package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Flash {
    private String flashMessage;
    private String flashType;
}
