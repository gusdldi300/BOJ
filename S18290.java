package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class S18290 {
    private static class Number implements Comparable<Number> {
        private int number;
        private int row;
        private int col;

        public Number(int number, int row, int col) {
            this.number = number;
            this.row = row;
            this.col = col;
        }

        @Override
        public int compareTo(final Number other) {
            return other.number - this.number;
        }
    }

    private static Number sNumbers[];
    private static int sVisited[][];
    private static int sRowSize;
    private static int sColSize;

    private static int sSelectSize;
    private static int sAnswer = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        sRowSize = Integer.parseInt(st.nextToken());
        sColSize = Integer.parseInt(st.nextToken());
        sSelectSize = Integer.parseInt(st.nextToken());

        sNumbers = new Number[sRowSize * sColSize];
        int numbersIndex = 0;
        for (int i = 0; i < sRowSize; ++i) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < sColSize; ++j) {
                sNumbers[numbersIndex++] = new Number(Integer.parseInt(st.nextToken()), i, j);
            }
        }

        Arrays.sort(sNumbers);
        sVisited = new int[sRowSize][sColSize];

        getAnswerRecursive(0, 0, 0);
        System.out.print(sAnswer);
    }

    private static void getAnswerRecursive(int numIndex, int selectIndex, int currentSum) {
        if (selectIndex == sSelectSize) {
            sAnswer = Math.max(sAnswer, currentSum);
            return;
        }

        for (int index = numIndex; index < sNumbers.length; ++index) {
            Number num = sNumbers[index];
            if (sVisited[num.row][num.col] != 0) {
                continue;
            }

            updateVisited(num, true);
            getAnswerRecursive(index + 1, selectIndex + 1, currentSum + num.number);
            updateVisited(num, false);
        }
    }

    private static void updateVisited(final Number num, final boolean bTrue) {
        int update = bTrue == true ? 1 : -1;

        int row = Math.max(num.row - 1, 0);
        int col = num.col;
        sVisited[row][col] += update;

        row = num.row;
        col = Math.min(num.col + 1, sColSize - 1);
        sVisited[row][col] += update;

        row = Math.min(num.row + 1, sRowSize - 1);
        col = num.col;
        sVisited[row][col] += update;

        row = num.row;
        col = Math.max(num.col - 1, 0);
        sVisited[row][col] += update;
    }
}
