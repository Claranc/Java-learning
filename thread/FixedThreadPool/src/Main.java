import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String path = "/Users/fengxinjie/go/src/";
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

        FileSearching t = new FileSearching("fxj", path, executor);
        executor.execute(t);
        System.out.println("Maximum threads inside pool " + executor.getMaximumPoolSize());
//        Thread.sleep(10000);
//        executor.shutdown();
    }
}
