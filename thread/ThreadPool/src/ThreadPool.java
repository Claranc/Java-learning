import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public class ThreadPool implements Runnable {
    private static AtomicInteger threadCount = new AtomicInteger();
    private String target;
    private LinkedList<String> queue;
    private Lock mu1;
    private Lock mu2;

    public ThreadPool(String t, LinkedList<String> d, Lock m1, Lock m2) {
        target = t;
        queue = d;
        mu1 = m1;
        mu2 = m2;
    }

    @Override
    public void run() {
//        int curCnt = threadCount.incrementAndGet();
//        System.out.println(String.format("Start: %d running", curCnt));
        while(true) {
            mu1.lock();
            String path;
            if(queue.isEmpty()){
                mu1.unlock();
                continue;
            }
            path = queue.removeFirst();
            mu1.unlock();
            File file = new File(path);
            File[] lists = file.listFiles();
            for (File list : lists) {
                if (list.isFile()) {
                    //System.out.println("文件：" + list);
                    try {
                        SearchFromFile(target, list.getPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (list.isDirectory()) {
                    //System.out.println("文件夹：" + list);
                    mu2.lock();
                    try {
                        queue.offerLast(list.getPath());
                    } finally {
                        mu2.unlock();
                    }
                }
            }
        }
//        curCnt = threadCount.decrementAndGet();
//        System.out.println(String.format("Exit: %d running", curCnt));
    }

    public void SearchFromFile(String target, String filePath) throws IOException {
        File file = new File(filePath);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        int num = 0;
        while((line = br.readLine()) != null) {
            num++;
            if(line.contains(target)) {
                System.out.println(filePath + " " + num + " " + line);
            }
        }
        fr.close();
        br.close();
    }
}
