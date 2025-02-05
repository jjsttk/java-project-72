package hexlet.code;

import hexlet.code.util.DatabaseConfig;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import hexlet.code.repository.BaseRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DatabaseTest {

    @Test
    void testDatabaseInitialization() throws Exception {
        DatabaseConfig.configure();

        try (Connection connection = BaseRepository.getDataSource().getConnection()) {
            assertThat(connection.isValid(2)).isTrue();
        }
    }
}
