package algorithm.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class G2133 {
    public static void testCode(String[] args) throws IOException {
        int caseCount = getCaseCount();
        System.out.println(caseCount);
    }

    private static int getCaseCount() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int colSize = Integer.parseInt(bf.readLine());

        if ((colSize % 2) == 1) {
            return 0;
        }

        if (colSize == 2) {
            return 3;
        }

        if (colSize == 4) {
            return 11;
        }

        int[] firstCache = new int[colSize / 2];
        int[] secondCache = new int[colSize / 2];

        firstCache[0] = 3;
        firstCache[1] = 11;

        secondCache[0] = 0;
        secondCache[1] = 0;

        for (int index = 2; index < (colSize / 2); ++index) {
            secondCache[index] = secondCache[index - 1] + (firstCache[index - 2] * 2);

            firstCache[index] = (firstCache[index - 1] * 3) + secondCache[index] + 2;
        }

        return firstCache[(colSize / 2) - 1];
    }
}

