package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 테이블 정의
 * dp[i] = i 까지의 합

 * 점화식
 * dp[k] = d[k - 1] + n[k]

 * 초기값 정의
 * dp[k] = n[k]
 */

public class S11659 {
    private static int sNumberCount;
    private static int sTestCount;
    private static int[] sNumbers;

    private static int[] sSums;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sNumberCount = Integer.parseInt(st.nextToken());
        sTestCount = Integer.parseInt(st.nextToken());

        sNumbers = new int[sNumberCount];
        sSums = new int[sNumberCount];

        st = new StringTokenizer(br.readLine());
        sNumbers[0] = Integer.parseInt(st.nextToken());
        sSums[0] = sNumbers[0];
        for (int i = 1; i < sNumberCount; ++i) {
            sNumbers[i] = Integer.parseInt(st.nextToken());
            sSums[i] = sSums[i - 1] + sNumbers[i];
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sTestCount; ++i) {
            st = new StringTokenizer(br.readLine());

            int startIndex = Integer.parseInt(st.nextToken()) - 1;
            int endIndex = Integer.parseInt(st.nextToken()) - 1;

            sb.append(sSums[endIndex] - sSums[startIndex] + sNumbers[startIndex]);
            sb.append(System.lineSeparator());
        }

        System.out.print(sb.toString());
    }
}
