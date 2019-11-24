package ChatServerPool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Queue;

public class ChatServerProcessor implements Runnable {
    private Socket socket;
    private Map<String, ChatServerProcessor> serverThreadMap;
    private boolean isAlive = true;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private String clientName;
    private Queue<Socket> clientQueue;

    ChatServerProcessor(Map<String, ChatServerProcessor> serverThreadMap, Socket socket, Queue<Socket> clientQueue) {
        this.serverThreadMap = serverThreadMap;
        this.socket = socket;
        this.clientQueue = clientQueue;
    }

    // 读取当前socket消息并处理
    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            byte[] msgByte = new byte[1024];
            int length = inputStream.read(msgByte);
            if(isAlive && length != -1) {
                String msgString = new String(msgByte, 0, length);
                String[] recv = msgString.split("&");
                System.out.println("recvMsg: " + msgString);
                switch (recv[0]) {
                    case "login":
                        if(recv[1].equals("all")) {
                            outputStream.write("login wrong".getBytes());
                            outputStream.flush();
                            break;
                        }
                        serverThreadMap.put(recv[1], this);
                        clientName = recv[1];
                        String sendMsg = recv[1] + " on";
                        System.out.println(sendMsg);
                        for (ChatServerProcessor m : serverThreadMap.values()) {
                            m.outputStream.write(sendMsg.getBytes());
                            m.outputStream.flush();
                        }
                        break;
                    case "bye":
                        isAlive = false;
                        for (ChatServerProcessor m : serverThreadMap.values()) {
                            if(!m.clientName.equals(recv[1])) {
                                String msg = recv[1] + " off";
                                m.outputStream.write(msg.getBytes());
                                m.outputStream.flush();
                            }
                        }
                        serverThreadMap.remove(recv[1]);
                        break;
                    case "chat":
                        if(recv.length != 4) {
                            outputStream.write("wrong request".getBytes());
                            outputStream.flush();
                            break;
                        }
                        if(!recv[1].equals("all")) {
                            //私聊
                            ChatServerProcessor m = serverThreadMap.get(recv[1]);
                            if(m != null) {
                                m.outputStream.write(msgString.getBytes());
                                m.outputStream.flush();
                            } else {
                                outputStream.write("he is not online".getBytes());
                                outputStream.flush();
                            }
                        } else {
                            //群发
                            for (ChatServerProcessor m : serverThreadMap.values()) {
                                if(!m.clientName.equals(recv[3])) {
                                    m.outputStream.write(msgString.getBytes());
                                    m.outputStream.flush();
                                }
                            }
                        }
                        break;
                    default:
                        outputStream.write("wrong request".getBytes());
                        outputStream.flush();
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            if(isAlive) {
                clientQueue.offer(socket);
            } else {
                try {
                    outputStream.close();
                    inputStream.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
