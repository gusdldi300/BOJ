package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G20057 {
    private static int sMapSize;
    private static int[][] sMap;
    private static final int RATIOS_SIZE = 5;
    private static double[][] sFlutterRatios = new double[][] {
            {0.0, 0.0, 0.02, 0.0, 0.0},
            {0.0, 0.1, 0.07, 0.01, 0.0},
            {0.05, 0.0, 0.0, 0.0, 0.0},
            {0.0, 0.1, 0.07, 0.01, 0.0},
            {0.0, 0.0, 0.02, 0.0, 0.0}
    };
    private static double[][] sTempRatios = new double[RATIOS_SIZE][RATIOS_SIZE];

    private static int[][] sDirs = new int[][] {
            {-1, 0}, {0, 1}, {1, 0}, {0, -1}
    };

    private static int sDisappearAmount = 0;

    private static int ROTATE_COUNT = 2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sMapSize = Integer.parseInt(br.readLine());
        sMap = new int[sMapSize][sMapSize];
        for (int row = 0; row < sMapSize; ++row) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sMapSize; ++col) {
                sMap[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        int row = sMapSize / 2;
        int col = row;
        int dirIndex = 3;
        int moveCount = 1;

        outer:
        while (true) {
            for (int rotate = 0; rotate < ROTATE_COUNT; ++rotate) {
                for (int move = 0; move < moveCount; ++move) {
                    row += sDirs[dirIndex][0];
                    col += sDirs[dirIndex][1];

                    flutterSand(row, col, dirIndex);

                    if (row == 0 && col == 0) {
                        break outer;
                    }
                }

                rotateRatiosCounterClockWise();
                dirIndex = (sDirs.length + (dirIndex - 1)) % sDirs.length;
            }

            ++moveCount;
        }

        System.out.print(sDisappearAmount);
    }

    private static void flutterSand(int startRow, int startCol, int dirIndex) {
        int curSandAmount = sMap[startRow][startCol];
        if (curSandAmount == 0) {
            return;
        }

        int nextSandAmount = curSandAmount;
        for (int row = 0; row < sFlutterRatios.length; ++row) {
            int mapRow = startRow - 2 + row;
            for (int col = 0; col < sFlutterRatios[0].length; ++col) {
                int mapCol = startCol - 2 + col;

                int flutterSandAmount = (int) (curSandAmount * sFlutterRatios[row][col]);
                if (!isOnBoundary(mapRow, mapCol)) {
                    sDisappearAmount += flutterSandAmount;
                } else {
                    sMap[mapRow][mapCol] += flutterSandAmount;
                }

                nextSandAmount -= flutterSandAmount;
            }
        }

        int nextRow = startRow + sDirs[dirIndex][0];
        int nextCol = startCol + sDirs[dirIndex][1];
        if (!isOnBoundary(nextRow, nextCol)) {
            sDisappearAmount += nextSandAmount;
        } else {
            sMap[nextRow][nextCol] += nextSandAmount;
        }

        sMap[startRow][startCol] = 0;
    }

    private static boolean isOnBoundary(int row, int col) {
        if (row < 0 || row >= sMapSize || col < 0 || col >= sMapSize) {
            return false;
        }

        return true;
    }

    private static void rotateRatiosCounterClockWise() {
        for (int row = 0; row < RATIOS_SIZE; ++row) {
            for (int col = 0; col < RATIOS_SIZE; ++col) {
                sTempRatios[RATIOS_SIZE - 1 - col][row] = sFlutterRatios[row][col];
            }
        }

        double[][] temp = sFlutterRatios;
        sFlutterRatios = sTempRatios;
        sTempRatios = temp;
    }
}
