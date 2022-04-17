package algorithm.DP;

import java.io.*;

public class G10942 {
    public static void testCode(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        int size = Integer.parseInt(bf.readLine());

        int[] numbers = new int[size];
        String[] inputs = bf.readLine().split(" ");
        for (int index = 0; index < size; ++index) {
            numbers[index] = Integer.parseInt(inputs[index]);
        }

        int testCount = Integer.parseInt(bf.readLine());
        int[] startIndexes = new int[testCount];
        int[] endIndexes = new int[testCount];

        for (int index = 0; index < testCount; ++index) {
            inputs = bf.readLine().split(" ");
            startIndexes[index] = Integer.parseInt(inputs[0]);
            endIndexes[index] = Integer.parseInt(inputs[1]);
        }

        printAnswers(numbers, startIndexes, endIndexes);
    }


    private static void printAnswers(final int[] numbers, final int[] startIndexes, final int[] endIndexes) throws IOException {
        if (numbers.length == 1) {
            System.out.println(1);

            return;
        }

        // true: 1, false: -1, not done: 0
        int[][] cache = new int[numbers.length][numbers.length];

        int start;
        int end;
        for (int startIndex = 0; startIndex < numbers.length; ++startIndex) {
            for (int endIndex = numbers.length - 1; endIndex >= 0; --endIndex) {
                int mid = startIndex + endIndex;
                if (mid % 2 == 0) {
                    start = mid / 2;
                    end = mid / 2;
                } else {
                    start = mid / 2;
                    end = (mid / 2) + 1;
                }

                if (cache[start][end] != 0) {
                    break;
                }

                boolean isTrue = true;
                while (end <= endIndex) {
                    if (isTrue == false) {
                        cache[start][end] = -1;

                        start--;
                        end++;

                        continue;
                    }

                    if (numbers[start] != numbers[end]) {
                        cache[start][end] = -1;

                        isTrue = false;
                    } else {
                        cache[start][end] = 1;
                    }

                    start--;
                    end++;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < startIndexes.length; ++index) {
            int startIndex = startIndexes[index] - 1;
            int endIndex = endIndexes[index] - 1;

            sb.append(cache[startIndex][endIndex] == 1 ? 1 : 0);
            sb.append(System.lineSeparator());
        }

        System.out.println(sb.toString());
    }
}
