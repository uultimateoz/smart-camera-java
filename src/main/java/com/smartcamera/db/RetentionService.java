
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RetentionService {

    public int deleteExpiredEvents() throws SQLException {

        String sql = """
            DELETE FROM events
            WHERE capture_time < NOW() - INTERVAL 30 DAY
            """;

        try ( Connection connection
                = DatabaseConnection.getConnection();  PreparedStatement statement
                = connection.prepareStatement(sql)) {

            return statement.executeUpdate();
        }
    }
}
