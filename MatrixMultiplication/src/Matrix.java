import cache.LRUCache;
import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLMessage;

import java.util.Random;

public class Matrix {
    public static final int CACHE_CAP = 32 * 32;

    private int dim;
    private int[] arr;
    private LRUCache cache;

    public Matrix(int dim) {
        this.dim = dim;
        arr = new int[dim * dim];
        cache = new LRUCache(CACHE_CAP);
    }

    public void randomFill(int bound) {
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(bound);
        }
    }

    public int getEle(int i, int j) {
        try {
            Thread.sleep(1);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return arr[i * dim + j];
    }

    public int getCacheEle(int i, int j) {
        int key = i * dim + j;
        int value = cache.get(key);
        if (value < 0) {
            value = getEle(i, j);
            cache.put(key, value);
        }
        return value;
    }

    public void setEle(int i, int j, int e) {
        arr[i * dim + j] = e;
    }
}
