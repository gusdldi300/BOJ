package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G16938 {
    private static int sProblemsSize;
    private static int sLeft;
    private static int sRight;
    private static int sDiff;

    private static int sAnswer;
    private static int[] sProblems;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sProblemsSize = Integer.parseInt(st.nextToken());
        sLeft = Integer.parseInt(st.nextToken());
        sRight = Integer.parseInt(st.nextToken());
        sDiff = Integer.parseInt(st.nextToken());

        sProblems = new int[sProblemsSize];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < sProblemsSize; ++i) {
            sProblems[i] = Integer.parseInt(st.nextToken());
        }

        getNumberOfCasesRecursive(0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        System.out.print(sAnswer);

        br.close();
    }

    private static void getNumberOfCasesRecursive(int index, int count, int maxDifficulty, int minDifficulty, int sum) {
        if (index >= sProblemsSize) {
            if (sum >= sLeft && sum <= sRight && (maxDifficulty - minDifficulty) >= sDiff) {
                sAnswer++;
            }

            return;
        }

        int difficulty = sProblems[index];
        getNumberOfCasesRecursive(index + 1, count + 1, Math.max(maxDifficulty, difficulty), Math.min(minDifficulty, difficulty), sum + difficulty);
        getNumberOfCasesRecursive(index + 1, count, maxDifficulty, minDifficulty, sum);
    }
}
