import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class DecodedFileReader extends Reader {
    private BufferedReader br;

    DecodedFileReader(BufferedReader x) {
        br = x;
    }

    @Override
    public int read(char[] chars, int i, int i1) throws IOException {
        br.read(chars, i, i1);
        for(int k = 0; k < chars.length; k++) {
            chars[k] -= 5;
        }
        return 0;
    }

    @Override
    public void close() throws IOException {
        br.close();
    }
}
