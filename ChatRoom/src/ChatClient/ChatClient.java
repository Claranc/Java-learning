package ChatClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient implements Runnable {
    private Socket socket;
    private boolean isAlive = true;
    private OutputStream outputStream = null;
    private InputStream inputStream = null;

    public ChatClient(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
            while (true) {
                byte[] recv = new byte[1024];
                int len = inputStream.read(recv);
                if (len != -1) {
                    String recvMsg = new String(recv, 0, len);
                    ProcessRecvMsg(recvMsg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(inputStream != null) {
                    inputStream.close();
                }
                if(socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void ProcessRecvMsg(String recvMsg) {
        String[] args = recvMsg.split("&");
        if (args.length == 1) {
            System.out.println(recvMsg);
        } else if (args.length == 4) {
            System.out.println("you receive message from " + args[3] + ", content:" + args[2]);
        } else {
            System.out.printf("unexpected recvMsg, msg:%s\n", recvMsg);
        }
    }
}
