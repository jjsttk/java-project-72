package hexlet.code.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.repository.BaseRepository;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.SQLException;

import static hexlet.code.util.FileReader.readResourceFile;

@Slf4j
public class DatabaseConfig {
    private static final String ENV_DATABASE_URL = System.getenv("JDBC_DATABASE_URL");

    public static void configure() throws SQLException, IOException {
        var config = new HikariConfig();
        String sql;

        // DB switch logic
        if (ENV_DATABASE_URL != null) {
            // PostgreSQL in prod
            config.setJdbcUrl(ENV_DATABASE_URL);
            sql = readResourceFile("PostgresSchema.sql");
        } else {
            // H2 locally
            config.setJdbcUrl("jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
            sql = readResourceFile("H2schema.sql");
        }
        var dataSource = new HikariDataSource(config);
        log.info(sql);
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }

        BaseRepository.setDataSource(dataSource);
    }
}
