package ChatServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class ChatServer implements Runnable {
    private Socket socket;
    private ServerSocket serverSocket;
    private Map<String, ChatServer> serverThreadMap;
    private boolean isAlive = true;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private String clientName;

    ChatServer(ServerSocket serverSocket, Socket socket, Map<String, ChatServer> serverThreadMap) {
        this.serverSocket = serverSocket;
        this.socket = socket;
        this.serverThreadMap = serverThreadMap;
    }

    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            byte[] msgByte = new byte[1024];
            while(isAlive) {
                int length = inputStream.read(msgByte);
                if(length == -1) {
                    continue;
                }
                String msgString = new String(msgByte, 0, length);
                String[] recv = msgString.split("&");
                switch (recv[0]) {
                    case "login":
                        serverThreadMap.put(recv[1], this);
                        clientName = recv[1];
                        String sendMsg = recv[1] + " on";
                        for (ChatServer m : serverThreadMap.values()) {
                            m.outputStream.write(sendMsg.getBytes());
                            m.outputStream.flush();
                        }
                        break;
                    case "bye":
                        isAlive = false;
                        serverThreadMap.remove(clientName);
                        break;
                    case "chat":
                        if(!recv[1].equals("all")) {
                            //私聊
                            ChatServer m = serverThreadMap.get(recv[1]);
                            if(m != null) {
                                String msg = msgString+"&"+clientName;
                                m.outputStream.write(msg.getBytes());
                                m.outputStream.flush();
                            } else {
                                outputStream.write("he is not online".getBytes());
                                outputStream.flush();
                            }
                        } else {
                            //群发
                            for (ChatServer m : serverThreadMap.values()) {
                                String msg = msgString+"&"+clientName;
                                if(m != this) {
                                    m.outputStream.write(msg.getBytes());
                                    m.outputStream.flush();
                                }
                            }
                        }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
    }
}
