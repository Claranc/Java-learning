import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("127.0.0.1", 9483);
        String sendMsg = "lark";
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            EncodedFileWriter ew = new EncodedFileWriter(bw);
            ew.write(sendMsg.toCharArray(), 0, sendMsg.length());
            ew.flush();
            System.out.println("sendMsg = " + sendMsg);

            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            DecodedFileReader dr = new DecodedFileReader(br);
            char[] result = new char[10];
            dr.read(result, 0, 10);
            System.out.println("recvMsg = " + String.valueOf(result));

            ew.close();
            dr.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
