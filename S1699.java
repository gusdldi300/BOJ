package dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 테이블 정의
 * dp[i], 숫자 i 의 제곱수의 합 최소 개수

 * 점화식
 * maxPowIndex = 제곱할 피연산자 중 가장 큰 수
 * dp[n] == dp[maxPowIndex ^ 2), dp[n] = 1

 * powIndex = 2 부터 maxPowIndex 까지 1씩 증가
 * dp[n] = min(이전 최솟 값, dp[powIndex ^ 2) + dp[n - powIndex ^ 2)) 중 가장 최소 개수

 * 초기값 정의
 * dp[1] = 1
 */

public class S1699 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int targetNum = Integer.parseInt(br.readLine());

        int[] minSquareAddCounts = new int[targetNum + 1];
        minSquareAddCounts[1] = 1;

        int maxPowIndex = 2;
        for (int num = 2; num <= targetNum; ++num) {
            int minSquareAddCount = Integer.MAX_VALUE;
            if (num == (maxPowIndex * maxPowIndex)) {
                minSquareAddCount = 1;

                ++maxPowIndex;
            } else {
                for (int powIndex = 1; powIndex < maxPowIndex; ++powIndex) {
                    int powNum = (powIndex * powIndex);
                    minSquareAddCount = Math.min(minSquareAddCount, minSquareAddCounts[powNum] + minSquareAddCounts[num - powNum]);
                }
            }

            minSquareAddCounts[num] = minSquareAddCount;
        }

        System.out.print(minSquareAddCounts[targetNum]);
    }
}
