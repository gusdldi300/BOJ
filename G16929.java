package algorithm.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class G16929 {
    private static final int MAX_DIRECTION = 4;
    private static int[] rowDirections = {-1, 1, 0, 0};
    private static int[] colDirections = {0, 0, -1, 1};

    public static void testCode(String[] args) throws IOException {
        printCycle();
    }

    private static void printCycle() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = bf.readLine().split(" ");

        int rowSize = Integer.parseInt(inputs[0]);
        int colSize = Integer.parseInt(inputs[1]);

        char[][] maze = new char[rowSize][colSize];
        for (int row = 0; row < rowSize; ++row) {
            String input = bf.readLine();

            maze[row] = input.toCharArray();
        }

        int count = 0;
        boolean[][] visited = new boolean[rowSize][colSize];
        boolean hasCycle = false;

        printLabel:
        for (int row = 0; row < rowSize; ++row) {
            for (int col = 0; col < colSize; ++col) {
                hasCycle = searchCycleRecursive(maze, maze[row][col], row, col, row, col, count, visited);

                if (hasCycle == true) {
                    break printLabel;
                }
            }
        }

        System.out.println(hasCycle == true ? "Yes" : "No");
    }

    private static boolean searchCycleRecursive(final char[][] maze, char color, int row, int col, int lastRow, int lastCol, int count, final boolean[][] visited) {
        if (row < 0 || row >= maze.length) {
            return false;
        }

        if (col < 0 || col >= maze[0].length) {
            return false;
        }

        if (visited[row][col] == true) {
            if (color == maze[row][col] && count >= 4) {
                return true;
            }

            return false;
        }

        if (color == maze[row][col]) {
            visited[row][col] = true;
            ++count;

            for (int index = 0; index < MAX_DIRECTION; ++index) {
                int nextRow = row + rowDirections[index];
                int nextCol = col + colDirections[index];

                if (nextRow == lastRow && nextCol == lastCol) {
                    continue;
                }

                boolean hasCycle = searchCycleRecursive(maze, color, nextRow, nextCol, row, col, count, visited);
                if (hasCycle == true) {
                    return true;
                }
            }
        }

        return false;
    }

}
