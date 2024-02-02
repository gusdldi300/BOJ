package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class S1182 {
    private static int sAnswer = 0;

    /*
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] inputs = br.readLine().split(" ");
        int size = Integer.parseInt(inputs[0]);
        int sum = Integer.parseInt(inputs[1]);

        inputs = br.readLine().split(" ");
        int[] numbers = new int[size];
        for (int index = 0; index < size; ++index) {
            numbers[index] = Integer.parseInt(inputs[index]);
        }

        boolean[] cache = new boolean[size];
        for (int index = 0; index < numbers.length; ++index) {
            getAnswerRecursive(numbers, index + 1, numbers[index], sum);
        }

        System.out.print(sAnswer);
    }

    private static void getAnswerRecursive(int[] numbers, int numberIndex, int currentSum, int sum) {
        if (currentSum == sum) {
            ++sAnswer;
            return;
        }


        for (int index = numberIndex; index < numbers.length; ++index) {
            getAnswerRecursive(numbers, index + 1, currentSum + numbers[index], sum);
        }
    }
    */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = br.readLine().split(" ");

        int size = Integer.parseInt(inputs[0]);
        int sum = Integer.parseInt(inputs[1]);

        int[] seq = new int[size];
        inputs = br.readLine().split(" ");
        for (int seqIndex = 0; seqIndex < size; ++seqIndex) {
            seq[seqIndex] = Integer.parseInt(inputs[seqIndex]);
        }

        for (int seqIndex = 0; seqIndex < size; ++seqIndex) {
            getAnswerRecursive(seqIndex + 1, seq[seqIndex], seq, sum);
        }

        System.out.print(sAnswer);
    }

    private static void getAnswerRecursive(int currentIndex, int currentSum, final int[] seq, final int sum) {
        if (currentIndex == seq.length) {
            if (currentSum == sum) {
                ++sAnswer;
            }

            return;
        }

        getAnswerRecursive(currentIndex + 1, currentSum, seq, sum);
        getAnswerRecursive(currentIndex + 1, currentSum + seq[currentIndex], seq, sum);
    }

}
