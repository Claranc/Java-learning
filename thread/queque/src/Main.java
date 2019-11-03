import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<String> q1 = new LinkedList<>();
        for(int i = 0; i < 100; i++) {
            Thread t = new Thread(new Queue(q1));
            t.start();
        }
    }
}
