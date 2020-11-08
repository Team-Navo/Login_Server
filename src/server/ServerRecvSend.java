package server;

import database.DBConnection;
import database.table.User;
import org.json.simple.JSONObject;
import util.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerRecvSend implements Runnable{
    Socket clientSocket;
    BufferedReader in;
    PrintWriter out;
    DBConnection db;

    public ServerRecvSend(Socket clientSocket) throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    @Override
    public void run() {
        boolean isThread = true;

        while(isThread) {
            try {
                String recvData = in.readLine();

                if (recvData.equals("/quit")) {
                    isThread = false;
                } else {
                    System.out.println("Client: " + recvData);

                    JSONObject json = JsonParser.createJson(recvData); // 클라이언트에게 받은 데이터를 JSON 으로 파싱
                    if(json != null) {
                        // 로그인
                        if(json.get("Header").equals("login")) { // 파싱 데이터의 "Header"가 "login"일 떄
                            db = DBConnection.getConnector(); // DB 연결
                            User user = new User(); // 유저 객체 생성
                            user.setId((String) json.get("id"));
                            user.setPw((String) json.get("pw"));

                            if (db.userLogin(user)) {
                                out.println("[SUCCESS] 로그인");
                                System.out.println("[SUCCESS] 로그인");
                            } else {
                                out.println("[FAIL] 로그인");
                                System.err.println("[FAIL] 로그인");
                            }
                        }
                        // 회원가입
                        else if(json.get("Header").equals("create")) {
                            db = DBConnection.getConnector();
                            User user = new User();
                            user.setName((String)json.get("name"));
                            user.setBirth((String)json.get("birth"));
                            user.setPhone((String)json.get("phone"));
                            user.setId((String)json.get("id"));
                            user.setPw((String)json.get("pw"));

                            if (db.createUser(user)) {
                                out.println("[SUCCESS] 회원가입");
                                System.out.println("[SUCCESS] 회원가입");
                            } else {
                                out.println("[FAIL] 회원가입");
                                System.err.println("[FAIL] 회원가입");
                            }
                        }

                        // id 찾기
                        else if(json.get("Header").equals("id")) {
                            db = DBConnection.getConnector();
                            User user = new User();
                            user.setName((String)json.get("name"));
                            user.setBirth((String)json.get("birth"));
                            user.setPhone((String)json.get("phone"));

                            if (db.findID(user)) {
                                out.println("[SUCCESS] ID 찾기");
                                out.println("[ID: " + db.getID() + "]");
                                System.out.println("[SUCCESS] ID 찾기");
                            } else {
                                out.println("[FAIL] ID 찾기");
                                System.err.println("[FAIL] ID 찾기");
                            }

                        }

                        // pw 찾기
                        else if(json.get("Header").equals("pw")) {
                            db = DBConnection.getConnector();
                            User user = new User();
                            user.setId((String)json.get("id"));
                            user.setName((String)json.get("name"));
                            user.setBirth((String)json.get("birth"));
                            user.setPhone((String)json.get("phone"));

                            if (db.findPW(user)) {
                                out.println("[SUCCESS] PW 찾기");
                                out.println("[PW: " + db.getPW() + "]");
                                System.out.println("[SUCCESS] PW 찾기");
                            } else {
                                out.println("[FAIL] PW 찾기");
                                System.err.println("[FAIL] PW 찾기");
                            }
                        }

                        else {
                            out.println("[WRONG] 잘못된 데이터");
                            System.out.println("[WRONG] 잘못된 데이터");
                        }
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
                try {
                    if(clientSocket != null)
                        clientSocket.close();
                    in.close();
                } catch (IOException ioE) {
                    ioE.printStackTrace();
                }
                isThread = false;
            }
        }
    }
}
