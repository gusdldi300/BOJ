package dp;

/*
 * 테이블 정의: D[i], i를 1, 2, 3 합으로 나타내는 방법의 수
 * 점화식: D[k] = D[k - 1] + D[k - 2] + D[k - 3]
 * D[4] = D[3] + D[2] + D[1]
 * 초기값 정의: D[1] = 1, D[2] = 2, D[3] = 4
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class S9095 {
    private static final int MAX_NUMBER = 11;
    private static int sMaxTestCount;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sMaxTestCount = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        int[] cache = new int[MAX_NUMBER + 1];
        cache[1] = 1;
        cache[2] = 2;
        cache[3] = 4;

        /*
        for (int test = 0; test < sMaxTestCount; ++test) {
            int targetNum = Integer.parseInt(br.readLine());
            for (int num = 4; num <= targetNum; ++num) {
                cache[num] = cache[num - 1] + cache[num - 2] + cache[num - 3];
            }

            sb.append(cache[targetNum]);
            sb.append(System.lineSeparator());
        }
        */

        for (int num = 4; num <= MAX_NUMBER; ++num) {
            cache[num] = cache[num - 1] + cache[num - 2] + cache[num - 3];
        }

        for (int test = 0; test < sMaxTestCount; ++test) {
            sb.append(cache[Integer.parseInt(br.readLine())]);
            sb.append(System.lineSeparator());
        }

        System.out.print(sb.toString());
    }
}
