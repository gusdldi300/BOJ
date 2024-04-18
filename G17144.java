package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class G17144 {
    private static class Position {
        private int row;
        private int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private static int[] sRowDirs = new int[] {-1, 0, 1, 0};
    private static int[] sColDirs = new int[] {0, 1, 0, -1};

    private static int sRowSize;
    private static int sColSize;
    private static int sTotalTime;
    private static int[][] sMap;
    private static int[][] sTempMap;
    private static ArrayList<Position> sAirCleaner = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sRowSize = Integer.parseInt(st.nextToken());
        sColSize = Integer.parseInt(st.nextToken());
        sTotalTime = Integer.parseInt(st.nextToken());

        sMap = new int[sRowSize][sColSize];
        sTempMap = new int[sRowSize][sColSize];
        for (int row = 0; row < sRowSize; ++row) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sColSize; ++col) {
                sMap[row][col] = Integer.parseInt(st.nextToken());

                if (sMap[row][col] == -1) {
                    sTempMap[row][col] = -1;
                    sAirCleaner.add(new Position(row, col));
                }
            }
        }

        int time = 0;
        while (time < sTotalTime) {
            for (int row = 0; row < sRowSize; ++row) {
                for (int col = 0; col < sColSize; ++col) {
                    if (sMap[row][col] <= 0) {
                        continue;
                    }

                    int newDustCount = 0;
                    int newDustAmount = sMap[row][col] / 5;

                    if (newDustAmount > 0) {
                        for (int dirIndex = 0; dirIndex < sRowDirs.length; ++dirIndex) {
                            int nextRow = row + sRowDirs[dirIndex];
                            int nextCol = col + sColDirs[dirIndex];

                            if (nextRow < 0 || nextRow >= sRowSize || nextCol < 0 || nextCol >= sColSize) {
                                continue;
                            }

                            if (sMap[nextRow][nextCol] == -1) {
                                continue;
                            }

                            sTempMap[nextRow][nextCol] += newDustAmount;
                            newDustCount++;
                        }
                    }

                    int leftDustAmount = sMap[row][col] - (newDustAmount * newDustCount);
                    if (leftDustAmount > 0) {
                        sTempMap[row][col] += leftDustAmount;
                    }

                    sMap[row][col] = 0;
                }
            }

            cleanUpDusts(sAirCleaner.get(0).row);
            cleanDownDusts(sAirCleaner.get(1).row);

            int[][] tempMap = sMap;
            sMap = sTempMap;
            sTempMap = tempMap;

            ++time;
        }

        int leftDustAmount = 0;
        for (int row = 0; row < sRowSize; ++row) {
            for (int col = 0; col < sColSize; ++col) {
                if (sMap[row][col] <= 0) {
                    continue;
                }

                leftDustAmount += sMap[row][col];
            }
        }

        System.out.print(leftDustAmount);
    }

    private static void cleanUpDusts(int startRow) {
        int lastForwardDust = sTempMap[startRow][sColSize - 1];
        for (int col = sColSize - 1; col >= 1; --col) {
            sTempMap[startRow][col] = sTempMap[startRow][col - 1];
        }
        sTempMap[startRow][1] = 0;

        int lastUpwardDust = sTempMap[0][sColSize - 1];
        for (int row = 0; row < startRow - 1; ++row) {
            sTempMap[row][sColSize - 1] = sTempMap[row + 1][sColSize - 1];
        }
        sTempMap[startRow - 1][sColSize - 1] = lastForwardDust;

        int lastBackwardDust = sTempMap[0][0];
        for (int col = 0; col < sColSize - 2; ++col) {
            sTempMap[0][col] = sTempMap[0][col + 1];
        }
        sTempMap[0][sColSize - 2] = lastUpwardDust;

        for (int row = startRow - 1; row >= 2; --row) {
            sTempMap[row][0] = sTempMap[row - 1][0];
        }
        sTempMap[1][0] = lastBackwardDust;
    }

    private static void cleanDownDusts(int startRow) {
        int lastForwardDust = sTempMap[startRow][sColSize - 1];
        for (int col = sColSize - 1; col >= 1; --col) {
            sTempMap[startRow][col] = sTempMap[startRow][col - 1];
        }
        sTempMap[startRow][1] = 0;

        int lastDownwardDust = sTempMap[sRowSize - 1][sColSize - 1];
        for (int row = sRowSize - 1; row > startRow + 1; --row) {
            sTempMap[row][sColSize - 1] = sTempMap[row - 1][sColSize - 1];
        }
        sTempMap[startRow + 1][sColSize - 1] = lastForwardDust;

        int lastBackwardDust = sTempMap[sRowSize - 1][0];
        for (int col = 0; col < sColSize - 2; ++col) {
            sTempMap[sRowSize - 1][col] = sTempMap[sRowSize - 1][col + 1];
        }
        sTempMap[sRowSize - 1][sColSize - 2] = lastDownwardDust;

        for (int row = startRow + 1; row < sRowSize - 2; ++row) {
            sTempMap[row][0] = sTempMap[row + 1][0];
        }
        sTempMap[sRowSize - 2][0] = lastBackwardDust;
    }
}
