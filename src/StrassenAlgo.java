import java.util.concurrent.*;

public class StrassenAlgo {
    public static int[][] concurAlgorithm(int[][] a, int[][] b, int size)
            throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(7);
        try {
            int[][][] a_quadrants = split(a);
            int[][][] b_quadrants = split(b);

            int[][] a11 = a_quadrants[0];
            int[][] a12 = a_quadrants[1];
            int[][] a21 = a_quadrants[2];
            int[][] a22 = a_quadrants[3];

            int[][] b11 = b_quadrants[0];
            int[][] b12 = b_quadrants[1];
            int[][] b21 = b_quadrants[2];
            int[][] b22 = b_quadrants[3];

            Future<int[][]> m1Future = executor.submit(() -> prod(sum(a11, a22), sum(b11, b22)));
            Future<int[][]> m2Future = executor.submit(() -> prod(sum(a21, a22), b11));
            Future<int[][]> m3Future = executor.submit(() -> prod(a11, sub(b12, b22)));
            Future<int[][]> m4Future = executor.submit(() -> prod(a22, sub(b21, b11)));
            Future<int[][]> m5Future = executor.submit(() -> prod(sum(a11, a12), b22));
            Future<int[][]> m6Future = executor.submit(() -> prod(sub(a21, a11), sum(b11, b12)));
            Future<int[][]> m7Future = executor.submit(() -> prod(sub(a12, a22), sum(b21, b22)));

            int[][] p1 = m1Future.get();
            int[][] p2 = m2Future.get();
            int[][] p3 = m3Future.get();
            int[][] p4 = m4Future.get();
            int[][] p5 = m5Future.get();
            int[][] p6 = m6Future.get();
            int[][] p7 = m7Future.get();

            int[][] c11 = sum(sub(sum(p1, p4), p5), p7);
            int[][] c12 = sum(p3, p5);
            int[][] c21 = sum(p2, p4);
            int[][] c22 = sum(sum(sub(p1, p2), p3), p6);

            int[][] c = join(c11, c12, c21, c22);

            return c;
        } finally {
            executor.shutdown();
        }
    }

    public static int[][][] split(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        if (rows % 2 != 0 || cols % 2 != 0) {
            System.out.println("Matrix dimensions must be even to split into quadrants");
            return null;
        }

        int midRow = rows / 2;
        int midCol = cols / 2;

        int[][] topLeft = new int[midRow][midCol];
        int[][] topRight = new int[midRow][cols - midCol];
        int[][] bottomLeft = new int[rows - midRow][midCol];
        int[][] bottomRight = new int[rows - midRow][cols - midCol];

        for (int i = 0; i < midRow; i++) {
            for (int j = 0; j < midCol; j++) {
                topLeft[i][j] = matrix[i][j];
            }
        }
        for (int i = 0; i < midRow; i++) {
            for (int j = midCol; j < cols; j++) {
                topRight[i][j - midCol] = matrix[i][j];
            }
        }
        for (int i = midRow; i < rows; i++) {
            for (int j = 0; j < midCol; j++) {
                bottomLeft[i - midRow][j] = matrix[i][j];
            }
        }
        for (int i = midRow; i < rows; i++) {
            for (int j = midCol; j < cols; j++) {
                bottomRight[i - midRow][j - midCol] = matrix[i][j];
            }
        }

        int[][][] quadrants = { topLeft, topRight, bottomLeft, bottomRight };
        return quadrants;
    }

    public static int[][] join(int[][] m11, int[][] m12, int[][] m21, int[][] m22) {
        int[][] joinedMatrix = new int[m11.length * 2][m11.length * 2];

        for (int i = 0; i < joinedMatrix.length; i++) {
            for (int j = 0; j < joinedMatrix.length; j++) {
                if (i < m11.length && j < m11.length) {
                    joinedMatrix[i][j] = m11[i][j];
                } else if (i < m11.length) {
                    joinedMatrix[i][j] = m12[i][j - m12.length];
                } else if (j < m11.length) {
                    joinedMatrix[i][j] = m21[i - m21.length][j];
                } else {
                    joinedMatrix[i][j] = m22[i - m22.length][j - m22.length];
                }
            }
        }

        return joinedMatrix;
    }

    public static int[][] prod(int[][] m1, int[][] m2) {
        int[][] multipliedMatrix = new int[m1.length][m1.length];

        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1.length; j++) {
                for (int k = 0; k < m1.length; k++) {
                    multipliedMatrix[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }

        return multipliedMatrix;
    }

    public static int[][] sum(int[][] m1, int[][] m2) {
        int[][] addedMatrix = new int[m1.length][m1.length];

        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2.length; j++) {
                addedMatrix[i][j] = m1[i][j] + m2[i][j];
            }
        }

        return addedMatrix;
    }

    public static int[][] sub(int[][] m1, int[][] m2) {
        int[][] substractedMatrix = new int[m1.length][m1.length];

        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1.length; j++) {
                substractedMatrix[i][j] = m1[i][j] - m2[i][j];
            }
        }

        return substractedMatrix;
    }
}