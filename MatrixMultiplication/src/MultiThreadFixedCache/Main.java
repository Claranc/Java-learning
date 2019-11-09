package MultiThreadFixedCache;

import Matrix.Matrix;
import Matrix2.Matrix2;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static final int SIZE = 16;

    public static void main(String[] args) throws InterruptedException {
        //生成第一个矩阵
        Matrix m1 = new Matrix(SIZE);
        m1.randomFill(10);
        //生成第二个矩阵
        Matrix m2 = new Matrix(SIZE);
        m2.randomFill(10);
        int batch = (int) Math.sqrt(SIZE);
        Lock mu = new ReentrantLock();
        Queue<Pair<Integer, Integer>> Index = new LinkedList<Pair<Integer, Integer>>();
        int[][] result1 = new int[SIZE][SIZE];
        MultiThreadFixedCache m = new MultiThreadFixedCache(m1, m2, mu, Index, batch,result1);
        m.StartComputing();
        System.out.println("result1 = ");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%d ", result1[i][j]);
            }
            System.out.println();
        }


        Matrix2 m12 = new Matrix2(SIZE);
        m12.randomFill(10);
        Matrix2 m22 = new Matrix2(SIZE);
        m22.randomFill(10);
        int batch2 = (int) Math.sqrt(SIZE);
        Lock mu2 = new ReentrantLock();
        Queue<Pair<Integer, Integer>> Index2 = new LinkedList<Pair<Integer, Integer>>();
        int[][] result2 = new int[SIZE][SIZE];
        MultiThreadFixedCache2 mm = new MultiThreadFixedCache2(m12, m22, mu2, Index2, batch2,result2);
        mm.StartComputing();


        System.out.println("result2 = ");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%d ", result2[i][j]);
            }
            System.out.println();
        }

        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                if(result1[i][j] != result2[i][j]) {
                    System.out.println("false " + i + " " + j);
                    System.out.println(result1[i][j] + " " + result2[i][j]);
                    return;
                }
            }
        }
        System.out.println("true");
    }
}
