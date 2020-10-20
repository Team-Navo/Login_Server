package Login;

import java.net.Socket;

public class ClientHandler {
    Socket clientSocket;

    ServerRecv_Send serverRecv; // 서버 발신
    //ServerSend serverSend; // 서버 수신

    public ClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
        streamSetting();
    }

    // 스트림 설정
    public void streamSetting() {
        try{
            clientSocket.getInetAddress(); // 서버 주소
            serverRecv = new ServerRecv_Send(clientSocket);
            //serverSend = new ServerSend(clientSocket);
            new Thread(serverRecv).start(); // 서버 수신 스레드 생성
            //new Thread(serverSend).start(); // 서버 발신 스레드 생성
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
