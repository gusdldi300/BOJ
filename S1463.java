package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class S1463 {
    private static int MAX_NUMBER = 1000000;
    private static int sNumber;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sNumber = Integer.parseInt(br.readLine());

        // 테이블 정의: D[i], i를 1로 만들기 위해 필요한 연산 사용 횟수의 최솟값
        // 점화식: D[k] = min(D[k / 3] + 1, D[k / 2] + 1, D[k - 1] + 1)
        // 초기값 정의: D[1] = 0

        int[] minCalCounts = new int[MAX_NUMBER + 1];
        minCalCounts[1] = 0;
        for (int num = 2; num <= sNumber; ++num) {
            int lastMin = minCalCounts[num - 1];
            if (num % 3 == 0) {
                lastMin = Math.min(lastMin, minCalCounts[num / 3]);
            }

            if (num % 2 == 0) {
                lastMin = Math.min(lastMin, minCalCounts[num / 2]);
            }

            minCalCounts[num] = lastMin + 1;
        }

        System.out.print(minCalCounts[sNumber]);
    }
}
