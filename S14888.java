package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class S14888 {
    private static final int OPERATORS_SIZE = 4;

    private static int sNumbersSize;
    private static int[] sNumbers;
    private static int[] sOperatorSizes = new int[OPERATORS_SIZE];

    private static int sMaxSum = Integer.MIN_VALUE;
    private static int sMinSum = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sNumbersSize = Integer.parseInt(st.nextToken());
        sNumbers = new int[sNumbersSize];

        st = new StringTokenizer(br.readLine());
        for (int index = 0; index < sNumbersSize; ++index) {
            sNumbers[index] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int index = 0; index < OPERATORS_SIZE; ++index) {
            sOperatorSizes[index] = Integer.parseInt(st.nextToken());
        }

        getAnswerRecursive(1, sNumbers[0]);
        System.out.println(sMaxSum);
        System.out.print(sMinSum);
    }

    public static void getAnswerRecursive(int opersCount, int currentSum) {
        if (opersCount == sNumbersSize) {
            sMaxSum = sMaxSum < currentSum ? currentSum : sMaxSum;
            sMinSum = sMinSum > currentSum ? currentSum : sMinSum;

            return;
        }

        for (int index = 0; index < OPERATORS_SIZE; ++index) {
            if (sOperatorSizes[index] == 0) {
                continue;
            }

            sOperatorSizes[index]--;
            switch (index) {
                case 0:
                    getAnswerRecursive(opersCount + 1, currentSum + sNumbers[opersCount]);
                    break;
                case 1:
                    getAnswerRecursive(opersCount + 1, currentSum - sNumbers[opersCount]);
                    break;
                case 2:
                    getAnswerRecursive(opersCount + 1, currentSum * sNumbers[opersCount]);
                    break;
                case 3:
                    //
                    getAnswerRecursive(opersCount + 1, currentSum / sNumbers[opersCount]);
                    break;
                default:
                    break;
            }
            sOperatorSizes[index]++;
        }
    }
}
