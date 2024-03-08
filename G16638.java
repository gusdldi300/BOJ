package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class G16638 {
    private static char[] sOperands;

    private static int[] sOperatorsPriority;
    private static String sExpression;

    private static int sLargestSum = Integer.MIN_VALUE;
    private static Stack<Integer> sNumberStack = new Stack<>();
    private static Stack<Integer> sOperIndexStack = new Stack<>();
    private static char[] sPostFix;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int expressionSize = Integer.parseInt(br.readLine());

        sExpression = br.readLine();
        if (expressionSize == 1) {
            System.out.print(sExpression.charAt(0));
            return;
        }

        sOperands = new char[expressionSize / 2];
        sOperatorsPriority = new int[expressionSize / 2];
        int operIndex = 0;
        for (int i = 0; i < sExpression.length(); ++i) {
            char element = sExpression.charAt(i);
            if (element >= '0' && element <= '9') {
                continue;
            }

            switch (element) {
                case '+':
                    // intentional fallthrough
                case '-':
                    sOperatorsPriority[operIndex] = 2;
                    break;
                case '*':
                    sOperatorsPriority[operIndex] = 1;
                    break;
                default:
                    assert (false);
                    break;
            }

            sOperands[operIndex++] = element;
        }

        sPostFix = new char[sExpression.length()];
        getLargestSumRecursive(0, false);


        System.out.print(sLargestSum);
    }

    private static void getLargestSumRecursive(final int operIndex, boolean isBracket) {
        if (operIndex == sOperands.length) {
            int sum = getSum();
            sLargestSum = Math.max(sLargestSum, sum);

            return;
        }

        if (isBracket == true) {
            getLargestSumRecursive(operIndex + 1, false);

            return;
        }

        int lastPriority = sOperatorsPriority[operIndex];
        sOperatorsPriority[operIndex] = 0;
        getLargestSumRecursive(operIndex + 1, true);
        sOperatorsPriority[operIndex] = lastPriority;

        getLargestSumRecursive(operIndex + 1, false);
    }

    private static void getPostFixExpression() {
        int postFixIndex = 0;

        int operIndex = 0;
        for (int i = 0; i < sExpression.length(); ++i) {
            char element = sExpression.charAt(i);
            if (element >= '0' && element <= '9') {
                sPostFix[postFixIndex++] = element;

                continue;
            }

            while (!sOperIndexStack.isEmpty()) {
                if (sOperatorsPriority[operIndex] < sOperatorsPriority[sOperIndexStack.peek()]) {
                    break;
                }

                char oper = sOperands[sOperIndexStack.pop()];
                sPostFix[postFixIndex++] = oper;
            }

            sOperIndexStack.push(operIndex++);
        }

        while (!sOperIndexStack.isEmpty()) {
            sPostFix[postFixIndex++] = sOperands[sOperIndexStack.pop()];
        }
    }

    private static int getSum() {
        getPostFixExpression();

        for (int i = 0; i < sPostFix.length; ++i) {
            char element = sPostFix[i];
            if (element >= '0' && element <= '9') {
                sNumberStack.push(element - '0');

                continue;
            }

            int result = 0;
            int firstNum = sNumberStack.pop();
            int secondNum = sNumberStack.pop();
            switch (element) {
                case '+':
                    result = secondNum + firstNum;
                    break;
                case '-':
                    result = secondNum - firstNum;
                    break;
                case '*':
                    result = secondNum * firstNum;
                    break;
                default:
                    assert (false);
                    break;
            }

            sNumberStack.push(result);
        }

        return sNumberStack.pop();
    }


}
