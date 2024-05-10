package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class G17822 {
    private static class Rotate {
        private int num;
        private int dir;
        private int count;

        public Rotate(int num, int dir, int count) {
            this.num = num;
            this.dir = dir;
            this.count = count;
        }
    }
    private static int sRadius;

    private static int sNumberCount;
    private static int sRotateCount;
    private static int[][] sDisk;
    private static int[][] sTempDisk;
    private static ArrayList<Rotate> sRotates = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sRadius = Integer.parseInt(st.nextToken());
        sNumberCount = Integer.parseInt(st.nextToken());
        sRotateCount = Integer.parseInt(st.nextToken());

        sDisk = new int[sRadius][sNumberCount];
        sTempDisk = new int[sRadius][sNumberCount];
        for (int i = 0; i < sRadius; ++i) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < sNumberCount; ++j) {
                sDisk[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int sum = 0;
        for (int i = 0; i < sRotateCount; ++i) {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            int count = Integer.parseInt(st.nextToken());

            // move numbers
            int index = 1;
            for (int j = num; j <= sRadius; j = num * index) {
                int nextIndex = j - 1;
                for (int k = 0; k < sNumberCount; ++k) {
                    int moveIndex;
                    if (dir == 0) {
                        moveIndex = (k + count) % sNumberCount;
                    } else {
                        moveIndex = (sNumberCount + k - count) % sNumberCount;
                    }

                    sTempDisk[nextIndex][moveIndex] = sDisk[nextIndex][k];
                }

                int[] temp = sDisk[nextIndex];
                sDisk[nextIndex] = sTempDisk[nextIndex];
                sTempDisk[nextIndex] = temp;

                ++index;
            }

            // search neighbors
            boolean isRemoved = false;
            sum = 0;
            int numCount = 0;
            for (int row = 0; row < sRadius; ++row) {
                for (int col = 0; col < sNumberCount; ++col) {
                    if (sDisk[row][col] == 0) {
                        continue;
                    }

                    if (removeEqualNumNeighbors(row, col)) {
                        isRemoved = true;
                    } else {
                        sum += sDisk[row][col];
                        numCount++;
                    }

                }
            }

            if (!isRemoved) {
                double average = (double) sum / (double) numCount;

                sum = 0;
                for (int row = 0; row < sRadius; ++row) {
                    for (int col = 0; col < sNumberCount; ++col) {
                        if (sDisk[row][col] == 0) {
                            continue;
                        }

                        double curNum = (double) sDisk[row][col];
                        if (average > curNum) {
                            sDisk[row][col]++;
                        } else if (average < curNum) {
                            sDisk[row][col]--;
                        }

                        sum += sDisk[row][col];
                    }
                }
            }

        }

        System.out.print(sum);
    }

    private static boolean removeEqualNumNeighbors(int row, int col) {
        Queue<int[]> positions = new LinkedList<>();
        positions.add(new int[] {row, col});
        int num = sDisk[row][col];

        assert (num != 0);

        boolean isRemoved = false;
        while (!positions.isEmpty()) {
            int[] position = positions.remove();

            for (int dirIndex = 0; dirIndex < sDirs.length; ++dirIndex) {
                int nextRow = position[0] + sDirs[dirIndex][0];
                int nextCol = position[1] + sDirs[dirIndex][1];

                if (nextRow < 0 || nextRow >= sDisk.length) {
                    continue;
                }

                if (nextCol < 0 ) {
                    nextCol = (sNumberCount + nextCol) % sNumberCount;
                } else if (nextCol >= sDisk[0].length) {
                    nextCol = nextCol % sNumberCount;
                }

                if (sDisk[nextRow][nextCol] == 0 || sDisk[nextRow][nextCol] != num) {
                    continue;
                }

                sDisk[row][col] = 0;
                sDisk[nextRow][nextCol] = 0;

                positions.add(new int[] {nextRow, nextCol});
                isRemoved = true;
            }
        }

        return isRemoved;
    }

    private static int[][] sDirs = new int[][] {
        {-1, 0}, {0, 1}, {1, 0}, {0, -1}
    };
}
