package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class G1038 {
    private static int sTurn;
    private static HashMap<Long, Long> sCache = new HashMap<>();
    private static final long MAX_DESCENDING_NUMBER = 9876543210L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sTurn = Integer.parseInt(br.readLine());

        //1 2 3 4 5 6 7 8 9 10 20 21 30 31 32 40 41 42 43 51 52 53 54
        if (sTurn <= 10) {
            System.out.print(sTurn);

            return;
        }

        for (long first = 0; first < 10; ++first) {
            sCache.put(first, 1L);
        }

        long currentTurn = 9;
        long currentDividend = 1; // 10^1

        // 970
        while (((long) Math.pow(10, currentDividend)) <= MAX_DESCENDING_NUMBER) {
            long temp = (long) Math.pow(10, currentDividend);

            for (long first = currentDividend; first < 10; ++first) {
                long cacheTurn = 0;
                for (long last = currentDividend - 1; last < first; ++last) {
                    long addTurn = sCache.get(last * (temp / 10));

                    if (sTurn <= (currentTurn + addTurn)) {
                        long currentNum = (first * temp) + (last * (temp / 10));
                        getDescendNumberRecursive(currentNum, last, currentDividend - 2, currentTurn);

                        return;
                    }

                    currentTurn += addTurn;
                    cacheTurn += addTurn;
                }

                sCache.put(first * temp, cacheTurn);
            }

            currentDividend++;
        }

        System.out.print(-1);
    }

    private static void getDescendNumberRecursive(long currentNum, long last, long dividend, long currentTurn) {
        if (currentTurn == sTurn) {
            while (dividend >= 0) {
                currentNum += (last * (long) Math.pow(10, dividend));

                --last;
                --dividend;
            }
            System.out.print(currentNum);

            System.exit(0);
        }

        long temp = (long) Math.pow(10, dividend);
        for (long next = dividend; next < last; ++next) {
            long addTurn = sCache.get(next * temp);

            if ((currentTurn + addTurn) == sTurn) {
                getDescendNumberRecursive(currentNum + (next * temp), next - 1, dividend - 1, currentTurn + addTurn);
            } else if ((currentTurn + addTurn) > sTurn) {
                getDescendNumberRecursive(currentNum + (next * temp), next, dividend - 1, currentTurn);
            }

            currentTurn += addTurn;
        }
    }

    /*
    private static boolean isDescendNumberRecursive(final long currentNum, final long last) {
        if (currentNum <= 0) {
            return true;
        }

        long remainder = currentNum % 10;
        if (remainder <= last) {
            return false;
        }

        return isDescendNumberRecursive(currentNum / 10, remainder);
    }
    */
}
