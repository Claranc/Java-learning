import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class FileSearching implements Runnable {
    public ThreadPoolExecutor executor;
    private static AtomicInteger threadCount = new AtomicInteger();
    public String curPath;
    private String target;

    public FileSearching(String t, String c, ThreadPoolExecutor e) {
        target = t;
        curPath = c;
        executor = e;
    }

    public String GetCurPath() {
        return curPath;
    }

    public void run() {
        //System.out.println("Thread running" );
//        int curCnt = threadCount.incrementAndGet();
//        System.out.println(String.format("Start: %d running", curCnt));
            //Runnable path = null;
        File file = new File(curPath);
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
                    FileSearching f = new FileSearching(target, list.getPath(), executor);
                    executor.execute(f);
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
