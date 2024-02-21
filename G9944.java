package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G9944 {
    private static int sRowSize;
    private static int sColSize;

    private static int[] sRowDirs = new int[] {-1, 0, 1, 0};
    private static int[] sColDirs = new int[] {0, 1, 0, -1};

    private static char[][] sMap;
    private static int sAnswer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int num = 1;
        while (true) {
            String inputOrNull = br.readLine();
            if (inputOrNull == null) {
                break;
            }

            StringTokenizer st = new StringTokenizer(inputOrNull);
            sRowSize = Integer.parseInt(st.nextToken());
            sColSize = Integer.parseInt(st.nextToken());

            sMap = new char[sRowSize][sColSize];
            for (int row = 0; row < sRowSize; ++row) {
                String input = br.readLine();
                for (int col = 0; col < sColSize; ++col) {
                    sMap[row][col] = input.charAt(col);
                }
            }

            boolean[][] visited = new boolean[sRowSize][sColSize];
            for (int row = 0; row < sRowSize; ++row) {
                for (int col = 0; col < sColSize; ++col) {
                    if (sMap[row][col] == '.') {
                        visited[row][col] = true;
                        getLeastMoveCountRecursive(0, row, col, visited);
                        visited[row][col] = false;
                    }
                }
            }

            if (sAnswer == Integer.MAX_VALUE) {
                sAnswer = -1;
            }

            sb.append(String.format("Case %d: %d%s", num, sAnswer, System.lineSeparator()));

            sAnswer = Integer.MAX_VALUE;
            ++num;
        }

        System.out.print(sb.toString());
    }

    private static boolean hasVisitedAll(final boolean[][] visited) {
        for (int row = 0; row < sRowSize; ++row) {
            for (int col = 0; col < sColSize; ++col) {
                if (sMap[row][col] == '.' && !visited[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void getLeastMoveCountRecursive(int moveCount, int row, int col, boolean[][] visited) {
        if (hasVisitedAll(visited)) {
            sAnswer = Math.min(sAnswer, moveCount);

            return;
        }

        for (int dir = 0; dir < sRowDirs.length; ++dir) {
            int moveRow = row + sRowDirs[dir];
            int moveCol = col + sColDirs[dir];

            if (moveRow < 0 || moveRow >= sRowSize || moveCol < 0 || moveCol >= sColSize) {
                continue;
            }

            if (visited[moveRow][moveCol] || sMap[moveRow][moveCol] == '*') {
                continue;
            }

            boolean[][] copied = copyVisited(visited);
            while (true) {
                copied[moveRow][moveCol] = true;
                int nextRow = moveRow + sRowDirs[dir];
                int nextCol = moveCol + sColDirs[dir];

                if (nextRow < 0 || nextRow >= sRowSize || nextCol < 0 || nextCol >= sColSize) {
                    break;
                }

                if (visited[nextRow][nextCol] || sMap[nextRow][nextCol] == '*') {
                    break;
                }

                moveRow = nextRow;
                moveCol = nextCol;
            }

            getLeastMoveCountRecursive(moveCount + 1, moveRow, moveCol, copied);
        }
    }

    private static boolean[][] copyVisited(final boolean[][] visited) {
        boolean[][] copied = new boolean[sRowSize][sColSize];
        for (int row = 0; row < sRowSize; ++row) {
            for (int col = 0; col < sColSize; ++col) {
                copied[row][col] = visited[row][col];
            }
        }

        return copied;
    }

    private static void move(int dir, int moveRow, int moveCol, boolean[][] visited) {
        while (true) {
            if (moveRow < 0 || moveRow >= sRowSize || moveCol < 0 || moveCol >= sColSize) {
                break;
            }

            if (visited[moveRow][moveCol] || sMap[moveRow][moveCol] == '*') {
                break;
            }

            visited[moveRow][moveCol] = true;
            moveRow += sRowDirs[dir];
            moveCol += sColDirs[dir];
        }
    }

}
