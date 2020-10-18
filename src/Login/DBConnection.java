import java.sql.*;

public class DBConnection {
    // private 선언 -> 외부 접근 차단
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // 드라이버
    private final String DB_URL = "jdbc:mysql://localhost:3306/user?&serverTimezone=UTC&useSSL=false";  // 접속할 DB 서버
    private final String USER_NAME = "root"; // DB에 접속할 사용자 이름
    private final String PASSWORD = "12345"; // 사용자 비밀번호
    private Connection conn;

    public DBConnection() {
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            System.out.println("[DB Connected Success]");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // 오류 발생의 근원지를 찾아 단계별로 오류 출력
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 회원가입 String name, int birth, int phone, String id, String pw, String qna
    public boolean insertUser(User user) {
        String sql = "insert into user values(?, ?, ?, ?, ?, ?);";
        PreparedStatement pstate = null;

        try {
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, user.getName());
            pstate.setInt(1, user.getBirth());
            pstate.setInt(1, user.getPhone());
            pstate.setString(1, user.getId());
            pstate.setString(1, user.getPw());
            pstate.setString(1, user.getQna());
            pstate.executeUpdate();

            System.out.println("[회원가입 성공]");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstate != null && !pstate.isClosed())
                    pstate.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    // pw 변경
    public void updateUser(User user) {
        String sql = "update user set pw = ?;";
        PreparedStatement pstate = null;

        try {
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, user.getPw());
            pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstate != null && !pstate.isClosed())
                    pstate.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 로그인
    public void selectLogin(User user) {
        String sql = "select id, pw from user where id = ? AND pw = ?;";
        PreparedStatement pstate = null;
        User u = new User();

        try {
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, user.getId());
            pstate.setString(2, user.getPw());
            ResultSet rs = pstate.executeQuery();

            if (rs != null) {
                System.out.println("[로그인 성공]");
                ServerRecv.checkDB(true);
            } else {
                System.out.println("[로그인 실패]");
                ServerRecv.checkDB(false);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstate != null && !pstate.isClosed())
                    pstate.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // id 찾기
    public User selectId(User user) {
        String sql = "select name, birth, phone from user where name = ? and birth = ? and phone = ?;";
        PreparedStatement pstate = null;
        User u = new User();

        try {
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, user.getName());
            pstate.setInt(2, user.getBirth());
            pstate.setInt(3, user.getPhone());

            System.out.println("[ID 찾기 성공]");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstate != null && !pstate.isClosed())
                    pstate.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return u;
    }

    // pw 찾기
    public User selectPw(User user) {
        String sql = "select * from user where name = ?;";
        PreparedStatement pstate = null;
        User u = new User();

        try {
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, user.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstate != null && !pstate.isClosed())
                    pstate.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return u;
    }
}
