package hexlet.code.util.file;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileReaderTest {

    @Test
    void testReadResourceFile() throws IOException {
        String content = FileReader.readResourceFile("fixtures/test.txt");
        assertEquals("Hello, world!", content);

        assertThrows(FileNotFoundException.class, () -> FileReader.readResourceFile("fixtures/nonExistentFile.txt"));

        String emptyContent = FileReader.readResourceFile("fixtures/empty.txt");
        assertEquals("", emptyContent);

        String multiLineContent = FileReader.readResourceFile("fixtures/multiLine.txt");
        assertEquals("Line 1\nLine 2", multiLineContent);

    }
}
