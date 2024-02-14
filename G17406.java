package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G17406 {
    private static class Rotation {
        private int row;
        private int col;
        private int offset;

        public Rotation(int row, int col, int offset) {
            this.row = row;
            this.col = col;
            this.offset = offset;
        }
    }

    private static int sRowSize;
    private static int sColSize;
    private static int sRotateSize;

    private static int[][] sArray;
    private static int sMinSum = Integer.MAX_VALUE;
    private static boolean[] sVisited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sRowSize = Integer.parseInt(st.nextToken());
        sColSize = Integer.parseInt(st.nextToken());
        assert (sRowSize >= 3 && sRowSize <= 50 && sColSize >= 3 && sColSize <= 50);

        sRotateSize = Integer.parseInt(st.nextToken());
        assert (sRotateSize <= 6 && sRotateSize >= 1);

        sArray = new int[sRowSize][sColSize];
        for (int row = 0; row < sRowSize; ++row) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sColSize; ++col) {
                sArray[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        Rotation[] rotations = new Rotation[sRotateSize];
        sVisited = new boolean[sRotateSize];
        for (int i = 0; i < sRotateSize; ++i) {
            st = new StringTokenizer(br.readLine());

            int rotateRow = Integer.parseInt(st.nextToken()) - 1;
            int rotateCol = Integer.parseInt(st.nextToken()) - 1;
            int offset = Integer.parseInt(st.nextToken());

            rotations[i] = new Rotation(rotateRow, rotateCol, offset);
        }

        getMinArrayRecursive(0, sArray, rotations);
        System.out.print(sMinSum);
    }

    private static void getMinArrayRecursive(int count, int[][] array, final Rotation[] rotations) {
        if (count == rotations.length) {
            int minRow = getMinRowSum(array);
            sMinSum = Math.min(sMinSum, minRow);

            return;
        }

        for (int index = 0; index < rotations.length; ++index) {
            if (sVisited[index]) {
                continue;
            }

            int[][] copied = copyArray(array);
            rotateRecursive(rotations[index].offset, rotations[index], copied);

            sVisited[index] = true;
            getMinArrayRecursive(count + 1, copied, rotations);
            sVisited[index] = false;
        }
    }

    private static int[][] copyArray(final int[][] array) {
        int[][] copied = new int[sRowSize][sColSize];
        for (int i = 0; i < sRowSize; ++i) {
            for (int j = 0; j < sColSize; ++j) {
                copied[i][j] = array[i][j];
            }
        }

        return copied;
    }

    private static int getMinRowSum(final int[][] array) {
        int minSumRow = Integer.MAX_VALUE;
        for (int row = 0; row < sRowSize; ++row) {
            int sumRow = 0;
            for (int col = 0; col < sColSize; ++col) {
                sumRow += array[row][col];
            }

            minSumRow = Math.min(minSumRow, sumRow);
        }

        return minSumRow;
    }

    private static void rotateRecursive(int count, final Rotation rotation, int[][] array) {
        if (count == 0) {
            return;
        }

        int row = rotation.row - count;
        int col = rotation.col - count;
        int size = count * 2 + 1;

        int endRow = rotation.row + count;
        int endCol = rotation.col + count;

        int forwardLast = array[row][endCol];
        int backwardLast = array[endRow][col];
        int downwardLast = array[endRow][endCol];
        int upwardLast = array[row][col];
        for (int i = 0; i < size - 1; ++i) {
            array[row][endCol - i] = array[row][endCol - i - 1];
            array[endRow - i][endCol] = array[endRow - i - 1][endCol];
            array[endRow][col + i] = array[endRow][col + i + 1];
            array[row + i][col] = array[row + i + 1][col];
        }

        array[row + 1][endCol] = forwardLast;
        array[endRow][endCol - 1] = downwardLast;
        array[endRow - 1][col] = backwardLast;
        array[row][col + 1] = upwardLast;

        rotateRecursive(count - 1, rotation, array);
    }
}
