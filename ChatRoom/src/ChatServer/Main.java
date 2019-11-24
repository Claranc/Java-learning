package ChatServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Map<String, ChatServer> serverThreadMap = new HashMap<String, ChatServer>();
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("tcp server listening port 8888 ...");
        Socket socket = null;
        while(true) {
            socket = serverSocket.accept();
            Thread t = new Thread(new ChatServer(serverSocket, socket, serverThreadMap));
            t.start();
        }
    }
}
