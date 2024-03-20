public class App {
    static final int MAX_THREADS_NUM = Runtime.getRuntime().availableProcessors();
    static int N1 = 20;
    static int N2 = 100;
    static int N3 = 1000;
    static int N4 = 5000;

    public static void main(String[] args) {
        testThreadMatrix(N1);
        testSyncMatrix(N1);
        System.out.println("----------------------------------");
        testThreadMatrix(N2);
        testSyncMatrix(N2);
        System.out.println("----------------------------------");
        testThreadMatrix(N3);
        testSyncMatrix(N3);
        System.out.println("----------------------------------");
        testThreadMatrix(N4);
        testSyncMatrix(N4);
    }

    public static void testThreadMatrix(int size) {
        int[][] matrix_1 = new int[size][size];
        int[][] matrix_2 = new int[size][size];
        int[][] result = new int[size][size];
        int k = 0;
        matrix_1 = MatrixWorker.generateRandomMatrix(size, size);
        matrix_2 = MatrixWorker.generateRandomMatrix(size, size);
        System.out.println("Matrix size: " + size + "x" + size);
        // System.out.println("-----------------Matrix 1-----------------");
        // MatrixWorker.printMatrix(matrix_1);
        // System.out.println("-----------------Matrix 2-----------------");
        // MatrixWorker.printMatrix(matrix_2);

        Thread[] threads = new Thread[MAX_THREADS_NUM];

        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX_THREADS_NUM; i++) {
            threads[i] = new Thread(new MatrixWorker(k++, matrix_1, matrix_2, result));
            threads[i].start();
        }
        for (int i = 0; i < MAX_THREADS_NUM; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("Matrix multiplication with threads:");
        System.out.println("Time taken: " + (end - start) + "ms");
    }

    public static void testSyncMatrix(int size) {
        int[][] matrix_1 = new int[size][size];
        int[][] matrix_2 = new int[size][size];
        int[][] result = new int[size][size];
        matrix_1 = MatrixWorker.generateRandomMatrix(size, size);
        matrix_2 = MatrixWorker.generateRandomMatrix(size, size);
        System.out.println("Matrix size: " + size + "x" + size);
        // System.out.println("-----------------Matrix 1-----------------");
        // MatrixWorker.printMatrix(matrix_1);
        // System.out.println("-----------------Matrix 2-----------------");
        // MatrixWorker.printMatrix(matrix_2);

        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    result[i][j] += matrix_1[i][k] * matrix_2[k][j];
                }
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("Matrix multiplication without threads:");
        System.out.println("Time taken: " + (end - start) + "ms");
    }
}
