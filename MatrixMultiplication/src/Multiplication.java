public class Multiplication {
    private int SIZE;
    Multiplication(int SIZE) {
        this.SIZE = SIZE;
    }

    public int[][] MatrixMultiplication(Matrix m1, Matrix m2) {
        int[][] result = new int[SIZE][SIZE];
        int sqrt = (int) Math.sqrt(SIZE);
        for (int i = 0; i < SIZE; i += sqrt) {
            for (int j = 0; j < SIZE; j += sqrt) {
                for (int k = 0; k < SIZE; k += sqrt) {

                    //计算单个矩阵
                    long t = System.currentTimeMillis();
                    for (int i1 = i; i1 < i + sqrt; i1++) {
                        for (int j1 = j; j1 < j + sqrt; j1++) {
                            for (int k1 = k; k1 < k + sqrt; k1++) {
//                                result[i1][j1] += m1.getEle(i1, k1) * m2.getEle(k1, j1);
                                result[i1][j1] += m1.getCacheEle(i1, k1) * m2.getCacheEle(k1, j1);
                            }
                        }
                    }
                    t = System.currentTimeMillis() - t;
                    System.out.println(String.format("(%d,%d,%d) %d", i, j, k, t));
                }
            }
        }
        return result;
    }
}
