package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class S1189 {
    private static int sRowSize;
    private static int sColSize;
    private static int sVisitCount;
    private static char[][] sMap;
    private static int sAnswer;

    private static int[] sRowDirs = new int[] {-1, 0, 1, 0};
    private static int[] sColDirs = new int[] {0, 1, 0, -1};

    private static boolean[][] sVisited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sRowSize = Integer.parseInt(st.nextToken());
        sColSize = Integer.parseInt(st.nextToken());
        sVisitCount = Integer.parseInt(st.nextToken());

        sMap = new char[sRowSize][sColSize];
        for (int row = 0; row < sRowSize; ++row) {
            String input = br.readLine();
            for (int col = 0; col < sColSize; ++col) {
                sMap[row][col] = input.charAt(col);
            }
        }
        sMap[0][sColSize - 1] = 'H';

        sVisited = new boolean[sRowSize][sColSize];
        sVisited[sRowSize - 1][0] = true;
        getNumberOfCasesRecursive(sRowSize - 1, 0, 1);

        System.out.print(sAnswer);
    }

    private static void getNumberOfCasesRecursive(int row, int col, int count) {
        if (count == sVisitCount) {
            if (sMap[row][col] == 'H') {
                ++sAnswer;
            }

            return;
        }

        for (int dir = 0; dir < sRowDirs.length; ++dir) {
            int newRow = row + sRowDirs[dir];
            int newCol = col + sColDirs[dir];

            if (newRow < 0 || newRow >= sRowSize || newCol < 0 || newCol >= sColSize) {
                continue;
            }

            if (sVisited[newRow][newCol] == true) {
                continue;
            }

            if (sMap[newRow][newCol] == 'T') {
                continue;
            }

            sVisited[newRow][newCol] = true;
            getNumberOfCasesRecursive(newRow, newCol, count + 1);
            sVisited[newRow][newCol] = false;
        }
    }

}
