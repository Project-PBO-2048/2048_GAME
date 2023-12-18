package Api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Register {
    public static void register(Connection connection, String username) throws SQLException {
        String query = "INSERT INTO USERS (username) VALUES ('"+username+"')";
        try (PreparedStatement pstmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User successfully inserted.");

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        JDBC.setUser_id(String.valueOf(userId));
                        System.out.println("Generated User ID: " + userId);
                    } else {
                        System.out.println("Failed to retrieve user ID.");
                    }
                }
            } else {
                System.out.println("Failed to insert user.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
