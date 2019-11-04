package MultipleThread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static final int SIZE = 4;

    public static void main(String[] args) {
        //生成第一个矩阵
        Matrix m1 = new Matrix(SIZE);
        m1.randomFill(10);
        //生成第二个矩阵
        Matrix m2 = new Matrix(SIZE);
        m2.randomFill(10);

        int batch = (int) Math.sqrt(SIZE);
        Lock mu = new ReentrantLock();
        CurrentLocation cl = new CurrentLocation(batch, mu);
        int result[][] = new int[SIZE][SIZE];
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        MultipleThread mt = new MultipleThread(m1, m2, mu, cl, batch, result, executor);
        executor.execute(mt);
    }
}
