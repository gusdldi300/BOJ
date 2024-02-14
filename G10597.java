package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class G10597 {
    private static final int MAX_NUM = 50;
    private static boolean[] sVisited = new boolean[MAX_NUM + 1];
    private static String sSeqString;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sSeqString = br.readLine();

        recoverSequenceRecursive(0, 0, new int[sSeqString.length()], 0);
    }

    private static boolean recoverSequenceRecursive(int index, int maxNum, int[] seq, int seqIndex) {
        if (index >= sSeqString.length()) {
            if (isSequence(maxNum)) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < seqIndex; ++i) {
                    sb.append(seq[i]);
                    sb.append(' ');
                }

                System.out.print(sb.toString());
                return true;
            }

            return false;
        }

        int num = (int) (sSeqString.charAt(index) - '0');
        for (int i = 1; i <= 2; ++i) {
            if (sSeqString.length() < (index + i)) {
                break;
            }

            if (i == 2) {
                num *= 10;
                num += (int) (sSeqString.charAt(index + 1) - '0');
            }

            if (num > MAX_NUM || num <= 0 || sVisited[num]) {
                continue;
            }

            sVisited[num] = true;
            seq[seqIndex] = num;
            if (recoverSequenceRecursive(index + i, Math.max(maxNum, num), seq, seqIndex + 1)) {
                return true;
            }

            sVisited[num] = false;
        }

        return false;
    }

    private static boolean isSequence(final int maxNum) {
        for (int num = maxNum; num >= 1; --num) {
            if (sVisited[num] == false) {
                return false;
            }
        }

        return true;
    }
}
