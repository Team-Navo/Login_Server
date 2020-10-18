import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket; // 서버 소켓 생성
    private static ArrayList<ClientHandler> clientList = new ArrayList<>(); // 접속 클라이언트들

    // 서버 설정
    public void serverSetting() {
        try{
            System.out.println("[Server] ---JAVA GAME SERVER 가동---");
            serverSocket = new ServerSocket(10002); // 생성&바인드
            System.out.println("[Server] socket create and bind");

        }
        catch(Exception e) {
            System.err.println(e.toString() + "Server20");
        }
    }

    public Server() {
        serverSetting();
    }

    public static void main(String[] args) {
        Socket clientSocket;
        Server s = new Server(); // 서버 객체 생성

        while(true) {
            try {
                clientList.removeIf(ch -> ch.clientSocket.isClosed());
                System.out.println("[Server] Waiting for client connection...");
                System.out.println("[Server] Connected Client : " + clientList.size()); // 접속한 클라이언트 수
                clientSocket = s.serverSocket.accept(); // 클라이언트 접속 대기
                System.out.println("[Server] Connected to client!");

                ClientHandler client = new ClientHandler(clientSocket); // 접속한 클라이언트에 대한 클라이언트 핸들러 생성
                Server.clientList.add(client); // 클라이언트 리스트에 접속한 클라이언트 추가

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}