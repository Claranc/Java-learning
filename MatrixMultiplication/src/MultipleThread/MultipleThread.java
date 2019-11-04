package MultipleThread;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;

public class MultipleThread implements Runnable {
    public ThreadPoolExecutor executor;
    private Matrix m1;
    private Matrix m2;
    private CurrentLocation cl;
    private Lock mu;
    private int batch;
    private int[][] result;

    MultipleThread(Matrix m1, Matrix m2, Lock mu, CurrentLocation cl, int batch, int[][] result, ThreadPoolExecutor exe) {
        this.m1 = m1;
        this.m2 = m2;
        this.mu = mu;
        this.cl = cl;
        this.batch = batch;
        this.result = result;
        executor = exe;
    }

    @Override
    public void run() {
        String str = cl.GetIndex();
        String[] index = str.split("#");
        int i = Integer.parseInt(index[0]);
        int j = Integer.parseInt(index[1]);
        if (i + j > 2 * batch * batch - 2 * batch) {
            for (int p = 0; p < batch * batch; p++) {
                for (int q = 0; q < batch * batch; q++) {
                    System.out.printf("%d ", result[p][q]);
                }
                System.out.println();
            }
            return;
        } else {
            MultipleThread mt = new MultipleThread(m1, m2, mu, cl, batch, result, executor);
            executor.execute(mt);
        }

        MatrixMultiplication(i, j);
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
}
