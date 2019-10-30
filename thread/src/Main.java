import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String path = "/Users/fengxinjie/go/src/";
        Thread t = new Thread(new ThreadAdded(path, "fxj"));
        t.start();
    }
}
