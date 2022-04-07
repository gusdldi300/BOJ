package algorithm.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class S11048 {
    public static void testCode(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = bf.readLine().split(" ");

        int rowSize = Integer.parseInt(inputs[0]);
        int colSize = Integer.parseInt(inputs[1]);

        int[][] candyMaze = new int[rowSize][colSize];
        for (int row = 0; row < rowSize; ++row) {
            inputs = bf.readLine().split(" ");
            for (int col = 0; col < colSize; ++col) {
                candyMaze[row][col] = Integer.parseInt(inputs[col]);
            }
        }

        printMostCandyCount(candyMaze);
    }

    private static void printMostCandyCount(final int[][] candyMaze) {
        int rowSize = candyMaze.length;
        int colSize = candyMaze[0].length;
        int[][] cache = new int[rowSize][colSize];
        for (int row = 0; row < rowSize; ++row) {
            for (int col = 0; col < colSize; ++col) {
                cache[row][col] = -1;
            }
        }

        Queue<Integer> rowQueue = new LinkedList<>();
        Queue<Integer> colQueue = new LinkedList<>();

        rowQueue.add(0);
        colQueue.add(0);
        cache[0][0] = candyMaze[0][0];

        while (rowQueue.isEmpty() == false) {
            int currentRow = rowQueue.poll();
            int currentCol = colQueue.poll();

            if (currentRow == rowSize - 1 && currentCol == colSize - 1) {
                break;
            }

            addCandy(rowQueue, colQueue, currentRow, currentCol + 1, cache[currentRow][currentCol], candyMaze, cache);
            addCandy(rowQueue, colQueue, currentRow + 1, currentCol, cache[currentRow][currentCol], candyMaze, cache);
        }

        System.out.println(cache[rowSize - 1][colSize - 1]);
    }

    private static void addCandy(final Queue<Integer> rowQueue, final Queue<Integer> colQueue, int row, int col, int candyCount, final int[][] candyMaze, final int[][] cache) {
        if (row < 0 || row >= cache.length) {
            return;
        }

        if (col < 0 || col >= cache[0].length) {
            return;
        }

        if (cache[row][col] != -1) {
            if (cache[row][col] < (candyCount + candyMaze[row][col])) {
                cache[row][col] = candyCount + candyMaze[row][col];
            }

            return;
        }

        cache[row][col] = candyCount + candyMaze[row][col];
        rowQueue.add(row);
        colQueue.add(col);
    }
}
