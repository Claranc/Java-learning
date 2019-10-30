import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String path = "/Users/fengxinjie/go/src/";
        LinkedBlockingQueue<String> directoryPath = new LinkedBlockingQueue<>();
        directoryPath.put(path);
        for(int i = 0; i < 4; i++) {
            Thread t = new Thread(new ThreadPool("fxj", directoryPath));
            t.start();
        }
    }
}
