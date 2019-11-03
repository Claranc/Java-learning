import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String path = "C:\\Go\\src";
        LinkedList<String> queue = new LinkedList<>();
        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();
        queue.offerLast(path);
        for(int i = 0; i < 4; i++) {
            Thread t = new Thread(new ThreadPool("math", queue, lock1, lock2));
            t.start();
        }
    }
}
