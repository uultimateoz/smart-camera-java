package com.smartcamera.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventDAO {

    public void addEvent(
            String cameraId,
            String location,
            String mediaUrl) throws SQLException {

        String sql = """
            INSERT INTO events (camera_id, location, media_url)
            VALUES (?, ?, ?)
            """;

        try ( Connection connection
                = DatabaseConnection.getConnection();  PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setString(1, cameraId);
            statement.setString(2, location);
            statement.setString(3, mediaUrl);

            statement.executeUpdate();
        }
    }
}
