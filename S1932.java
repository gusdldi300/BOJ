package algorithm.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class S1932 {
    public static void testCode(String[] args) throws IOException {
        int total = getBiggestTotal();
        System.out.println(total);
    }

    private static int getBiggestTotal() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int depthSize = Integer.parseInt(bf.readLine());

        if (depthSize == 1) {
            return Integer.parseInt(bf.readLine());
        }

        int[] cache = new int[depthSize];
        int[] inputs = new int[depthSize];
        int[] temp;
        for (int depth = 0; depth < depthSize; ++depth) {
            String[] inputStrings = bf.readLine().split(" ");

            int count = inputStrings.length;
            inputs[0] = cache[0] + Integer.parseInt(inputStrings[0]);

            for (int index = 1; index < count; ++index) {
                int biggerCache = Math.max(cache[index - 1], cache[index]);
                inputs[index] = biggerCache + Integer.parseInt(inputStrings[index]);
            }

            temp = cache;
            cache = inputs;
            inputs = temp;
        }

        int biggestNumber = cache[0];
        for (int index = 1; index < depthSize; ++index) {
            if (biggestNumber < cache[index]) {
                biggestNumber = cache[index];
            }
        }

        return biggestNumber;
    }
}
