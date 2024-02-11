package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class G7490 {
    private static int sTestSize;
    private static int sSeqSize;

    private static LinkedList<Integer> sNumberStack = new LinkedList<>();
    private static Queue<Character> sOperatorQueue = new LinkedList<>();

    private static final char[] sOperators = new char[] {' ', '+', '-'};

    private static char[] sCurrentOperators;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sTestSize = Integer.parseInt(br.readLine());
        assert (sTestSize < 10);

        StringBuilder sb = new StringBuilder();
        for (int count = 0; count < sTestSize; ++count) {
            sSeqSize = Integer.parseInt(br.readLine());
            assert (sSeqSize >= 3 && sSeqSize <= 9);

            sCurrentOperators = new char[sSeqSize - 1];
            addZeroSumExpressionRecursive(0, sb);

            sb.append(System.lineSeparator());
        }

        System.out.print(sb.toString());
    }

    private static void addZeroSumExpressionRecursive(int operatorsCount, final StringBuilder sb) {
        if (operatorsCount == sCurrentOperators.length) {
            if (calculateExpression() == 0) {
                for (int operand = 1; operand < sSeqSize; operand++) {
                    sb.append(operand);
                    sb.append(sCurrentOperators[operand - 1]);
                }

                sb.append(sSeqSize);
                sb.append(System.lineSeparator());
            }

            return;
        }

        for (int i = 0; i < sOperators.length; ++i) {
            sCurrentOperators[operatorsCount] = sOperators[i];
            addZeroSumExpressionRecursive(operatorsCount + 1, sb);
        }
    }

    private static int calculateExpression() {
        int num = 1;
        sNumberStack.add(num);

        for (int operatorIndex = 0; operatorIndex < sCurrentOperators.length; ++operatorIndex) {
            ++num;

            char operator = sCurrentOperators[operatorIndex];
            int newNum = num;

            switch (operator) {
                case '+':
                    // intentional fallthrough
                case '-':
                    sOperatorQueue.add(operator);
                    break;
                case ' ':
                    newNum += (sNumberStack.pop() * 10);

                    break;
                default:
                    assert (true);
                    break;
            }

            sNumberStack.addFirst(newNum);
        }

        while (!sOperatorQueue.isEmpty()) {
            char operator = sOperatorQueue.remove();

            int firstNum = sNumberStack.removeLast();
            int secondNum = sNumberStack.removeLast();

            int sum = firstNum;
            switch (operator) {
                case '+':
                    sum += secondNum;
                    break;
                case '-':
                    sum -= secondNum;
                    break;
                default:
                    assert (true);
                    break;
            }

            sNumberStack.addLast(sum);
        }

        return sNumberStack.remove();
    }
}