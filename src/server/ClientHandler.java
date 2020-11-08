package server;

import java.net.Socket;

public class ClientHandler {
    Socket clientSocket;

    ServerRecvSend serverRecvSend;
    //ServerSend serverSend;

    public void streamSetting() {
        try{
            clientSocket.getInetAddress();
            serverRecvSend = new ServerRecvSend(clientSocket);
            //serverSend = new ServerSend(clientSocket);
            new Thread(serverRecvSend).start();
            //new Thread(serverSend).start();
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    public ClientHandler(Socket clientSocket){
            this.clientSocket = clientSocket;
            streamSetting();
    }
}
