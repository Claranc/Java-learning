package ChatServerPool;

import java.net.Socket;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ThreadPoolExecutor;

public class ChatServerController implements Runnable {
    private Queue<Socket> clientQueue;
    private ThreadPoolExecutor threadPoolExecutor;
    private Map<String, ChatServerProcessor> serverThreadMap = null;

    ChatServerController(Queue<Socket> clientQueue, ThreadPoolExecutor threadPoolExecutor, Map<String, ChatServerProcessor> serverThreadMap) {
        this.clientQueue = clientQueue;
        this.threadPoolExecutor = threadPoolExecutor;
        this.serverThreadMap = serverThreadMap;
    }
    // 轮训客户端队列，依次处理消息
    @Override
    public void run() {
        while (true) {
            Socket socket = clientQueue.poll();
            if(socket != null) {
                threadPoolExecutor.execute(new ChatServerProcessor(serverThreadMap, socket, clientQueue));
            }
        }
    }
}
