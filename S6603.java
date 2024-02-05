package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class S6603 {
    private static final int SUB_SEQ_SIZE = 6;
    private static int sSeqSize;
    private static int[] sSeq;
    private static int[] sSubSeq = new int[SUB_SEQ_SIZE];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String input = st.nextToken();
            if (input.equals("0")) {
                break;
            }

            sSeqSize = Integer.parseInt(input);
            sSeq = new int[sSeqSize];

            for (int index = 0; index < sSeqSize; ++index) {
                sSeq[index] = Integer.parseInt(st.nextToken());
            }

            getAnswerRecursive(0, 0, sb);
            sb.append(System.lineSeparator());
        }

        System.out.print(sb.toString());
    }

    private static void getAnswerRecursive(int seqIndex, int subSeqIndex, final StringBuilder sb) {
        if (subSeqIndex == SUB_SEQ_SIZE) {
            for (int index = 0; index < SUB_SEQ_SIZE; ++index) {
                sb.append(sSubSeq[index]);
                sb.append(' ');
            }
            sb.append(System.lineSeparator());

            return;
        }

        for (int index = seqIndex; index < sSeqSize; ++index) {
            sSubSeq[subSeqIndex] = sSeq[index];
            getAnswerRecursive(index + 1, subSeqIndex + 1, sb);
        }
    }
}
