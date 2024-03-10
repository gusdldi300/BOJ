package dp;

/*
 * 테이블 정의: dp[i]: i 번째 계단에 도달할 수 있는 가장 높은 점수
 * 점화식: dp[k] = max(dp[k - 2], dp[k - 3] + s[k - 1]) + s[k]
 * 초기값 정의: dp[0], dp[1], dp[3] 정의
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class S2579 {
    private static int sTotalStairCount;
    private static int[] sStairScores;
    private static int[] sMaxScores;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sTotalStairCount = Integer.parseInt(br.readLine());

        sStairScores = new int[sTotalStairCount];
        for (int i = 0; i < sTotalStairCount; ++i) {
            sStairScores[i] = Integer.parseInt(br.readLine());
        }

        if (sTotalStairCount == 1) {
            System.out.print(sStairScores[0]);

            return;
        }

        if (sTotalStairCount == 2) {
            System.out.print(sStairScores[0] + sStairScores[1]);

            return;
        }

        sMaxScores = new int[sTotalStairCount];
        sMaxScores[0] = sStairScores[0];
        sMaxScores[1] = sMaxScores[0] + sStairScores[1];
        sMaxScores[2] = Math.max(sStairScores[0], sStairScores[1]) + sStairScores[2];

        for (int curStair = 3; curStair < sTotalStairCount; ++curStair) {
            sMaxScores[curStair] = Math.max(sMaxScores[curStair - 2], sMaxScores[curStair - 3] + sStairScores[curStair - 1]) + sStairScores[curStair];
        }

        System.out.print(sMaxScores[sTotalStairCount - 1]);
    }
}
