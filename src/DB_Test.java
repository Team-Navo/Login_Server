import java.sql.*;

public class DB_Test {
    // private 선언 -> 외부 접근 차단
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // 드라이버
    private final String DB_URL = "jdbc:mysql://localhost:3306/test?&serverTimezone=UTC&useSSL=false ";  // 접속할 DB 서버
    private final String USER_NAME = "root"; // DB에 접속할 사용자 이름
    private final String PASSWORD = "cpb12345"; // 사용자 비밀번호

    public DB_Test() {
        Connection conn = null;
        Statement state = null;
        String query;
        ResultSet rs;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            state = conn.createStatement();

            query = "Select * From test";
            rs = state.executeQuery(query); // 쿼리문 결과 저장


            while(rs.next()) { // 결과의 개수만큼 반복
                String name = rs.getString("name");
                System.out.println("name: " + name);
            }

            rs.close();
            state.close();
            conn.close();

        } catch(Exception e) {
            System.err.println("Connection Failed");

        } finally {
            try {
                if (state != null)
                    state.close();
            } catch (SQLException e1) {
            }

            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e1) {
            }
        }
        System.out.println("\n[MySQL Closed]");
    }

    public static void main(String[] args) {
        new DB_Test();
    }
}