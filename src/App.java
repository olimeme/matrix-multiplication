public class App {
    static final int MAX_THREADS_NUM = Runtime.getRuntime().availableProcessors();
    static int[][] matrix_1 = new int[MAX_THREADS_NUM][MAX_THREADS_NUM];
    static int[][] matrix_2 = new int[MAX_THREADS_NUM][MAX_THREADS_NUM];
    static int[][] result = new int[MAX_THREADS_NUM][MAX_THREADS_NUM];
    static int step_i = 0;

    public static void main(String[] args) {
        matrix_1 = MatrixWorker.generateRandomMatrix(MAX_THREADS_NUM, MAX_THREADS_NUM);
        matrix_2 = MatrixWorker.generateRandomMatrix(MAX_THREADS_NUM, MAX_THREADS_NUM);
        System.out.println("-----------------Matrix 1-----------------");
        MatrixWorker.printMatrix(matrix_1);
        System.out.println("-----------------Matrix 2-----------------");
        MatrixWorker.printMatrix(matrix_2);

        Thread[] threads = new Thread[MAX_THREADS_NUM];

        for (int i = 0; i < MAX_THREADS_NUM; i++) {
            threads[i] = new Thread(new MatrixWorker(step_i++, MAX_THREADS_NUM, matrix_1, matrix_2, result));
            threads[i].start();
        }
        for (int i = 0; i < MAX_THREADS_NUM; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("-----------------Multiplication of matricies-----------------");
        for (int i = 0; i < MAX_THREADS_NUM; i++) {
            for (int j = 0; j < MAX_THREADS_NUM; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }
}
