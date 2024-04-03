package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G17070 {
    private static enum Direction {
        HORIZONTAL,
        VERTICAL,
        DIAGONAL
    }

    private static Direction[] sNextHorizontalDirs = new Direction[] {Direction.HORIZONTAL, Direction.DIAGONAL};
    private static Direction[] sNextVerticalDirs = new Direction[] {Direction.VERTICAL, Direction.DIAGONAL};
    private static Direction[] sNextDiagonalDirs = new Direction[] {Direction.HORIZONTAL, Direction.VERTICAL, Direction.DIAGONAL};

    private static int[][] sHouse;
    private static int[][] sVisited;
    private static int sHouseSize;
    private static int sMovableCount;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sHouseSize = Integer.parseInt(br.readLine());

        sHouse = new int[sHouseSize][sHouseSize];
        for (int row = 0; row < sHouseSize; ++row) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sHouseSize; ++col) {
                sHouse[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        getMovableNumberOfCasesRecursive(0, 1, Direction.HORIZONTAL);

        System.out.print(sMovableCount);
    }

    private static void getMovableNumberOfCasesRecursive(int lastRow, int lastCol, final Direction lastDir) {
        if (lastRow == sHouseSize - 1 && lastCol == sHouseSize - 1) {
            ++sMovableCount;

            return;
        }

        Direction[] nextDirs = null;
        switch (lastDir) {
            case HORIZONTAL:
                nextDirs = sNextHorizontalDirs;
                break;
            case VERTICAL:
                nextDirs = sNextVerticalDirs;
                break;
            case DIAGONAL:
                nextDirs = sNextDiagonalDirs;
                break;
            default:
                assert (false);
                break;
        }

        outer:
        for (Direction curDir : nextDirs) {
            int startRow = lastRow;
            int startCol = lastCol;

            switch (curDir) {
                case HORIZONTAL:
                    startCol++;
                    if (startCol >= sHouseSize || sHouse[startRow][startCol] == 1) {
                        continue outer;
                    }

                    break;
                case VERTICAL:
                    startRow++;
                    if (startRow >= sHouseSize || sHouse[startRow][startCol] == 1) {
                        continue outer;
                    }

                    break;
                case DIAGONAL:
                    if (startCol + 1 >= sHouseSize || startRow + 1 >= sHouseSize) {
                        continue outer;
                    }

                    if (sHouse[startRow + 1][startCol] == 1 || sHouse[startRow][startCol + 1] == 1|| sHouse[startRow + 1][startCol + 1] == 1) {
                        continue outer;
                    }

                    startRow++;
                    startCol++;

                    break;
                default:
                    assert (false);
                    break;
            }

            getMovableNumberOfCasesRecursive(startRow, startCol, curDir);
        }
    }
}
