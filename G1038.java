package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class G1038 {
    private static int sTurn;
    private static HashMap<Long, Integer> sCache = new HashMap<>();
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
            sCache.put(first, 1);
        }

        int turn = 9;
        int degree = 1; // 10 ^ 1

        long multiple = (long) Math.pow(10, degree);
        while (multiple <= MAX_DESCENDING_NUMBER) {
            long currentNum = degree * multiple;

            sCache.put(currentNum, 1);
            turn++;

            if (turn == sTurn) {
                printAnswer(currentNum, degree - 1, degree - 1);

                return;
            }

            for (int first = degree + 1; first < 10; ++first) {
                int last = first - 1;
                int cacheTurn = sCache.get(last * multiple) + sCache.get(last * (multiple / 10));

                currentNum = first * multiple;
                if (sTurn <= (turn + cacheTurn)) {
                    getDescendNumberRecursive(currentNum, first, degree - 1, turn);

                    return;
                }

                sCache.put(currentNum, cacheTurn);
                turn += cacheTurn;
            }

            degree++;
            multiple = (long) Math.pow(10, degree);
        }

        System.out.print(-1);
    }

    private static void printAnswer(long currentNum, int last, int degree) {
        long multiple = (long) Math.pow(10, degree);

        while (multiple > 0) {
            currentNum += (last * multiple);

            --last;
            multiple /= 10;
        }

        System.out.print(currentNum);
    }

    private static void getDescendNumberRecursive(long currentNum, int last, int degree, int turn) {
        if (turn == sTurn) {
            printAnswer(currentNum, last, degree);

            return;
        }

        long multiple = (long) Math.pow(10, degree);

        int next = degree;
        int nextTurn = turn + sCache.get(next * multiple);

        while (next < last) {
            if (nextTurn >= sTurn) {
                break;
            }

            ++next;

            turn = nextTurn;
            nextTurn = turn + sCache.get(next * multiple);
        }

        currentNum += (next * multiple);
        if (nextTurn == sTurn) {
            getDescendNumberRecursive(currentNum, next - 1, degree - 1, nextTurn);
        } else {
            getDescendNumberRecursive(currentNum, next, degree - 1, turn);
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
