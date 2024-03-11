package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G1443 {
    private static final int START_NUMBER = 2;
    private static final int END_NUMBER = 9;

    private static int sDisplayDigit;
    private static int sMultipleCount;

    private static int sMaxDisplayNumber;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sDisplayDigit = Integer.parseInt(st.nextToken());
        sMultipleCount = Integer.parseInt(st.nextToken());
        if (sMultipleCount == 0) {
            System.out.print(1);

            return;
        }

        sMaxDisplayNumber = ((int) Math.pow(10, sDisplayDigit)) - 1;
        displayBiggestNumberRecursive(0, 1);

        if (sBiggestNumber == Integer.MAX_VALUE) {
            sBiggestNumber = -1;
        }

        System.out.print(sBiggestNumber);
    }

    private static int sBiggestNumber = Integer.MIN_VALUE;

    private static void displayBiggestNumberRecursive(int count, int number) {
        if (count == sMultipleCount) {
            sBiggestNumber = Math.max(sBiggestNumber, number);
            return;
        }

        for (int i = END_NUMBER; i >= START_NUMBER; --i) {
            int newNumber = number * i;
            if (newNumber > sMaxDisplayNumber) {
                return;
            }

            displayBiggestNumberRecursive(count + 1, newNumber);
        }
    }

}
