package ChatClient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        OutputStream outputStream = null;
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8888);
            outputStream = socket.getOutputStream();
            Scanner sc = new Scanner(System.in);
            System.out.print("please input your name:");
            String clientName = sc.nextLine();
            //向服务端注册
            String loginMsg = "login&" + clientName;
            outputStream.write(loginMsg.getBytes());
            outputStream.flush();

            //开启新线程，实时读取服务端返回数据
            InputStream inputStream = socket.getInputStream();
            byte[] recv = new byte[1024];
            int len = inputStream.read(recv);
            if (len != -1) {
                String recvMsg = new String(recv, 0, len);
                if(recvMsg.equals("login wrong")) {
                    System.out.println("login wrong");
                    System.exit(-1);
                }
            }

            Thread t = new Thread(new ChatClient(socket));
            t.setDaemon(true);
            t.start();

            //聊天
            boolean isAlive = true;
            while (isAlive) {
                String sendMsg = sc.nextLine();
                if(sendMsg.equals("bye")) {
                    isAlive = false;
                }
                sendMsg = sendMsg + "&" + clientName;
                outputStream.write(sendMsg.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(outputStream != null) {
                    outputStream.close();
                }
                if(socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
