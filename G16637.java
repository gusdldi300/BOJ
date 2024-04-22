package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

public class G16637 {
    private static int sLength;
    private static String sExpression;
    private static boolean[] sBracketOperators;
    private static int sMaxResult = Integer.MIN_VALUE;
    private static ArrayList<Character> sOperators = new ArrayList<>();
    private static ArrayList<Integer> sNumbers = new ArrayList<>();
    private static Stack<Character> sOperatorStack = new Stack<>();
    private static Stack<Integer> sNumberStack = new Stack<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sLength = Integer.parseInt(br.readLine());
        sExpression = br.readLine();

        for (int i = 0; i < sLength; ++i) {
            char curChar = sExpression.charAt(i);
            if (curChar >= '0' && curChar <= '9') {
                sNumbers.add(curChar - '0');
            } else {
                sOperators.add(curChar);
            }
        }

        int bracketsSize = sLength / 2;
        sBracketOperators = new boolean[bracketsSize];
        getMaxResultRecursive(0, false);

        System.out.print(sMaxResult);
    }

    private static void getMaxResultRecursive(int operIndex, boolean isLastOperBracket) {
        if (operIndex == sBracketOperators.length) {
            int result = calculateResult();
            sMaxResult = Math.max(sMaxResult, result);

            return;
        }

        for (int index = operIndex; index < sBracketOperators.length; ++index) {
            if (!isLastOperBracket) {
                sBracketOperators[index] = true;
                getMaxResultRecursive(index + 1, true);
                sBracketOperators[index] = false;
            }

            getMaxResultRecursive(index + 1, false);
        }
    }

    private static int calculateResult() {
        int numberIndex = sNumbers.size() - 1;
        int addNum = sNumbers.get(numberIndex--);
        sNumberStack.push(addNum);

        for (int i = sOperators.size() - 1; i >= 0; --i) {
            char operator = sOperators.get(i);

            addNum = sNumbers.get(numberIndex--);
            if (sBracketOperators[i]) {
                int lastNum = sNumberStack.pop();
                addNum = getSum(operator, addNum, lastNum);
            } else {
                sOperatorStack.push(operator);
            }

            sNumberStack.push(addNum);
        }

        int sum = 0;
        while (!sOperatorStack.isEmpty()) {
            char operator = sOperatorStack.pop();
            int firstNum = sNumberStack.pop();
            int secondNum = sNumberStack.pop();
            sum = getSum(operator, firstNum, secondNum);

            sNumberStack.push(sum);
        }

        return sNumberStack.pop();
    }

    private static int getSum(char operator, int first, int second) {
        int sum = 0;
        switch (operator) {
            case '+':
                sum = first + second;
                break;
            case '-':
                sum = first - second;
                break;
            case '*':
                sum = first * second;
                break;
            default:
                assert (false);
                break;
        }

        return sum;
    }
}
