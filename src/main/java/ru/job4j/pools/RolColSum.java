package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums(getRowSum(matrix, i), getColSum(matrix, i));
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < sums.length; i++) {
            int finalI = i;
            int row = CompletableFuture.supplyAsync(() -> getRowSum(matrix, finalI)).get();
            int col = CompletableFuture.supplyAsync(() -> getColSum(matrix, finalI)).get();
            sums[i] = new Sums(row, col);
        }
        return sums;
    }

    public static int getRowSum(int[][] matrix, int row) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[row][i];
        }
        return sum;
    }

    public static int getColSum(int[][] matrix, int col) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][col];
        }
        return sum;
    }
}
