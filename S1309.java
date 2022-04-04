package algorithm.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class S1309 {
    public static void testCode(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int rowSize = Integer.parseInt(bf.readLine());

        printDeploymentCase(rowSize);
    }

    private static void printDeploymentCase(int rowSize) {
        if (rowSize == 1) {
            System.out.println(3);

            return;
        }

        int[] cache = new int[rowSize];
        cache[0] = 3;

        int temp = 1;
        for (int index = 1; index < rowSize; ++index) {
            // temp 가 cache 값보다 클 경우?
            temp = ((cache[index - 1] + 9901) - temp) % 9901;


            cache[index] = (cache[index - 1] + (temp * 2)) % 9901;
        }

        System.out.println(cache[rowSize - 1]);
    }
}
