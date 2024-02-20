package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G5913 {
    private static final int MAP_SIZE = 5;
    private static int[][] sMap = new int[MAP_SIZE][MAP_SIZE];

    private static int sCollectSize;
    private static int sAnswer;

    private static int[] sRowDirs = new int[]{-1, 0, 1, 0};
    private static int[] sColDirs = new int[]{0, 1, 0, -1};

    private static boolean[][] sVisited = new boolean[MAP_SIZE][MAP_SIZE];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int rockSize = Integer.parseInt(br.readLine());
        for (int i = 0; i < rockSize; ++i) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;
            sMap[row][col] = 1;
        }

        sVisited[0][0] = true;
        sCollectSize = MAP_SIZE * MAP_SIZE - rockSize - 1;
        getNumberOfAppleCollectRecursive(0, 0, 0);

        System.out.print(sAnswer);
    }

    private static void getNumberOfAppleCollectRecursive(int count, int row, int col) {
        if (row >= MAP_SIZE - 1 && col >= MAP_SIZE - 1) {
            if (count == sCollectSize) {
                sAnswer++;
            }

            return;
        }


        for (int junDir = 0; junDir < sRowDirs.length; ++junDir) {
            int nextRow = row + sRowDirs[junDir];
            int nextCol = col + sColDirs[junDir];

            if (nextRow < 0 || nextRow >= MAP_SIZE || nextCol < 0 || nextCol >= MAP_SIZE) {
                continue;
            }

            if (sVisited[nextRow][nextCol]) {
                continue;
            }

            if (sMap[nextRow][nextCol] == 1) {
                continue;
            }

            sVisited[nextRow][nextCol] = true;
            getNumberOfAppleCollectRecursive(count + 1, nextRow, nextCol);
            sVisited[nextRow][nextCol] = false;
        }
    }

    /*
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int rockSize = Integer.parseInt(br.readLine());
        for (int i = 0; i < rockSize; ++i) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;
            sMap[row][col] = 1;
        }

        sCollectSize = (MAP_SIZE * MAP_SIZE - rockSize) / 2;
        sVisited[0][0] = true;
        sVisited[MAP_SIZE - 1][MAP_SIZE - 1] = true;
        getNumberOfAppleCollectRecursive(0, 0, 0, MAP_SIZE - 1, MAP_SIZE - 1);

        System.out.print(sAnswer);
    }



    private static void getNumberOfAppleCollectRecursive(int count, int junRow, int junCol, int haeRow, int haeCol) {
        for (int junDir = 0; junDir < sRowDirs.length; ++junDir) {
            int nextJunRow = junRow + sRowDirs[junDir];
            int nextJunCol = junCol + sColDirs[junDir];

            if (isCollectable(nextJunRow, nextJunCol)) {
                sVisited[nextJunRow][nextJunCol] = true;
                for (int haeDir = 0; haeDir < sRowDirs.length; ++haeDir) {
                    int nextHaeRow = haeRow + sRowDirs[haeDir];
                    int nextHaeCol = haeCol + sColDirs[haeDir];

                    if (count == sCollectSize - 1) {
                        if (nextJunRow == nextHaeRow && nextJunCol == nextHaeCol) {
                            ++sAnswer;
                        }

                        continue;
                    }

                    if (isCollectable(nextHaeRow, nextHaeCol)) {
                        sVisited[nextHaeRow][nextHaeCol] = true;
                        getNumberOfAppleCollectRecursive(count + 1, nextJunRow, nextJunCol, nextHaeRow, nextHaeCol);
                        sVisited[nextHaeRow][nextHaeCol] = false;
                    }
                }
                sVisited[nextJunRow][nextJunCol] = false;
            }
        }
    }

    private static boolean isCollectable(int row, int col) {
        if (row < 0 || row >= MAP_SIZE || col < 0 || col >= MAP_SIZE) {
            return false;
        }

        if (sVisited[row][col]) {
            return false;
        }

        if (sMap[row][col] == 1) {
            return false;
        }

        return true;
    }

    */
}
