package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class G2661 {
    private static int sSeqSize;
    private static int[] sSeq;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sSeqSize = Integer.parseInt(br.readLine());
        sSeq = new int[sSeqSize];

        for (int num = 1; num <= 3; ++num) {
            sSeq[0] = num;
            if (getGoodSeqRecursive(1)) {
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sSeqSize; ++i) {
            sb.append(sSeq[i]);
        }

        System.out.print(sb.toString());
    }

    private static boolean getGoodSeqRecursive(int index) {
        if (index == sSeqSize) {
            return true;
        }

        for (int num = 1; num <= 3; ++num) {
            sSeq[index] = num;
            if (isBadSeq(index)) {
                continue;
            }

            if (getGoodSeqRecursive(index + 1)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isBadSeq(int index) {
        assert (index >= 1);

        if (sSeq[index - 1] == sSeq[index]) {
            return true;
        }

        for (int digit = 1; digit <= ((index + 1) / 2); ++digit) {
            int checkIndex = index - digit;
            int currentIndex = index;

            boolean bBadSeq = true;
            for (int count = digit; count > 0; --count) {
                if (sSeq[checkIndex--] != sSeq[currentIndex--]) {
                    bBadSeq = false;

                    break;
                }
            }

            if (bBadSeq == true) {
                return true;
            }
        }

        return false;
    }
}
