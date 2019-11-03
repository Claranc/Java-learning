public class Main {
    private static final int SIZE = 256;

    public static void main(String[] args) {
        //生成第一个矩阵
        Matrix m1 = new Matrix(SIZE);
        m1.randomFill(10);
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                System.out.printf("%d ", m1.getEle(i, j));
//            }
//            System.out.println();
//        }
        //生成第二个矩阵
        Matrix m2 = new Matrix(SIZE);
        m2.randomFill(10);
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                System.out.printf("%d ", m2.getEle(i, j));
//            }
//            System.out.println();
//        }

        //计算相乘并输出结果
        int[][] result = MatrixMultiplication(m1, m2);

//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                System.out.printf("%d ", result[i][j]);
//            }
//            System.out.println();
//        }
    }

    private static int[][] MatrixMultiplication(Matrix m1, Matrix m2) {
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
                                result[i1][j1] += m1.getEle(i1, k1) * m2.getEle(k1, j1);
//                                result[i1][j1] += m1.getCacheEle(i1, k1) * m2.getCacheEle(k1, j1);
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