import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool implements Runnable {
    private static AtomicInteger threadCount = new AtomicInteger();
    private String target;
    private LinkedBlockingQueue<String> directoryPath;

    public ThreadPool(String t, LinkedBlockingQueue<String> d) {
        target = t;
        directoryPath = d;
    }

    @Override
    public void run() {
//        int curCnt = threadCount.incrementAndGet();
//        System.out.println(String.format("Start: %d running", curCnt));
        while(true) {
            String path = null;
            try {
                path = directoryPath.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            File file = new File(path);
            File[] lists = file.listFiles();
            if (lists != null) {
                for (File list : lists) {
                    if (list.isFile()) {
                        //System.out.println("文件：" + lists[i]);
                        try {
                            SearchFromFile(target, list.getPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (list.isDirectory()) {
                        //System.out.println("文件夹：" + lists[i]);
                        try {
                            directoryPath.put(list.getPath());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
