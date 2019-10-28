import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String path = "/Users/fengxinjie/go/src/";
        FileAnalysis fa = new FileAnalysis();
        try {
            fa.TraversingDictory("fxj", path);
        } catch(IOException e) {
            e.getStackTrace();
        }

    }
}
