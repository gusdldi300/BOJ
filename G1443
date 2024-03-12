package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class G1443 {
    private static final int START_NUMBER = 2;
    private static final int END_NUMBER = 9;

    private static int sDisplayDigit;
    private static int sMultipleCount;

    private static int sMaxDisplayNumber;
    private static int sBiggestNumber = Integer.MIN_VALUE;

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
        displayBiggestNumberRecursive(0, START_NUMBER, 1);

        if (sBiggestNumber == Integer.MIN_VALUE) {
            sBiggestNumber = -1;
        }

        System.out.print(sBiggestNumber);
    }


    private static void displayBiggestNumberRecursive(int count, int nextNumber, int displayNumber) {
        if (count == sMultipleCount) {
            sBiggestNumber = Math.max(sBiggestNumber, displayNumber);
            return;
        }

        for (int i = nextNumber; i <= END_NUMBER; ++i) {
            int newDisplayNumber = displayNumber * i;
            if (newDisplayNumber > sMaxDisplayNumber) {
                return;
            }

            displayBiggestNumberRecursive(count + 1, i, newDisplayNumber);
        }
    }

}
