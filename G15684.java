package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G15684 {
    private static int sRowSize;
    private static int sColSize;
    private static int sLineSize;

    private static boolean[][] sLines;

    private static int sAnswer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sColSize = Integer.parseInt(st.nextToken());
        sLineSize = Integer.parseInt(st.nextToken());
        sRowSize = Integer.parseInt(st.nextToken());

        sLines = new boolean[sRowSize][sColSize - 1];
        for (int i = 0; i < sLineSize; ++i) {
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;

            sLines[row][col] = true;
        }

        getMinMoreLinesRecursive(0, -1);
        if (sAnswer == Integer.MAX_VALUE) {
            sAnswer = -1;
        }

        System.out.print(sAnswer);
    }

    private static void getMinMoreLinesRecursive(int count, int position) {
        if (count > 3) {
            return;
        }

        if (isManipulated()) {
            sAnswer = Math.min(sAnswer, count);
            return;
        }

        while(++position < (sRowSize * (sColSize - 1))) {
            int nextRow = position / (sColSize - 1);
            int nextCol = position % (sColSize - 1);

            if (sLines[nextRow][nextCol]) {
                continue;
            }

            if (nextCol - 1 >= 0 && sLines[nextRow][nextCol - 1]) {
                continue;
            }

            if (nextCol + 1 < sColSize - 1 && sLines[nextRow][nextCol + 1]) {
                continue;
            }

            sLines[nextRow][nextCol] = true;
            getMinMoreLinesRecursive(count + 1, position);
            sLines[nextRow][nextCol] = false;
        }
    }

    private static boolean isManipulated() {
        for (int start = 0; start < sColSize; ++start) {
            int row = 0;
            int col = start;

            while (row < sRowSize) {
                int checkLine = col;
                if (checkLine < sColSize - 1 && sLines[row][checkLine]) {
                    row++;
                    col = checkLine + 1;

                    continue;
                }

                checkLine--;
                if (checkLine >= 0 && sLines[row][checkLine]) {
                    row++;
                    col = checkLine;

                    continue;
                }

                row++;
            }

            if (col != start) {
                return false;
            }
        }

        return true;
    }
}
