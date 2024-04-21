package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class G20058 {
    private static class Position {
        private int row;
        private int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    private static int sMapPow;
    private static int sMagicCount;
    private static int[][] sMap;
    private static int[][] sTempMap;
    private static int[][] sDirs = new int[][] {
            {-1, 0}, {0, 1}, {1, 0}, {0, -1}
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sMapPow = Integer.parseInt(st.nextToken());
        sMagicCount = Integer.parseInt(st.nextToken());
        int mapSize = (int) Math.pow(2.0, sMapPow);

        sMap = new int[mapSize][mapSize];
        sTempMap = new int[mapSize][mapSize];
        for (int row = 0; row < mapSize; ++row) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < mapSize; ++col) {
                sMap[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        Queue<Position> meltPositions = new LinkedList<>();
        for (int i = 0; i < sMagicCount; ++i) {
            int level = Integer.parseInt(st.nextToken());
            int offset = (int) Math.pow(2.0, level);

            if (level > 0) {
                for (int row = 0; row < mapSize; row += offset) {
                    for (int col = 0; col < mapSize; col += offset) {
                        rotatePartClockWise(row, col, offset);
                    }
                }

                int[][] temp = sMap;
                sMap = sTempMap;
                sTempMap = temp;
            }

            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col < mapSize; col++) {
                    if (sMap[row][col] == 0) {
                        continue;
                    }

                    int combinedCount = 0;
                    for (int dirIndex = 0; dirIndex < sDirs.length; ++dirIndex) {
                        int nextRow = row + sDirs[dirIndex][0];
                        int nextCol = col + sDirs[dirIndex][1];

                        if (nextRow < 0 || nextRow >= mapSize || nextCol < 0 || nextCol >= mapSize) {
                            continue;
                        }

                        if (sMap[nextRow][nextCol] == 0) {
                            continue;
                        }

                        ++combinedCount;
                    }

                    if (combinedCount < 3) {
                        meltPositions.add(new Position(row, col));
                    }
                }
            }

            while (!meltPositions.isEmpty()) {
                Position meltPosition = meltPositions.remove();

                sMap[meltPosition.row][meltPosition.col]--;
            }
        }

        int sum = 0;
        for (int row = 0; row < mapSize; row++) {
            for (int col = 0; col < mapSize; col++) {
                sum += sMap[row][col];
            }
        }

        boolean[][] visited = new boolean[mapSize][mapSize];
        int biggestCount = Integer.MIN_VALUE;
        for (int row = 0; row < mapSize; row++) {
            for (int col = 0; col < mapSize; col++) {
                if (sMap[row][col] == 0 || visited[row][col]) {
                    continue;
                }

                int glacierCount = getGlacierCount(row, col, visited);
                biggestCount = Math.max(biggestCount, glacierCount);
            }
        }

        if (biggestCount <= 1) {
            biggestCount = 0;
        }

        System.out.format("%d%s%d", sum, System.lineSeparator(), biggestCount);
    }

    private static int getGlacierCount(int startRow, int startCol, final boolean[][] visited) {
        Queue<Position> icePositions = new LinkedList<>();
        icePositions.add(new Position(startRow, startCol));
        visited[startRow][startCol] = true;

        int count = 0;
        while (!icePositions.isEmpty()) {
            Position icePosition = icePositions.remove();

            for (int dirIndex = 0; dirIndex < sDirs.length; ++dirIndex) {
                int nextRow = icePosition.row + sDirs[dirIndex][0];
                int nextCol = icePosition.col + sDirs[dirIndex][1];

                if (nextRow < 0 || nextRow >= sMap.length || nextCol < 0 || nextCol >= sMap[0].length) {
                    continue;
                }

                if (visited[nextRow][nextCol]) {
                    continue;
                }

                if (sMap[nextRow][nextCol] == 0) {
                    continue;
                }

                visited[nextRow][nextCol] = true;
                icePositions.add(new Position(nextRow, nextCol));
            }

            ++count;
        }

        return count;
    }

    private static void rotatePartClockWise(int startRow, int startCol, int offset) {
        int endRow = startRow + offset - 1;
        int endCol = startCol + offset - 1;

        int rotateCol = startCol;
        for (int row = startRow; row <= endRow; ++row) {
            int rotateRow = endRow;
            for (int col = startCol; col <= endCol; ++col) {
                sTempMap[row][col] = sMap[rotateRow][rotateCol];
                --rotateRow;
            }

            ++rotateCol;
        }
    }
}
