package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class S11057 {
    public static void testCode(String[] args) throws IOException {
        int count = getAscendingCount();
        System.out.println(count);
    }

/*
    private static long getAscendingCount() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int targetLength = Integer.parseInt(bf.readLine());

        if (targetLength == 1) {
            return 10;
        }

        if (targetLength == 2) {
            return 55;
        }

        final int CACHE_SIZE = 10;
        long[] firstCache = new long[CACHE_SIZE];
        long[] results = new long[CACHE_SIZE];

        long count = 10;
        for (int index = 0; index < CACHE_SIZE; ++index) {
            firstCache[index] = count;
            count--;
        }

        // Start from length 3
        long totalCount = 55;
        for (int numberLength = 3; numberLength <= targetLength; ++numberLength) {
            results[0] = totalCount;

            for (int index = 1; index < CACHE_SIZE; ++index) {
                results[index] = ((results[index - 1] - firstCache[index - 1]) + 10007) % 10007;
                totalCount += results[index];
            }

            // Swap cache;
            long[] tempCache = firstCache;
            firstCache = results;
            results = tempCache;
        }

        return totalCount;
    }
*/

    private static int getAscendingCount() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int targetLength = Integer.parseInt(bf.readLine());

        if (targetLength == 1) {
            return 10;
        }

        if (targetLength == 2) {
            return 55;
        }

        final int CACHE_SIZE = 10;
        BigInteger[] firstCache = new BigInteger[CACHE_SIZE];
        BigInteger[] results = new BigInteger[CACHE_SIZE];

        BigInteger count = new BigInteger("10");
        for (int index = 0; index < CACHE_SIZE; ++index) {
            firstCache[index] = new BigInteger(count.toString());
            count = count.subtract(new BigInteger("1"));
        }

        // Start from length 3
        BigInteger totalCount = new BigInteger("55");
        for (int numberLength = 3; numberLength <= targetLength; ++numberLength) {
            results[0] = totalCount;

            for (int index = 1; index < CACHE_SIZE; ++index) {
                results[index] = results[index - 1].subtract(firstCache[index - 1]);
                totalCount = totalCount.add(results[index]);
            }

            // Swap cache;
            BigInteger[] tempCache = firstCache;
            firstCache = results;
            results = tempCache;
        }

        return totalCount.mod(new BigInteger("10007")).intValue();
    }
}

