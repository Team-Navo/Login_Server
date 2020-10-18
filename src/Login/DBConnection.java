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
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 회원가입
    public void createUser(User user) {
        String sql = "insert into user values(?, ?, ?, ?, ?, ?);";
        PreparedStatement pstate = null;

        try {
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, user.getName());
            pstate.setString(2, user.getBirth());
            pstate.setString(3, user.getPhone());
            pstate.setString(4, user.getId());
            pstate.setString(5, user.getPw());
            pstate.setString(6, user.getQna());
            int rs = pstate.executeUpdate();

            if(rs == 1) {
                System.out.println("[회원가입 성공]");
            } else {
                System.out.println("[회원가입 실패]");
                ServerRecv_Send.checkDB(false);
            }
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
    }

    // 로그인
    public void userLogin(User user) {
        String sql = "select id, pw from user where id = ? AND pw = ?;";
        PreparedStatement pstate = null;

        try {
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, user.getId());
            pstate.setString(2, user.getPw());
            ResultSet rs = pstate.executeQuery();

            if (rs != null) {
                System.out.println("[로그인 성공]");
            } else {
                System.out.println("[로그인 실패]");
                ServerRecv_Send.checkDB(false);
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
    public void findID(User user) {
        String sql = "select name, birth, phone from user where name = ? and birth = ? and phone = ?;";
        PreparedStatement pstate = null;
        try {
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, user.getName());
            pstate.setString(2, user.getBirth());
            pstate.setString(3, user.getPhone());
            ResultSet rs = pstate.executeQuery();

            if (rs != null) {
                System.out.println("[ID 찾기 성공]");
            } else {
                System.out.println("[ID 찾기 실패]");
                ServerRecv_Send.checkDB(false);
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

    // pw 찾기
    public void findPW(User user) {
        String sql = "select * from user where name = ?;";
        PreparedStatement pstate = null;

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
    }

    // pw 변경
    public void changePW(User user) {
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
}
