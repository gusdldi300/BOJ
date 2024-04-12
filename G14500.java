package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class G14500 {
    private static boolean[][][] sBlocks = new boolean[][][] {
            /*
            -----
            */
            {
                    {true, true, true, true}
            },
            {
                    {true},
                    {true},
                    {true},
                    {true}
            },

            /*
            --
            --
            */
            {
                    {true, true},
                    {true, true}
            },

            /*
            -
            -
            --
            */
            {
                    {true, false},
                    {true, false},
                    {true, true}
            },
            {
                    {false, true},
                    {false, true},
                    {true, true}
            },
            {
                    {true, true, true},
                    {true, false, false}
            },
            {
                    {true, true, true},
                    {false, false, true}
            },
            {
                    {true, true},
                    {false, true},
                    {false, true}
            },
            {
                    {true, true},
                    {true, false},
                    {true, false}
            },
            {
                    {false, false, true},
                    {true, true, true}
            },
            {
                    {true, false, false},
                    {true, true, true}
            },

            /*
            -
            --
             -
            */
            {
                    {true, false},
                    {true, true},
                    {false, true}
            },
            {
                    {false, true},
                    {true, true},
                    {true, false}
            },
            {
                    {false, true, true},
                    {true, true, false},
            },
            {
                    {true, true, false},
                    {false, true, true},
            },

            /*
            ---
             -
            */
            {
                    {true, true, true},
                    {false, true, false}
            },
            {
                    {false, true},
                    {true, true},
                    {false, true}
            },
            {
                    {false, true, false},
                    {true, true, true}
            },
            {
                    {true, false},
                    {true, true},
                    {true, false}
            },
    };

    /*
    private static int[][] sPaper;
    private static int sRowSize;
    private static int sColSize;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] inputs = br.readLine().split(" ");
        sRowSize = Integer.parseInt(inputs[0]);
        sColSize = Integer.parseInt(inputs[1]);

        sPaper = new int[sRowSize][sColSize];
        for (int row = 0; row < sRowSize; ++row) {
            inputs = br.readLine().split(" ");
            for (int col = 0; col < sColSize; ++col) {
                sPaper[row][col] = Integer.parseInt(inputs[col]);
            }
        }

        int maxSum = Integer.MIN_VALUE;
        for (int row = 0; row < sRowSize; ++row) {
            for (int col = 0; col < sColSize; ++col) {
                for (int blockIndex = 0; blockIndex < sBlocks.length; ++blockIndex) {
                    if (row + sBlocks[blockIndex].length > sRowSize || col + sBlocks[blockIndex][0].length > sColSize) {
                        continue;
                    }

                    int sum = 0;
                    for (int blockRow = 0; blockRow < sBlocks[blockIndex].length; ++blockRow) {
                        for (int blockCol = 0; blockCol < sBlocks[blockIndex][0].length; ++blockCol) {
                            if (!sBlocks[blockIndex][blockRow][blockCol]) {
                                continue;
                            }

                            sum += sPaper[row + blockRow][col + blockCol];
                        }
                    }

                    if (sum > maxSum) {
                        maxSum = sum;
                    }
                }
            }
        }

        System.out.print(maxSum);
    }
    */

    private static final int MAX_COUNT = 4;
    private static int[][] sPaper;
    private static int sRowSize;
    private static int sColSize;

    private static int[] sRowDirs = new int[] {-1, 0, 1, 0};
    private static int[] sColDirs = new int[] {0, 1, 0, -1};

    private static int sMaxSum = Integer.MIN_VALUE;
    private static boolean[][] sVisited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] inputs = br.readLine().split(" ");
        sRowSize = Integer.parseInt(inputs[0]);
        sColSize = Integer.parseInt(inputs[1]);

        sPaper = new int[sRowSize][sColSize];
        sVisited = new boolean[sRowSize][sColSize];
        for (int row = 0; row < sRowSize; ++row) {
            inputs = br.readLine().split(" ");
            for (int col = 0; col < sColSize; ++col) {
                sPaper[row][col] = Integer.parseInt(inputs[col]);
            }
        }

        for (int row = 0; row < sRowSize; ++row) {
            for (int col = 0; col < sColSize; ++col) {
                sVisited[row][col] = true;
                getTetrominoMaxSumRecursive(row, col, 1, sPaper[row][col]);
                sVisited[row][col] = false;

                // ㅗ
                outer:
                for (int startIndex = 0; startIndex < sRowDirs.length; ++startIndex) {
                    int sum = sPaper[row][col];
                    for (int count = 0; count < MAX_COUNT - 1; ++count) {
                        int dirIndex = (startIndex + count) % sRowDirs.length;

                        int nextRow = row + sRowDirs[dirIndex];
                        int nextCol = col + sColDirs[dirIndex];

                        if (!isOnBoundary(nextRow, nextCol)) {
                            continue outer;
                        }

                        sum += sPaper[nextRow][nextCol];
                    }

                    sMaxSum = Math.max(sMaxSum, sum);
                }
            }
        }

        System.out.print(sMaxSum);
    }

    private static boolean isOnBoundary(int row, int col) {
        if (row < 0 || row >= sRowSize || col < 0 || col >= sColSize) {
            return false;
        }

        return true;
    }

    private static void getTetrominoMaxSumRecursive(int row, int col, int count, int sum) {
        if (count == MAX_COUNT) {
            sMaxSum = Math.max(sMaxSum, sum);

            return;
        }

        for (int dirIndex = 0; dirIndex < sRowDirs.length; ++dirIndex) {
            int nextRow = row + sRowDirs[dirIndex];
            int nextCol = col + sColDirs[dirIndex];

            if (!isOnBoundary(nextRow, nextCol)) {
                continue;
            }

            if (sVisited[nextRow][nextCol]) {
                continue;
            }

            sVisited[nextRow][nextCol] = true;
            getTetrominoMaxSumRecursive(nextRow, nextCol, count + 1, sum + sPaper[nextRow][nextCol]);
            sVisited[nextRow][nextCol] = false;
        }
    }
}
