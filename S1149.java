package dp;

/*
 * 테이블 정의:
 * dp[i][j]: i는 집의 개수, j는 색깔 개수
 * i 에서의 j 별 최소 가격
 *
 * 점화식:
 * dp[i][j] = Math.min(dp[i - 1][(j + 1) % 3], dp[i - 1][(j + 2) % 3]) + hp[i][j];
 *
 * 초기값 정의:
 * dp[0][1], dp[0][2], dp[0][3]
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class S1149 {
    private static final int MAX_COLOR_COUNT = 3;
    private static int sTotalHouseCount;
    private static int[][] sHousePrices;
    private static int[][] sMinPrices;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sTotalHouseCount = Integer.parseInt(br.readLine());

        sHousePrices = new int[sTotalHouseCount][MAX_COLOR_COUNT];
        for (int i = 0; i < sTotalHouseCount; ++i) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < MAX_COLOR_COUNT; ++j) {
                sHousePrices[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        sMinPrices = new int[sTotalHouseCount][MAX_COLOR_COUNT];
        sMinPrices[0][0] = sHousePrices[0][0];
        sMinPrices[0][1] = sHousePrices[0][1];
        sMinPrices[0][2] = sHousePrices[0][2];

        for (int i = 1; i < sTotalHouseCount; ++i) {
            for (int j = 0; j < MAX_COLOR_COUNT; ++j) {
                sMinPrices[i][j] = Math.min(sMinPrices[i - 1][(j + 1) % 3], sMinPrices[i - 1][(j + 2) % 3]) + sHousePrices[i][j];
            }
        }

        int minPrice = sMinPrices[sTotalHouseCount - 1][0];
        for (int i = 1; i < MAX_COLOR_COUNT; ++i) {
            minPrice = Math.min(minPrice, sMinPrices[sTotalHouseCount - 1][i]);
        }

        System.out.print(minPrice);
    }
}
