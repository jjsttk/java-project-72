package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlsRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (var conn = getDataSource().getConnection();
             var stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, url.getName());
            var timestamp = Timestamp.valueOf(LocalDateTime.now());
            stmt.setTimestamp(2, timestamp);
            stmt.executeUpdate();
            var generatedKey = stmt.getGeneratedKeys();
            if (generatedKey.next()) {
                url.setId(generatedKey.getLong(1));
                url.setCreatedAt(timestamp);
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static Optional<Url> findByUrl(String urlString) throws SQLException {
        String sql = "SELECT * FROM urls WHERE name = ?";
        try (var conn = getDataSource().getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, urlString);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    var id = rs.getLong("id");
                    var name = rs.getString("name");
                    var createdAt = rs.getTimestamp("created_at");
                    var url = new Url(id, name, createdAt);
                    return Optional.of(url);
                }
            }
            return Optional.empty();
        }
    }

    public static Optional<Url> find(Long id) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id = ?";
        try (var conn = getDataSource().getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    var name = rs.getString("name");
                    var createdAt = rs.getTimestamp("created_at");
                    var url = new Url(id, name, createdAt);
                    return Optional.of(url);
                }
            }
            return Optional.empty();
        }
    }

    public static Optional<Url> find(String id) throws SQLException {
        return find(Long.valueOf(id));
    }

    public static List<Url> getEntities() throws SQLException {
        var sql = "SELECT * FROM urls";
        try (var conn = getDataSource().getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            var result = new ArrayList<Url>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at");
                var url = new Url(id, name, createdAt);
                result.add(url);
            }
            return result;
        }
    }
}
