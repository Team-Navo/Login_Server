import java.io.*;
import java.net.Socket;

public class ServerRecv implements Runnable {
    private static boolean checkDB;
    Socket clientSocket;
    PrintWriter writer;
    BufferedReader in; // 수신 데이터

    public ServerRecv(Socket clientSocket) throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // 클라이언트의 입력을 읽는 객체를 in에 저장
        writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
    }

    @Override
    public void run() {
        boolean isThread = true;

        while (isThread) {
            try {
                String recvData = in.readLine(); // 수신 데이터를 한 줄씩 읽어 문자열로 저장
                if (recvData.equals("/quit")) {
                    isThread = false;
                } else {
                    new ParsingToJSON(recvData); // 제이슨 파싱 클래스
                    if (checkDB) {
                        writer.println("[DB Success]");
                        System.out.println("[DB Success]");
                    } else {
                        writer.println("[DB Failed]");
                        System.err.println("[DB Failed]");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

                try {
                    if (clientSocket != null)
                        clientSocket.close();
                    in.close();
                } catch (IOException ioE) {
                    ioE.printStackTrace();
                }
                isThread = false;
            }
        }
    }
    static void checkDB ( boolean check){
        checkDB = check;
    }
}