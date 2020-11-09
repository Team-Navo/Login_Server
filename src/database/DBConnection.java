package database;

import database.table.User;
import java.sql.*;

public class DBConnection {
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // DB 드라이버
    private final String DB_URL = "jdbc:mysql://localhost:3306/user?&serverTimezone=UTC&useSSL=false";  // 접속할 DB 서버
    private final static String USER_NAME = "root"; // DB에 접속할 사용자 이름
    private final static String PASSWORD = "12345"; // 사용자 비밀번호
    private static DBConnection connector;
    private static Connection conn;

    String id, pw;

    // DB 연결 생성
    public DBConnection(){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DB 연결 확인
    public static DBConnection getConnector() {
        if(connector == null)
            connector = new DBConnection();
        return connector;
    }

    // ID 중복 체크
    public boolean idCheck(User user) {
        String sql = "SELECT id FROM user WHERE id=?;";
        PreparedStatement pstate = null;
        boolean result = false;
        try {
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, user.getId());
            ResultSet rs = pstate.executeQuery();

            if(rs.next()) {
                if(rs.getString(1).equals(user.getId())) {
                    result = true;
                } else {
                    result = false;
                }
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
        return result;
    }

    // 회원가입
    public boolean createUser(User user) {
        String sql = "insert into user values(?, ?, ?, ?, ?);";
        PreparedStatement pstate = null;
        boolean result = false;
        try {
            if(idCheck(user)) { // id 중복 체크
                result = false;
            } else {
                pstate = conn.prepareStatement(sql);
                pstate.setString(1, user.getId());
                pstate.setString(2, user.getPw());
                pstate.setString(3, user.getName());
                pstate.setString(4, user.getBirth());
                pstate.setString(5, user.getPhone());
                int rs = pstate.executeUpdate();

                if(rs == 1) {
                    result = true;
                } else {
                    result = false;
                }
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
        return result;
    }

    // 로그인
    public boolean userLogin(User user) {
        String sql = "SELECT pw FROM user WHERE id=?;";
        PreparedStatement pstate = null;
        boolean result = false;

        try {
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, user.getId());
            ResultSet rs = pstate.executeQuery();
            if(rs.next()) {
                if(rs.getString(1).equals(user.getPw())) {
                    result = true;
                } else {
                    result = false;
                }
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
        return result;
    }

    // id 찾기
    public boolean findID(User user) {
        String sql = "SELECT id FROM user WHERE name=? AND birth=?;";
        PreparedStatement pstate = null;
        boolean result = false;

        try {
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, user.getName());
            pstate.setString(2, user.getBirth());
            ResultSet rs = pstate.executeQuery();

            if(rs.next()) {
                id = rs.getString("id");
                result = true;
            } else {
                result = false;
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
        return result;
    }

    // pw 찾기
    public boolean findPW(User user) {
        String sql = "SELECT pw FROM user WHERE id=? AND name=?;";
        PreparedStatement pstate = null;
        boolean result = false;

        try {
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, user.getId());
            pstate.setString(2, user.getName());
            ResultSet rs = pstate.executeQuery();

            if(rs.next()) {
                pw = rs.getString("pw");
                result = true;
            } else {
                result = false;
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
        return result;
    }

    public String getID() {
        return id;
    }
    public String getPW() {
        return pw;
    }

}
