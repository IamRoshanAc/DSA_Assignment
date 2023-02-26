import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//
//Implement multi-threaded algorithm to multiply n*n matrix.

public class Q_N_7_a {
    public static void main(String[] args) {
        int[][] matrixA = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] matrixB = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};
        int n = matrixA.length;
        int[][] resultMatrix = new int[n][n];

        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                final int row = i;
                final int col = j;
                executorService.submit(() -> {
                    int sum = 0;
                    for (int k = 0; k < n; k++) {
                        sum += matrixA[row][k] * matrixB[k][col];
                    }
                    resultMatrix[row][col] = sum;
                });
            }
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            // wait for all threads to complete
        }

        // print the result matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(resultMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
