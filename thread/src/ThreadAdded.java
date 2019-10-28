import java.io.IOException;

public class ThreadAdded implements Runnable {
    private Thread t;
    private String threadName;
    private FileAnalysis fa;
    private String path;
    private String target;

    public ThreadAdded(String name, FileAnalysis f, String p, String t) {
        threadName = name;
        fa = f;
        path = p;
        target = t;
    }

    @Override
    public void run() {
        //System.out.println(threadName + "RUN...");
        try {
            fa.TraversingDictory(target, path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        //System.out.println(threadName + "Start...");
        if (t == null) {
            t = new Thread (this, threadName);
            t.start();
        }
    }
}
