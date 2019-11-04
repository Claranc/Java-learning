package MultipleThread;

import javafx.util.Pair;

import java.util.concurrent.locks.Lock;

public class CurrentLocation {
    private String location = "0#0";
    private int batch;
    Lock mu;

    CurrentLocation(int batch, Lock mu) {
        this.batch = batch;
        this.mu = mu;
    }

    public String GetIndex() {
        mu.lock();
        String result = location;
        String[] index = location.split("#");

        int first = Integer.parseInt(index[0]);
        int second = Integer.parseInt(index[1]);

        if(first < batch*batch - batch) {
            second += batch;
            if(second > batch*batch - batch) {
                first += batch;
                second = 0;
            }
        } else {
            second += batch;
        }

        location = String.format("%d#%d", first, second);
        mu.unlock();
       // System.out.println("lock = " + result);
        return result;
    }
}
