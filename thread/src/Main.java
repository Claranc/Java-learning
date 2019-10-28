import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String path = "/Users/fengxinjie/go/src/";
        Thread t = new Thread(new ThreadAdded(path, "fxj"));
        t.start();
    }
}
