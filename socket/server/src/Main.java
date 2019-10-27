import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9483);
        System.out.println("Start to listen...");
        Socket s = server.accept();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DecodedFileReader dr = new DecodedFileReader(br);
            char[] result = new char[4];
            dr.read(result, 0, 4);
            System.out.println("recv = " + String.valueOf(result));

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            EncodedFileWriter ew = new EncodedFileWriter(bw);
            ew.write(result, 0, 4);
            ew.flush();
            System.out.println("sendMsg = " + Arrays.toString(result));

            dr.close();
            ew.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
