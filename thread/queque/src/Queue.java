import java.util.LinkedList;

public class Queue implements Runnable {
    private LinkedList<String> q;
    Queue(LinkedList<String> x) {
        q = x;
    }
    @Override
    public void run() {
        q.offerLast("last");
        q.removeFirst();
        System.out.println(q);
    }
}
