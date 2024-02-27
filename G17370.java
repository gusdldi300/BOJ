package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class G17370 {
    private static final int MAP_SIZE = 100;
    private static int sRotateSize;
    private static boolean[][] sVisited = new boolean[MAP_SIZE][MAP_SIZE];
    private static int sAnswer;
    private static Direction[] sNorthNextDirs = new Direction[] {Direction.NORTH_WEST, Direction.NORTH_EAST};
    private static Direction[] sNorthEastNextDirs = new Direction[] {Direction.NORTH, Direction.SOUTH_EAST};
    private static Direction[] sNorthWestNextDirs = new Direction[] {Direction.NORTH, Direction.SOUTH_WEST};
    private static Direction[] sSouthNextDirs = new Direction[] {Direction.SOUTH_WEST, Direction.SOUTH_EAST};
    private static Direction[] sSouthEastNextDirs = new Direction[] {Direction.SOUTH, Direction.NORTH_EAST};
    private static Direction[] sSouthWestNextDirs = new Direction[] {Direction.SOUTH, Direction.NORTH_WEST};

    private static enum Direction {
        NORTH,
        NORTH_EAST,
        NORTH_WEST,
        SOUTH,
        SOUTH_EAST,
        SOUTH_WEST
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sRotateSize = Integer.parseInt(br.readLine());
        if (sRotateSize < 5) {
            System.out.print(0);

            return;
        }

        if (sRotateSize == 5) {
            System.out.print(2);

            return;
        }

        int startRow = MAP_SIZE / 2;
        int startCol = startRow;
        sVisited = new boolean[MAP_SIZE][MAP_SIZE];
        sVisited[startRow][startCol] = true;

        getAnswerRecursive(0, startRow, startCol, Direction.NORTH);

        System.out.print(sAnswer);
    }

    private static void getAnswerRecursive(int rotateCount, int lastRow, int lastCol, final Direction dir) {
        Direction[] nextDirs = sNorthNextDirs;
        int row = lastRow;
        int col = lastCol;
        switch (dir) {
            case NORTH:
                row--;
                nextDirs = sNorthNextDirs;
                break;
            case NORTH_EAST:
                col++;
                nextDirs = sNorthEastNextDirs;
                break;
            case NORTH_WEST:
                col--;
                nextDirs = sNorthWestNextDirs;
                break;
            case SOUTH:
                row++;
                nextDirs = sSouthNextDirs;
                break;
            case SOUTH_EAST:
                col++;
                nextDirs = sSouthEastNextDirs;
                break;
            case SOUTH_WEST:
                col--;
                nextDirs = sSouthWestNextDirs;
                break;
            default:
                break;
        }

        //addTab(rotateCount);
        //System.out.format("%s (%d)\n", dir, rotateCount);

        if (rotateCount == sRotateSize) {
            if (sVisited[row][col]) {
                sAnswer++;
                //System.out.println(sAnswer);
            }

            return;
        }

        if (sVisited[row][col]) {
            return;
        }

        sVisited[row][col] = true;
        for (int i = 0; i < sNorthNextDirs.length; ++i) {
            getAnswerRecursive(rotateCount + 1, row, col, nextDirs[i]);
        }
        sVisited[row][col] = false;
    }

    private static void addTab(int count) {
        for (int i = 0; i < count; ++i) {
            System.out.print("    ");
        }
    }
}
