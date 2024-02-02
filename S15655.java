package backtracking;

import java.io.*;
import java.util.Arrays;

public class S15655 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = br.readLine().split(" ");

        int size = Integer.parseInt(inputs[0]);
        int subSeqSize = Integer.parseInt(inputs[1]);

        int[] seq = new int[size];
        inputs = br.readLine().split(" ");
        for (int seqIndex = 0; seqIndex < size; ++seqIndex) {
            seq[seqIndex] = Integer.parseInt(inputs[seqIndex]);
        }

        Arrays.sort(seq);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int[] subSeq = new int[subSeqSize];
        getAnswerRecursive(0, seq, 0, subSeq, subSeqSize, bw);

        bw.flush();
    }

    private static void getAnswerRecursive(int seqIndex, final int[] seq, int subSeqIndex, final int[] subSeq, final int subSeqSize, final BufferedWriter bw) throws IOException {
        if (subSeqIndex == subSeqSize) {
            for (int i = 0; i < subSeqSize; ++i) {
                bw.append(String.valueOf(subSeq[i]));
                bw.append(' ');
            }
            bw.append(System.lineSeparator());

            return;
        }

        for (int i = seqIndex; i < seq.length; ++i) {
            subSeq[subSeqIndex] = seq[i];
            getAnswerRecursive(i + 1, seq, subSeqIndex + 1, subSeq, subSeqSize, bw);
        }
    }

}
