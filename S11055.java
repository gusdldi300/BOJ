package algorithm.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class S11055 {
    public static void testCode(String[] args) throws IOException {
        int total = getBiggestTotal();
        System.out.println(total);
    }

    private static int getBiggestTotal() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(bf.readLine());

        String[] inputs = bf.readLine().split(" ");
        int[] numbers = new int[size];
        for (int index = 0; index < size; ++index) {
            numbers[index] = Integer.parseInt(inputs[index]);
        }

        if (size == 1) {
            return numbers[0];
        }

        int[] cache = new int[size];
        cache[0] = numbers[0];

        for (int index = 1; index < size; ++index) {
            int biggerTotal = 0;
            for (int totalIndex = index -1; totalIndex >= 0; --totalIndex) {
                if (numbers[index] > numbers[totalIndex]) {
                    if (biggerTotal < cache[totalIndex]) {
                        biggerTotal = cache[totalIndex];
                    }
                }
            }

            cache[index] = numbers[index] + biggerTotal;
        }

        int biggestTotal = cache[0];
        for (int index = 1; index < size; ++index) {
            if (biggestTotal < cache[index]) {
                biggestTotal = cache[index];
            }
        }

        return biggestTotal;
    }
}
