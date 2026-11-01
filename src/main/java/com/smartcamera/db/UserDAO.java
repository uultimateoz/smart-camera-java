package com.smartcamera.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.smartcamera.model.User;

public class UserDAO {

    public User findUserByEmail(String email)
            throws SQLException {

        String sql = """
            SELECT user_id, username, email,
                   password_hash, role,
                   failed_attempts, locked_until
            FROM users
            WHERE email = ?
            """;
            try (Connection connection =
                 DatabaseConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet result =
                     statement.executeQuery()) {

                if (result.next()) {

                    Timestamp timestamp =
                        result.getTimestamp("locked_until");

                    LocalDateTime lockedUntil =
                        timestamp == null
                            ? null
                            : timestamp.toLocalDateTime();

                    return new User(
                        result.getInt("user_id"),
                        result.getString("username"),
                        result.getString("email"),
                        result.getString("password_hash"),
                        result.getString("role"),
                        result.getInt("failed_attempts"),
                        lockedUntil
                    );
                }
            }
        }

        return null;
    }
    
    public void insertUser(String email, String passwordHash, String role) throws SQLException {
        String sql = "INSERT INTO users (email, password_hasd, failed_attempts) "
                     +"VALUES (?, ?, ?, 0)";
        
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, email);
            statement.setString(2, passwordHash);
            statement.setString(3, role); 
            
            statement.executeUpdate();
        }
    }

    public void updateFailedAttempts(
            int userId,
            int attempts,
            LocalDateTime lockedUntil)
            throws SQLException {

        String sql = """
            UPDATE users
            SET failed_attempts = ?,
                locked_until = ?
            WHERE user_id = ?
            """;

        try (Connection connection =
                 DatabaseConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql)) {

            statement.setInt(1, attempts);

            if (lockedUntil == null) {
                statement.setNull(
                    2, java.sql.Types.TIMESTAMP
                );
            } else {
                statement.setTimestamp(
                    2, Timestamp.valueOf(lockedUntil)
                );
            }

            statement.setInt(3, userId);

            statement.executeUpdate();
        }
    }
}