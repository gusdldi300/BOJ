package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class G2293 {
    private static int sCoinCount;
    private static int sTargetAmount;
    private static int[] sCoins;
    private static int[] sCases;
    private static int[][] sCaseCoins;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = br.readLine().split(" ");
        sCoinCount = Integer.parseInt(inputs[0]);
        sTargetAmount = Integer.parseInt(inputs[1]);

        sCoins = new int[sCoinCount];
        for (int i = 0; i < sCoinCount; ++i) {
            sCoins[i] = Integer.parseInt(br.readLine());
        }

        sCases = new int[sTargetAmount + 1];
        sCaseCoins = new int[sTargetAmount + 1][sCoinCount];

        Arrays.sort(sCoins);
        sCases[0] = 1;
        if (sCoins[0] > sTargetAmount) {
            System.out.print(0);
            return;
        }

        sCases[sCoins[0]] = 1;
        for (int i = 1; i <= sTargetAmount; ++i) {
            int curCase = 0;

            for (int j = 0; j < sCoinCount; ++j) {
                int index = i - sCoins[j];
                if (index < 0) {
                    break;
                }

                int sum = sCases[index];
                for (int k = 0; k < j; ++k) {
                    sum -= sCaseCoins[index][k];
                }

                sCaseCoins[i][j] = sum;
                curCase += sum;
            }

            sCases[i] = curCase;
        }

        System.out.print(sCases[sTargetAmount]);
    }
}
