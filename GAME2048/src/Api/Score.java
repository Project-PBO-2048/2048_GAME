import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Score {
    public static int userScore;
    public static int userPrevScore;

    public static ArrayList<String> usernames = new ArrayList<>();
    public static ArrayList<Integer> highscores = new ArrayList<>();

    public static int getScore() {
        return userScore;
    }
    public static int getPrevScore() {
        return userPrevScore;
    }

    public static void updateScore(int update) {
        userPrevScore=userScore;

        userScore+=update;
        System.out.println("user score : " + userScore);
    }
    public static void setUserScore(int score){
        userScore=score;
    }

    public static void updateScore() throws SQLException {
        System.out.println("user id : " + JDBC.user_id);
        System.out.println("user score : " + userScore);
        String getHighestScoreQuery = "SELECT MAX(score) AS highest_score FROM score WHERE player_id = '" + JDBC.user_id + "'";


        int highestScore = 0; // Skor tertinggi default
        try (Statement stmt = JDBC.client.createStatement()) {
            ResultSet rs = stmt.executeQuery(getHighestScoreQuery);
            if (rs.next()) {
                highestScore = rs.getInt("highest_score");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (userScore > highestScore) {
            String updateScoreQuery = "UPDATE score SET score = " + userScore + " WHERE player_id = '" + JDBC.user_id + "'";
            System.out.println("hhhhhmmm " + userScore);

            try (Statement stmt = JDBC.client.createStatement()) {
                stmt.executeUpdate(updateScoreQuery);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static void getUserScore() throws SQLException {
        String query = "SELECT u.username, s.score FROM users u INNER JOIN score s ON u.id = s.player_id ORDER BY s.score DESC";
        usernames.clear();
        highscores.clear();
        try (Statement stmt = JDBC.client.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                usernames.add(rs.getString("username"));
                highscores.add(rs.getInt("score"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
