import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    public static Connection client;
    static String user_id;
    public JDBC() {
        try {
            String url = "jdbc:mysql://localhost:3306/game_2048?user=root&password=";
            client = DriverManager.getConnection(url);
            System.out.println("Connection success.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Connection failure.");
            System.exit(1);
        }
    }
    public static void setUser_id(String user_id) {
        JDBC.user_id = user_id;
    }

    public static String getUsername(){
        String username = "";
        try {
            String query = "SELECT username FROM users WHERE id = '" + user_id + "'";
            var stmt = client.createStatement();
            var rs = stmt.executeQuery(query);
            if (rs.next()) {
                username = rs.getString("username");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return username;
    }
    public static String getUser_id() {
        return user_id;
    }
}
