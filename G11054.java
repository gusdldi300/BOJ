package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class G11054 {
    public static void testCode(String[] args) throws IOException {
        int count = getLongestCount();
        System.out.println(count);
    }

    private static int getLongestCount() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(bf.readLine());

        String[] inputs = bf.readLine().split(" ");
        int[] numbers = new int[size];
        for (int index = 0; index < size; ++index) {
            numbers[index] = Integer.parseInt(inputs[index]);
        }

        if (size == 1) {
            return 1;
        }

        int[] ascendingCache = new int[size];

        // Ascending
        for (int index = 0; index < size; ++index) {
            int AscendingCount = 0;

            for (int cacheIndex = index - 1; cacheIndex >= 0; --cacheIndex) {
                if (numbers[index] > numbers[cacheIndex]) {
                    if (AscendingCount < ascendingCache[cacheIndex]) {
                        AscendingCount = ascendingCache[cacheIndex];
                    }
                }
            }

            ascendingCache[index] = AscendingCount + 1;
        }

        // Ascending
        int[] descendingCache = new int[size];

        for (int index = size - 1; index >= 0; --index) {
            int DescendingCount = 0;

            for (int cacheIndex = index + 1; cacheIndex < size; ++cacheIndex) {
                if (numbers[index] > numbers[cacheIndex]) {
                    if (DescendingCount < descendingCache[cacheIndex]) {
                        DescendingCount = descendingCache[cacheIndex];
                    }
                }
            }

            descendingCache[index] = DescendingCount + 1;
        }

        int longestCount = ascendingCache[0] + descendingCache[0];
        for (int index = 1; index < size; ++index) {
            int bitonicCount = ascendingCache[index] + descendingCache[index];
            if (longestCount < bitonicCount) {
                longestCount = bitonicCount;
            }
        }

        return longestCount - 1;
    }
}
