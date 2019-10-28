import org.junit.Test;

import java.io.*;

public class FileAnalysis {
    private static int curNum = 1;

    public void TraversingDictory(String target, String dictoryPath) throws IOException {
        File file = new File(dictoryPath);
        File[] lists = file.listFiles();
        assert lists != null;
        for (File list : lists) {
            if (list.isFile()) {
                //System.out.println("文件：" + lists[i]);
                SearchFromFile(target, list.getPath());
            } else if (list.isDirectory()) {
                //System.out.println("文件夹：" + lists[i]);
                curNum += 1;
                ThreadAdded newThread = new ThreadAdded(String.valueOf(curNum), this, list.getPath(), target);
                newThread.start();
                TraversingDictory(target, list.getPath());
            }
        }
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
