package MultiThreadFixedCache;

import Matrix.Matrix;
import javafx.util.Pair;

import java.util.Queue;
import java.util.concurrent.locks.Lock;

public class MultiThreadFixedCache implements Runnable {
    private Matrix m1;
    private Matrix m2;
    private Queue<Pair<Integer, Integer>> curIndex;
    private Lock getKLock;
    private int batch;
    private int[][] result;
    private int i;
    private int j;
    private int k;

    MultiThreadFixedCache(Matrix m1, Matrix m2, Lock mu1, Queue<Pair<Integer, Integer>> curIndex, int batch, int[][] result) {
        this.m1 = m1;
        this.m2 = m2;
        this.getKLock = mu1;
        this.curIndex = curIndex;
        this.batch = batch;
        this.result = result;
    }

    private MultiThreadFixedCache(Matrix m1, Matrix m2, Lock mu1, Queue<Pair<Integer, Integer>> curIndex, int batch, int[][] result, int i, int j, int k) {
        this.m1 = m1;
        this.m2 = m2;
        this.getKLock = mu1;
        this.curIndex = curIndex;
        this.batch = batch;
        this.result = result;
        this.i = i;
        this.j = j;
        this.k = k;
    }

    @Override
    public void run() {
        while (true) {
            if(curIndex.size() == 0) {
                break;
            }
            getKLock.lock();
            Pair<Integer, Integer> p = curIndex.poll();
            if (p == null) {
                getKLock.unlock();
                break;
            }
            getKLock.unlock();
//                                result[i1][j1] += m1.getEle(i1, k1) * m2.getEle(k1, j1);
            for(int x = 0; x < batch; x++) {
                result[i+p.getKey()][j+p.getValue()] += m1.getCacheEle(i+p.getKey(), k+x) * m2.getCacheEle(k+x, j+p.getValue());
            }
        }
    }

    public void StartComputing() throws InterruptedException {
        for (int i = 0; i < batch * batch; i += batch) {
            for (int j = 0; j < batch * batch; j += batch) {
                for (int k = 0; k < batch * batch; k += batch) {
                    for(int x = 0; x < batch; x++) {
                        for(int y =0; y < batch; y++) {
                            curIndex.offer(new Pair<Integer, Integer>(x, y));
                        }
                    }
                    int num = 16;
                    Thread[] threadArray = new Thread[num];
                    for (int x = 0; x < num; x++) {
                        threadArray[x] = new Thread(new MultiThreadFixedCache(m1, m2, getKLock, curIndex, batch, result, i, j, k));
                        threadArray[x].start();
                    }
                    for (int x = 0; x < num; x++) {
                        threadArray[x].join();
                    }
                }
            }
        }
    }
}
