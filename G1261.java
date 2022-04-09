package algorithm.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class G1261 {
    public static void testCode(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = bf.readLine().split(" ");

        int rowSize = Integer.parseInt(inputs[1]);
        int colSize = Integer.parseInt(inputs[0]);

        boolean[][] maze = new boolean[rowSize][colSize];
        for (int row = 0; row < rowSize; ++row) {
            String input = bf.readLine();
            for (int col = 0; col < colSize; ++col) {
                if (input.charAt(col) == '1') {
                    maze[row][col] = true;

                    continue;
                }

                maze[row][col] = false;
            }
        }

        printLeastBreakCount(maze);
    }

    private static void printLeastBreakCount(boolean[][] maze) {
        int rowSize = maze.length;
        int colSize = maze[0].length;

        int[][] cache = new int[rowSize][colSize];
        for (int row = 0; row < rowSize; ++row) {
            for (int col = 0; col < colSize; ++col) {
                cache[row][col] = -1;
            }
        }
        cache[0][0] = 0;

        Queue<Integer> rowQueue = new LinkedList<>();
        Queue<Integer> colQueue = new LinkedList<>();
        rowQueue.add(0);
        colQueue.add(0);

        while (rowQueue.isEmpty() == false) {
            int row = rowQueue.poll();
            int col = colQueue.poll();

            int count = cache[row][col];
            // North, South, East, West
            addPosition(maze, rowQueue, colQueue, row - 1, col, count, cache);
            addPosition(maze, rowQueue, colQueue, row + 1, col, count, cache);
            addPosition(maze, rowQueue, colQueue, row, col - 1, count, cache);
            addPosition(maze, rowQueue, colQueue, row, col + 1, count, cache);
        }

        System.out.println(cache[rowSize - 1][colSize - 1]);
    }

    private static void addPosition(final boolean[][] maze, final Queue<Integer> rowQueue, final Queue<Integer> colQueue, int row, int col, int lastCount, final int[][] cache) {
        if (row < 0 || row >= maze.length) {
            return;
        }

        if (col < 0 || col >= maze[0].length) {
            return;
        }

        int wall = (maze[row][col] == true) ? 1 : 0;
        if (cache[row][col] != -1 && (lastCount + wall) >= cache[row][col]) {
            return;
        }

        cache[row][col] = lastCount + wall;
        rowQueue.add(row);
        colQueue.add(col);
    }

}
