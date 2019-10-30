import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadAdded implements Runnable {
    private static AtomicInteger threadCount = new AtomicInteger();
    private String path;
    private String target;

    public ThreadAdded(String p, String t) {
        path = p;
        target = t;
    }

    @Override
    public void run() {
        int curCnt = threadCount.incrementAndGet();
        System.out.println(String.format("Start: %d running", curCnt));

        File file = new File(path);
        File[] lists = file.listFiles();
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
                Thread newThread = new Thread(new ThreadAdded(list.getPath(), target));
                newThread.start();
            }
        }

        curCnt = threadCount.decrementAndGet();
        System.out.println(String.format("Exit: %d running", curCnt));
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
