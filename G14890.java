package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G14890 {
    private static int sRampLength;
    private static int sMapSize;
    private static int[][] sMap;
    private static int[][] sDirs = new int[][] {
            {-1, 0}, {0, 1}, {1, 0}, {0, -1}
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        sMapSize = Integer.parseInt(st.nextToken());
        sRampLength = Integer.parseInt(st.nextToken());
        sMap = new int[sMapSize][sMapSize];

        for (int row = 0; row < sMapSize; ++row) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sMapSize; ++col) {
                sMap[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        int pathCount = 0;
        boolean[][] ramps = new boolean[sMapSize][sMapSize];
        row_outer:
        for (int row = 0; row < sMapSize; ++row) {
            int lastHeight = sMap[row][0];
            for (int col = 1; col < sMapSize; ++col) {
                int curHeight = sMap[row][col];

                if (Math.abs(lastHeight - curHeight) > 1) {
                    continue row_outer;
                }

                if (lastHeight < curHeight) {
                    if (!putRamp(row, col - 1, 3, ramps)) {
                        continue row_outer;
                    }
                } else if (lastHeight > curHeight) {
                    if (!putRamp(row, col, 1, ramps)) {
                        continue row_outer;
                    }
                }

                lastHeight = curHeight;
            }

            ++pathCount;
        }

        ramps = new boolean[sMapSize][sMapSize];
        col_outer:
        for (int col = 0; col < sMapSize; ++col) {
            int lastHeight = sMap[0][col];
            for (int row = 1; row < sMapSize; ++row) {
                int curHeight = sMap[row][col];

                if (Math.abs(lastHeight - curHeight) > 1) {
                    continue col_outer;
                }

                if (lastHeight < curHeight) {
                    if (!putRamp(row - 1, col, 0, ramps)) {
                        continue col_outer;
                    }
                } else if (lastHeight > curHeight) {
                    if (!putRamp(row, col, 2, ramps)) {
                        continue col_outer;
                    }
                }

                lastHeight = curHeight;
            }

            ++pathCount;
        }

        System.out.print(pathCount);
    }

    private static boolean isRampPlaceable(int row, int col, int dirIndex, final boolean[][] ramps) {
        int nextRow = row;
        int nextCol = col;

        for (int i = 0; i < sRampLength; ++i) {
            if (nextRow < 0 || nextRow >= sMapSize || nextCol < 0 || nextCol >= sMapSize) {
                return false;
            }

            if (ramps[nextRow][nextCol]) {
                return false;
            }

            if (sMap[nextRow][nextCol] != sMap[row][col]) {
                return false;
            }

            nextRow += sDirs[dirIndex][0];
            nextCol += sDirs[dirIndex][1];
        }

        return true;
    }

    private static boolean putRamp(int row, int col, int dirIndex, final boolean[][] ramps) {
        if (!isRampPlaceable(row, col, dirIndex, ramps)) {
            return false;
        }

        int nextRow = row;
        int nextCol = col;
        for (int i = 0; i < sRampLength; ++i) {
            ramps[nextRow][nextCol] = true;

            nextRow += sDirs[dirIndex][0];
            nextCol += sDirs[dirIndex][1];
        }

        return true;
    }
}
