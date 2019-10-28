import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class EncodedFileWriter extends Writer {
    private BufferedWriter bw;

    EncodedFileWriter(BufferedWriter x) {
        bw = x;
    }

    @Override
    public void write(char[] chars, int i, int i1) throws IOException {
        for(int k = 0; k < chars.length; k++) {
            chars[k] += 5;
        }
        bw.write(chars, i, i1);
    }

    @Override
    public void flush() throws IOException {
        bw.flush();
    }

    @Override
    public void close() throws IOException {
        bw.close();
    }
}
