import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        ThreadAdded t1 = new ThreadAdded("thread1");
//        t1.start();
//
//        ThreadAdded t2 = new ThreadAdded("thread2");
//        t2.start();
        String path = "/Users/fengxinjie/go/src/";
        FileAnalysis fa = new FileAnalysis();
        try {
            fa.TraversingDictory("fxj", path);
        } catch(IOException e) {
            e.getStackTrace();
        }

    }
}
