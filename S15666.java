package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class S15666 {
    private static int sSeqSize;
    private static int sSubSeqSize;

    private static int[] sSeq;
    private static int[] sSubSeq;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sSeqSize = Integer.parseInt(st.nextToken());
        sSubSeqSize = Integer.parseInt(st.nextToken());

        sSeq = new int[sSeqSize];
        sSubSeq = new int[sSubSeqSize];

        st = new StringTokenizer(br.readLine());
        for (int index = 0; index < sSeqSize; ++index) {
            sSeq[index] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(sSeq);
        StringBuilder sb = new StringBuilder();
        getAnswerRecursive(0, 0, sb);

        System.out.print(sb.toString());
    }

    private static void getAnswerRecursive(int seqIndex, int subSeqIndex, final StringBuilder sb) {
        if (subSeqIndex == sSubSeqSize) {
            for (int index = 0; index < sSubSeqSize; ++index) {
                sb.append(sSubSeq[index]);
                sb.append(' ');
            }

            sb.append(System.lineSeparator());
            return;
        }

        int lastNum = -1;
        for (int index = seqIndex; index < sSeqSize; ++index) {
            if (sSeq[index] == lastNum) {
                continue;
            }

            sSubSeq[subSeqIndex] = sSeq[index];
            getAnswerRecursive(index, subSeqIndex + 1, sb);

            lastNum = sSeq[index];
        }
    }
}

