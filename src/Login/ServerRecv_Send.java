import java.io.*;
import java.net.Socket;

public class ServerRecv_Send implements Runnable {
    private static boolean checkDB;
    private static boolean checkData;
    Socket clientSocket;
    PrintWriter out;
    BufferedReader in;

    public ServerRecv_Send(Socket clientSocket) throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
    }

    @Override
    public void run() {
        boolean isThread = true;
        checkDB = true;
        checkData = true;

        while (isThread) {
            try {
                String recvData = in.readLine(); // 수신 데이터를 한 줄씩 읽어 문자열로 저장
                if (recvData.equals("/quit")) {
                    isThread = false;
                } else {
                    new ParsingToJSON(recvData); // 제이슨 파싱 클래스

                    if (checkData == false) {
                        out.println("[Wrong Data]");
                        System.err.println("[Wrong Data]");
                    }

                    if (checkDB) {
                        out.println("[DB Success]");
                        System.out.println("[DB Success]");
                    } else {
                        out.println("[DB Failed]");
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
    static void checkDB (boolean check){
        checkDB = check;
    }
    static void checkData (boolean check){ checkData = check; }
}
