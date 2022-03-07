package algorithm.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class S1912 {
    public static void testCode(String[] args) throws IOException {
        int result = getBiggestResult();
        System.out.println(result);
    }

    /*
    private static int getBiggestResult() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(bf.readLine());

        int[] numbers = new int[size];
        String[] inputNumbers = bf.readLine().split(" ");
        for (int index = 0; index < size; ++index) {
            numbers[index] = Integer.parseInt(inputNumbers[index]);
        }

        if (size == 1) {
            return numbers[0];
        }

        int[] cache = new int[size];
        int biggestResult = numbers[0];

        for (int count = 0; count < size; ++count) {
            for (int index = 0; index < count; ++index) {
                cache[index] += numbers[count];
                
                if (biggestResult < cache[index]) {
                    biggestResult = cache[index];
                }
            }
        }

        return biggestResult;
    }
    */

    public static int getBiggestResult() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(bf.readLine());

        int[] numbers = new int[size];
        String[] inputNumbers = bf.readLine().split(" ");
        for (int index = 0; index < size; ++index) {
            numbers[index] = Integer.parseInt(inputNumbers[index]);
        }

        if (size == 1) {
            return numbers[0];
        }

        int lastNumber = numbers[0];
        int biggestResult = numbers[0];

        for (int index = 1; index < size; ++index) {
            lastNumber += numbers[index];
            if (lastNumber < numbers[index]) {
                lastNumber = numbers[index];
            }

            if (biggestResult < lastNumber) {
                biggestResult = lastNumber;
            }
        }

        return biggestResult;
    }
}
