package hexlet.code.repository;

import com.zaxxer.hikari.HikariDataSource;

public class BaseRepository {
    private static HikariDataSource dataSource;

    public static void setDataSource(HikariDataSource newDataSource) {
        BaseRepository.dataSource = newDataSource;
    }
}
