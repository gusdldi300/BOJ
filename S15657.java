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
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int[] subSeq = new int[sSubSeqSize];

        getAnswerRecursive(subSeq, 0, 0, bw);
        bw.flush();

        br.close();
        bw.close();
    }

    private static void getAnswerRecursive(final int[] subSeq, int subSeqIndex, int seqIndex, final BufferedWriter bw) throws IOException {
        if (subSeqIndex == sSubSeqSize) {
            for (int index = 0; index < sSubSeqSize; ++index) {
                bw.append(String.valueOf(subSeq[index]));
                bw.append(' ');
            }

            bw.append(System.lineSeparator());
            return;
        }

        for (int index = seqIndex; index < sSeqSize; ++index) {
            subSeq[subSeqIndex] = sSeq[index];
            getAnswerRecursive(subSeq, subSeqIndex + 1, index, bw);
        }
    }
}
