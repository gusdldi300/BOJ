package algorithm.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class S2193 {
    public static void testCode(String[] args) throws IOException {
        long count = getPinaryNumberCount();

        System.out.println(count);
    }

    private static long getPinaryNumberCount() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        int size = Integer.parseInt(bf.readLine());

        if (size == 1 || size == 2) {
            return 1;
        }

        long[] cache = new long[size];

        cache[0] = 1;
        cache[1] = 1;
        cache[2] = 2;

        for (int index = 3; index < size; ++index) {
            for (int addIndex = index - 2; addIndex >= 0; --addIndex) {
                cache[index] += cache[addIndex];
            }

            cache[index] += 1;
        }

        return cache[size - 1];
    }
}
