package MultiThread;

import Matrix.Matrix;
import javafx.util.Pair;

import java.util.Queue;
import java.util.concurrent.locks.Lock;

public class MultipleThreadDistribution implements Runnable {
    private Matrix m1;
    private Matrix m2;
    private Queue<Pair<Integer, Integer>> index;
    private Lock mu;
    private int batch;
    private int[][] result;

    MultipleThreadDistribution(Matrix m1, Matrix m2, Lock mu, Queue<Pair<Integer, Integer>> in, int batch, int[][] result) {
        this.m1 = m1;
        this.m2 = m2;
        this.mu = mu;
        this.index = in;
        this.batch = batch;
        this.result = result;
    }

    @Override
    public void run() {
        while (true) {
            mu.lock();
            Pair<Integer, Integer> cur = index.poll();
            if(cur == null) {
                mu.unlock();
                break;
            }
            int i = cur.getKey();
            int j = cur.getValue();
            mu.unlock();
            System.out.println("i,j = " + i +" " + j);
            MatrixMultiplication(i, j);
        }
    }

    public void MatrixMultiplication(int i, int j) {
        for (int k = 0; k < batch * batch; k += batch) {

            //计算单个矩阵
            long t = System.currentTimeMillis();
            for (int i1 = i; i1 < i + batch; i1++) {
                for (int j1 = j; j1 < j + batch; j1++) {
                    for (int k1 = k; k1 < k + batch; k1++) {
//                                result[i1][j1] += m1.getEle(i1, k1) * m2.getEle(k1, j1);
                        result[i1][j1] += m1.getEle(i1, k1) * m2.getEle(k1, j1);
                    }
                }
            }
            t = System.currentTimeMillis() - t;
            //System.out.println(String.format("(%d,%d,%d) %d", i, j, k, t));
        }
    }

    public void Start() {
        run();
    }
}
