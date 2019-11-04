package naive;

public class Multiplication {
    private int SIZE;
    Multiplication(int SIZE) {
        this.SIZE = SIZE;
    }

    public int[][] MatrixMultiplication(Matrix m1, Matrix m2) {
        int[][] result = new int[SIZE][SIZE];
        long t = System.currentTimeMillis();
        for (int i1 = 0; i1 < SIZE; i1++) {
            for (int j1 = 0; j1 < SIZE; j1++) {
                for (int k1 = 0; k1 < SIZE; k1++) {
                                result[i1][j1] += m1.getEle(i1, k1) * m2.getEle(k1, j1);
//                    result[i1][j1] += m1.getCacheEle(i1, k1) * m2.getCacheEle(k1, j1);
                }
            }
        }
        t = System.currentTimeMillis() - t;
        System.out.println(t);
        return result;
    }
}
