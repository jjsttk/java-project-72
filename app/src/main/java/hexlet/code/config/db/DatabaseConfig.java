package hexlet.code.config.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.repository.BaseRepository;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static hexlet.code.util.file.FileReader.readResourceFile;

@Slf4j
public class DatabaseConfig {
    private static final String JDBC_DATABASE_URL = System.getenv("JDBC_DATABASE_URL");
    private static final String DEFAULT_H2_URL = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;";

    public static void configure() throws SQLException, IOException {
        var dataSource = createDataSource();
        String schemaSql = loadSchemaScript();

        initializeDatabase(dataSource, schemaSql);
        BaseRepository.setDataSource(dataSource);
    }

    private static HikariDataSource createDataSource() {
        var config = new HikariConfig();
        if (JDBC_DATABASE_URL != null) {
            config.setJdbcUrl(JDBC_DATABASE_URL);
            log.info("Using PostgreSQL database.");
        } else {
            config.setJdbcUrl(DEFAULT_H2_URL);
            log.info("Using in-memory H2 database.");
        }
        return new HikariDataSource(config);
    }

    private static String loadSchemaScript() throws IOException {
        return JDBC_DATABASE_URL != null ? readResourceFile("PostgresSchema.sql")
                : readResourceFile("H2schema.sql");
    }

    private static void initializeDatabase(HikariDataSource dataSource, String schemaSql) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(schemaSql);
            log.info("Database schema successfully initialized.");
        }
    }
}
