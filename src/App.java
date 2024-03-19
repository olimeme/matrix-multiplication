import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        int[][] matrix1 = Matrix.generateRandomMatrix(3, 3);
        int[][] matrix2 = Matrix.generateRandomMatrix(3, 3);

        int[][] result = Matrix.multiplyMatrices(matrix1, matrix2);
        Matrix.printMatrix(matrix1);
        System.out.println();
        Matrix.printMatrix(matrix2);
        System.out.println();
        Matrix.printMatrix(result);
    }

}
