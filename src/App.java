import java.util.Random;

public class App {
    static final int MAX_THREADS_NUM = Runtime.getRuntime().availableProcessors();
    static int N1 = 16;
    static int N2 = 128;
    static int N3 = 1024;
    static int N4 = 4096;

    public static void main(String[] args) {
        testMatrixSize(N1);
        testMatrixSize(N2);
        testMatrixSize(N3);
        testMatrixSize(N4);
    }

    public static void testMatrixSize(int size) {
        System.out.println("----------------------------------");
        int[][] matrix_1 = new int[size][size];
        int[][] matrix_2 = new int[size][size];
        matrix_1 = generateRandomMatrix(size);
        matrix_2 = generateRandomMatrix(size);
        testStrassenAlgo(matrix_1, matrix_2, size);
        testSeqStrassenAlgo(matrix_1, matrix_2, size);
        testNaiveAlgo(matrix_1, matrix_2, size);
    }

    public static void testStrassenAlgo(int[][] matrix_1, int[][] matrix_2, int size) {

        System.out.println("Matrix size: " + size + "x" + size);
        // System.out.println("-----------------Matrix 1-----------------");
        // printMatrix(matrix_1);
        // System.out.println("-----------------Matrix 2-----------------");
        // printMatrix(matrix_2);

        try {
            long start = System.currentTimeMillis();
            int[][] result = StrassenAlgo.concurAlgorithm(matrix_1, matrix_2, size);
            long end = System.currentTimeMillis();
            System.out.println("Matrix multiplication using concurrent Strassen Algorithm:");
            System.out.println("Time taken: " + (end - start) + "ms");
            // printMatrix(result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public static void testNaiveAlgo(int[][] matrix_1, int[][] matrix_2, int size) {
        int[][] result = new int[size][size];
        // System.out.println("Matrix size: " + size + "x" + size);
        // System.out.println("-----------------Matrix 1-----------------");
        // printMatrix(matrix_1);
        // System.out.println("-----------------Matrix 2-----------------");
        // printMatrix(matrix_2);

        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    result[i][j] += matrix_1[i][k] * matrix_2[k][j];
                }
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("Matrix multiplication using naive approach:");
        System.out.println("Time taken: " + (end - start) + "ms");
        // printMatrix(result);
    }

    public static void testSeqStrassenAlgo(int[][] matrix_1, int[][] matrix_2, int size) {
        int[][] result = new int[size][size];
        // System.out.println("Matrix size: " + size + "x" + size);
        // System.out.println("-----------------Matrix 1-----------------");
        // printMatrix(matrix_1);
        // System.out.println("-----------------Matrix 2-----------------");
        // printMatrix(matrix_2);

        long start = System.currentTimeMillis();
        result = StrassenAlgo.seqAlgorithm(matrix_1, matrix_2, size);
        long end = System.currentTimeMillis();

        System.out.println("Matrix multiplication using synchronous Strassen Algorithm:");
        System.out.println("Time taken: " + (end - start) + "ms");
        // printMatrix(result);
    }

    static int[][] generateRandomMatrix(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Random rand = new Random();
                matrix[i][j] = rand.nextInt(10);
            }
        }
        return matrix;
    }

    static void printMatrix(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
}
