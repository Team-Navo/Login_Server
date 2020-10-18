import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerSend implements Runnable{
    Socket clientSocket;
    PrintWriter out; // 발신 데이터
    String sendData;

    public ServerSend(Socket clientSocket) throws IOException {
        out = new PrintWriter(clientSocket.getOutputStream(), true); // 클라이언트의 출력을 읽는 객체를 out에 저장
    }

    /*
    public ServerSend(String sendData) {
        this.sendData = sendData;
    }*/

    @Override
    public void run() {
        boolean isThread = true;
        Scanner input = new Scanner(System.in);

        while(isThread) {
            try {
                sendData = input.nextLine();
                if (sendData.equals("/quit")) {
                    isThread = false;
                } else
                    out.println(sendData);
            } catch (Exception e) {
                System.err.println(e.toString());

                try {
                    if(clientSocket != null)
                        clientSocket.close();
                    out.close();
                } catch (IOException ioE) {
                    ioE.printStackTrace();
                }
                isThread = false;
            }
        }
    }
}
