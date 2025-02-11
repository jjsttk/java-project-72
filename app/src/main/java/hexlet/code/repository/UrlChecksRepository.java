package hexlet.code.repository;

import hexlet.code.model.UrlCheck;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UrlChecksRepository extends BaseRepository {

    public static void save(UrlCheck checkResult) {
        String sql = "INSERT INTO url_checks (url_id, status_code, title, h1, description, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (var conn = getDataSource().getConnection();
             var stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, checkResult.getUrlId());
            stmt.setInt(2, checkResult.getStatusCode());
            stmt.setString(3, checkResult.getTitle());
            stmt.setString(4, checkResult.getH1());
            stmt.setString(5, checkResult.getDescription());
            stmt.setTimestamp(6, checkResult.getCreatedAt());
            stmt.executeUpdate();

            var generatedKey = stmt.getGeneratedKeys();
            if (generatedKey.next()) {
                checkResult.setId(generatedKey.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static List<UrlCheck> getEntities(Long urlId) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY created_at DESC";
        try (var conn = getDataSource().getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            var rs = stmt.executeQuery();
            var result = new ArrayList<UrlCheck>();
            while (rs.next()) {
                var id = rs.getLong("id");
                var statusCode = rs.getInt("status_code");
                var title = rs.getString("title");
                var h1 = rs.getString("h1");
                var description = rs.getString("description");
                var createdAt = rs.getTimestamp("created_at");
                var check = new UrlCheck(
                        id, statusCode, title,
                        h1, description, urlId, createdAt);
                result.add(check);
            }
            return result;
        }
    }

    public static Optional<UrlCheck> getLastCheck(Long urlId) throws SQLException {
        var sql = "SELECT status_code, created_at "
                + "FROM url_checks "
                + "WHERE url_id = ? "
                + "ORDER BY created_at DESC LIMIT 1";
        try (var conn = getDataSource().getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                var statusCode = rs.getInt("status_code");
                var createdAt = rs.getTimestamp("created_at");
                var check = UrlCheck.forLastCheck(statusCode, createdAt);
                return Optional.of(check);
            }
        }
        return Optional.empty();
    }
}
