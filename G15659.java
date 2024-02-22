package backtracking;

import java.io.*;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

public class G15659 {
    private static final int OPERATORS_SIZE = 4;

    private static int sSeqSize;
    private static int[] sSeq;
    private static int[] sOperatorCounts = new int[OPERATORS_SIZE];

    private static int sMinSum = Integer.MAX_VALUE;
    private static int sMaxSum = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sSeqSize = Integer.parseInt(br.readLine());

        sSeq = new int[sSeqSize];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < sSeqSize; ++i) {
            sSeq[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i =0 ; i < OPERATORS_SIZE; ++i) {
            sOperatorCounts[i] = Integer.parseInt(st.nextToken());
        }

        sOperatorSeq = new int[sSeqSize - 1];
        getMinMaxSumRecursive(0);

        StringBuilder sb = new StringBuilder();
        sb.append(sMaxSum);
        sb.append(System.lineSeparator());
        sb.append(sMinSum);

        System.out.print(sb.toString());
    }

    private static int[] sOperatorSeq;

    private static void getMinMaxSumRecursive(int index) {
        if (index == (sSeqSize - 1)) {
            int sum = getSum();

            sMinSum = Math.min(sMinSum, sum);
            sMaxSum = Math.max(sMaxSum, sum);

            return;
        }

        for (int operator = 0; operator < OPERATORS_SIZE; ++operator) {
            if (sOperatorCounts[operator] == 0) {
                continue;
            }

            sOperatorCounts[operator]--;
            sOperatorSeq[index] = operator;
            getMinMaxSumRecursive(index + 1);
            sOperatorCounts[operator]++;
        }
    }

    private static LinkedList<Integer> sNumList = new LinkedList<>();
    private static LinkedList<Integer> sOperatorList = new LinkedList<>();

    private static int getSum() {
        sNumList.addLast(sSeq[0]);
        for (int i = 0; i < sOperatorSeq.length; ++i) {
            int num = 0;

            int operator = sOperatorSeq[i];
            switch (operator) {
                case 0:
                    // intentional fallthrough
                case 1:
                    num = sSeq[i + 1];
                    sOperatorList.addLast(operator);
                    break;
                case 2:
                    num = sNumList.removeLast() * sSeq[i + 1];
                    break;
                case 3:
                    num = sNumList.removeLast() / sSeq[i + 1];
                    break;
                default:
                    break;
            }

            sNumList.addLast(num);
        }

        while (!sOperatorList.isEmpty()) {
            int sum = sNumList.removeFirst();
            if (sOperatorList.removeFirst() == 0) {
                sum += sNumList.removeFirst();
            } else {
                sum -= sNumList.removeFirst();
            }

            sNumList.addFirst(sum);
        }

        return sNumList.remove();
    }
}
