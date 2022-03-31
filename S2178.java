package search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class S2178 {
    public static void testCode(String[] args) throws IOException {
        int count = getLeastPathCount();
        System.out.println(count);
    }

    private static int getLeastPathCount() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = bf.readLine().split(" ");

        int rowSize = Integer.parseInt(inputs[0]);
        int colSize = Integer.parseInt(inputs[1]);

        int[][] maze = new int[rowSize][colSize];
        for (int row = 0; row < rowSize; ++row) {
            String input = bf.readLine();
            for (int col = 0; col < colSize; ++col) {
                if (input.charAt(col) == '1') {
                    maze[row][col] = 1;
                } else {
                    maze[row][col] = 0;
                }
            }
        }

        boolean[][] visited = new boolean[rowSize][colSize];
        Queue<Integer> rowQueue = new LinkedList<>();
        Queue<Integer> colQueue = new LinkedList<>();

        rowQueue.add(0);
        colQueue.add(0);
        visited[0][0] = true;

        int depthCount = 0;
        int neighborCount = 0;

        while (rowQueue.isEmpty() == false) {
            if (neighborCount == 0) {
                depthCount++;
                neighborCount = rowQueue.size();
            }

            neighborCount--;

            int currentRow = rowQueue.poll();
            int currentCol = colQueue.poll();

            if (currentRow == rowSize - 1 && currentCol == colSize -1) {
                break;
            }

            //add North, South, East, West
            addNeighbors(maze, rowQueue, colQueue, currentRow - 1, currentCol, visited);
            addNeighbors(maze, rowQueue, colQueue, currentRow + 1, currentCol, visited);
            addNeighbors(maze, rowQueue, colQueue, currentRow, currentCol - 1, visited);
            addNeighbors(maze, rowQueue, colQueue, currentRow, currentCol + 1, visited);
        }

        return depthCount;
    }

    private static void addNeighbors(final int[][] maze, final Queue<Integer> rowQueue, final Queue<Integer> colQueue, int row, int col, final boolean[][] visited) {
        if (row < 0 || row >= maze.length) {
            return;
        }

        if (col < 0 || col >= maze[0].length) {
            return;
        }

        if (visited[row][col] == true) {
            return;
        }

        if (maze[row][col] == 0) {
            return;
        }

        rowQueue.add(row);
        colQueue.add(col);

        visited[row][col] = true;
    }
}
