package MultiThread;

import Matrix.Matrix;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static final int SIZE = 1024;

    public static void main(String[] args) throws InterruptedException {
        //生成第一个矩阵
        Matrix m1 = new Matrix(SIZE);
        m1.randomFill(10);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%d ", m1.getEle(i, j));
            }
            System.out.println();
        }
        System.out.println();
        //生成第二个矩阵
        Matrix m2 = new Matrix(SIZE);
        m2.randomFill(10);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%d ", m2.getEle(i, j));
            }
            System.out.println();
        }
        long t = System.currentTimeMillis();
        int batch = (int) Math.sqrt(SIZE);
        Lock mu = new ReentrantLock();
        Queue<Pair<Integer, Integer>> Index = new LinkedList<Pair<Integer, Integer>>();
        for(int i = 0; i < SIZE; i += batch) {
            for(int j = 0; j < SIZE; j += batch) {
                Index.offer(new Pair<Integer, Integer>(i,j));

            }
        }
        int[][] result = new int[SIZE][SIZE];
        Thread t1 = new Thread(new MultipleThreadDistribution(m1, m2, mu, Index, batch, result));
        t1.start();
        Thread t2 = new Thread(new MultipleThreadDistribution(m1, m2, mu, Index, batch, result));
        t2.start();
        t1.join();
        t2.join();

//        t = System.currentTimeMillis() - t;
//        System.out.println("time = " + t);
//        System.out.println("result = ");
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                System.out.printf("%d ", result[i][j]);
//            }
//            System.out.println();
//        }
        System.out.println(result[456][565]);
    }
}
