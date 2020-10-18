import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    private Socket clientSocket;
    BufferedReader in; // 수신 데이터
    PrintWriter out; // 발신 데이터

    public static void main(String[] args) {
        new Client2();
    }

    public Client2() {
        connect();
        streamSetting();
        dataSend();
        dataRecv();
    }

    // 서버와 연결
    public void connect() {
        try{
            clientSocket = new Socket("127.0.0.1",10002); // 클라이언트 정보
            System.out.println("[Client] Connected to server");
        }
        catch(Exception e) {
            System.out.println(e.toString() + "Client 28");
        }
    }

    // 스트림 세팅
    public void streamSetting() {
        try{
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // 입력 스트림
            out = new PrintWriter(clientSocket.getOutputStream(), true); // 출력 스트림
        }
        catch(Exception e) {
            System.err.println(e.toString()+ "Client 39");
        }
    }

    // 데이터 수신
    public void dataRecv() {
        new Thread(new Runnable() {
            boolean isThread = true;

            @Override
            public void run() {
                while(isThread)
                    try {
                        String recvData = in.readLine();
                        if(recvData.equals("/quit")) // 수신 데이터가 quit 면
                            isThread = false; // 데이터 수신 종료
                        else
                            System.out.println("Server : " + recvData); // 수신 데이터 출력
                    }
                    catch (Exception e) {
                        System.err.println(e.toString() + "Client 59");

                        try {
                            if(clientSocket != null)
                                clientSocket.close(); // 클라이언트 소켓 종료
                            in.close(); // 입력 버퍼 종료
                        } catch (IOException ioE) {
                            ioE.printStackTrace();
                        }
                        isThread = false;
                    }
            }
        }).start();
    }

    // 데이터 발신
    public void dataSend() {
        new Thread(new Runnable() {
            Scanner input = new Scanner(System.in);
            boolean isThread = true;

            @Override
            public void run() {
                while (isThread)
                    try {
                        String sendData = input.nextLine(); // 발신 데이터 입력
                        // input = "{name:soneunsil}
                        if(sendData.equals("/quit"))
                            isThread = false;
                        else
                            out.println(sendData); // 발신 데이터 출력
                    }
                    catch (Exception e) {
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
        }).start();
    }
}


