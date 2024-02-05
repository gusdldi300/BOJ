package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class S15663 {
    private static int sSeqSize;
    private static int sSubSeqSize;

    private static int[] sSeq;
    private static int[] sSubSeq;
    private static HashMap<Integer, Integer> sNumCounts = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs =  br.readLine().split(" ");

        sSeqSize = Integer.parseInt(inputs[0]);
        sSubSeqSize = Integer.parseInt(inputs[1]);

        sSubSeq = new int[sSubSeqSize];

        inputs = br.readLine().split(" ");

        for (int index = 0; index < sSeqSize; ++index) {
            int num = Integer.parseInt(inputs[index]);

            int count = 0;
            if (sNumCounts.containsKey(num)) {
                count = sNumCounts.get(num);
            }

            sNumCounts.put(num, count + 1);
        }

        sSeq = new int[sNumCounts.size()];
        int seqIndex = 0;
        for (int num : sNumCounts.keySet()) {
            sSeq[seqIndex++] = num;
        }

        Arrays.sort(sSeq);

        StringBuilder sb = new StringBuilder();
        getAnswerRecursive(0, sb);

        System.out.print(sb.toString());
    }

    private static void getAnswerRecursive(int subSeqIndex, final StringBuilder sb) {
        if (subSeqIndex == sSubSeqSize) {
            for (int index = 0; index < sSubSeqSize; ++index) {
                sb.append(sSubSeq[index]);
                sb.append(' ');
            }

            sb.append(System.lineSeparator());
            return;
        }

        for (int index = 0; index < sNumCounts.size(); ++index) {
            int num = sSeq[index];
            int count = sNumCounts.get(num);

            if (count == 0) {
                continue;
            }

            sNumCounts.put(num, count - 1);
            sSubSeq[subSeqIndex] = sSeq[index];
            getAnswerRecursive(subSeqIndex + 1, sb);
            sNumCounts.put(num, count);
        }
    }
}
