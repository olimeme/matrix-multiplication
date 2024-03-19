public class MatrixWorker implements Runnable {
    private int i;
    private int threads_num;
    private int[][] matrix1;
    private int[][] matrix2;
    private int[][] result;

    public MatrixWorker(int i, int thread_num, int[][] matrix1, int[][] matrix2, int[][] result) {
        this.i = i;
        this.threads_num = thread_num;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.result = result;
    }

    static int[][] generateRandomMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = (int) (Math.random() * 10);
            }
        }
        return matrix;
    }

    @Override
    public void run() {
        int cols1 = matrix1[0].length;
        int rows2 = matrix2.length;

        if (cols1 != rows2) {
            System.out.println("Cannot multiply the matrices. Incorrect dimensions");
            return;
        }
        for (int j = 0; j < threads_num; j++) {
            for (int k = 0; k < threads_num; k++) {
                result[i][j] += matrix1[i][k] * matrix2[k][j];
            }
        }
    }

    static void printMatrix(int[][] matrix) {
        System.out.println("-------------------------------------");
        for (int[] row : matrix) {
            for (int i : row) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}
