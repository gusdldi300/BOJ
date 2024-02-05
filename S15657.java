package backtracking;

import java.io.*;
import java.util.Arrays;

public class S15657 {
    private static int sSeqSize;
    private static int sSubSeqSize;
    private static int[] sSeq;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = br.readLine().split(" ");

        sSeqSize = Integer.parseInt(inputs[0]);
        sSubSeqSize = Integer.parseInt(inputs[1]);

        sSeq = new int[sSeqSize];
        inputs = br.readLine().split(" ");
        for (int index = 0; index < sSeqSize; ++index) {
            sSeq[index] = Integer.parseInt(inputs[index]);
        }

        Arrays.sort(sSeq);
        StringBuilder sb = new StringBuilder();
        int[] subSeq = new int[sSubSeqSize];

        getAnswerRecursive(subSeq, 0, 0, sb);
        System.out.print(sb.toString());

        br.close();
    }

    private static void getAnswerRecursive(final int[] subSeq, int subSeqIndex, int seqIndex, final StringBuilder sb) throws IOException {
        if (subSeqIndex == sSubSeqSize) {
            for (int index = 0; index < sSubSeqSize; ++index) {
                sb.append(subSeq[index]);
                sb.append(' ');
            }

            sb.append(System.lineSeparator());
            return;
        }

        for (int index = seqIndex; index < sSeqSize; ++index) {
            subSeq[subSeqIndex] = sSeq[index];
            getAnswerRecursive(subSeq, subSeqIndex + 1, index, sb);
        }
    }
}
