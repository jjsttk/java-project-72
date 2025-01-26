import hexlet.code.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldTest {

    @Test
    public void helloWorldTest() {
        var result = Main.helloWorld();
        assertEquals("Hello world!", result);
    }
}
