package Slice;

import Matrix.Matrix;

public class Main {
    private static final int SIZE = 1024;

    public static void main(String[] args) {
        //生成第一个矩阵
        Matrix m1 = new Matrix(SIZE);
        m1.randomFill(10);
        //生成第二个矩阵
        Matrix m2 = new Matrix(SIZE);
        m2.randomFill(10);

        //计算相乘并输出结果
        Multiplication m = new Multiplication(SIZE);
        int[][] result = m.MatrixMultiplication(m1, m2);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%d ", result[i][j]);
            }
            System.out.println();
        }
    }


}