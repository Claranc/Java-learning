package ChatServerPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    private static final int threadSum = 10;
    private static Map<String, ChatServerProcessor> serverThreadMap = new ConcurrentHashMap<>();
    private static Queue<Socket> clientQueue = new ConcurrentLinkedQueue<>();
    private static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadSum);
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("tcp server listening port 8888 ...");
        Socket socket = null;
        while(true) {
            socket = serverSocket.accept();
            boolean isSuccess = clientQueue.offer(socket);
            if(!isSuccess) {
                System.out.println("add client failed");
            }
            threadPoolExecutor.execute(new Thread(new ChatServerController(clientQueue, threadPoolExecutor, serverThreadMap)));
        }
    }
}
