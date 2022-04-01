package algorithm.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class S7562 {
    public static void testCode(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int caseCount = Integer.parseInt(bf.readLine());

        int[] counts = new int[caseCount];

        // UP, DOWN, LEFT, RIGHT
        final int[] knightRowMoves = new int[] {-2, -2, 2, 2, 1, -1, 1, -1};
        final int[] knightColMoves = new int[] {-1, 1, -1, 1, -2, -2, 2, 2};

        String[] inputs;
        for (int index = 0; index < caseCount; ++index) {
            int chessSize = Integer.parseInt(bf.readLine());
            boolean[][] chess = new boolean[chessSize][chessSize];

            inputs = bf.readLine().split(" ");
            int startCol = Integer.parseInt(inputs[0]);
            int startRow = Integer.parseInt(inputs[1]);

            inputs = bf.readLine().split(" ");
            int targetCol = Integer.parseInt(inputs[0]);
            int targetRow = Integer.parseInt(inputs[1]);

            counts[index] = getLeastMoveCount(chess, knightRowMoves, knightColMoves, startRow, startCol, targetRow, targetCol);
        }

        for (int index = 0; index < counts.length; ++index) {
            System.out.println(counts[index]);
        }
    }

    private static int getLeastMoveCount(final boolean visited[][], final int[] knightRowMoves, final int[] knightColMoves, int startRow, int startCol, int targetRow, int targetCol) {
        Queue<Integer> rowQueue = new LinkedList<>();
        Queue<Integer> colQueue = new LinkedList<>();

        rowQueue.add(startRow);
        colQueue.add(startCol);
        visited[startRow][startCol] = true;

        int depthCount = -1;
        int neighborSize = 0;
        while (rowQueue.isEmpty() == false) {
            if (neighborSize == 0) {
                neighborSize = rowQueue.size();

                ++depthCount;
            }

            int row = rowQueue.poll();
            int col = colQueue.poll();

            if (row == targetRow && col == targetCol) {
                break;
            }

            neighborSize--;

            for (int index = 0; index < knightRowMoves.length; ++index) {
                addKnightMoves(visited, rowQueue, colQueue, row + knightRowMoves[index], col + knightColMoves[index]);
            }
        }

        return depthCount;
    }

    private static void addKnightMoves(final boolean[][] visited, final Queue<Integer> rowQueue, final Queue<Integer> colQueue, int row, int col) {
        if (row < 0 || row >= visited.length) {
            return;
        }

        if (col < 0 || col >= visited.length) {
            return;
        }

        if (visited[row][col] == true) {
            return;
        }

        rowQueue.add(row);
        colQueue.add(col);

        visited[row][col] = true;
    }

}
