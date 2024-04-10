package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class G2240 {
    private static int sTimeCount;
    private static int[] sFallPlumTrees;
    private static int sMoveCount;
    private static int[][] sMostPlumCounts;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] inputs = br.readLine().split(" ");
        sTimeCount = Integer.parseInt(inputs[0]);
        sMoveCount = Integer.parseInt(inputs[1]) + 1;

        sFallPlumTrees = new int[sTimeCount];
        sMostPlumCounts = new int[sTimeCount][sMoveCount];

        int fallTree = Integer.parseInt(br.readLine());
        int curTree = 1;
        for (int move = 0; move < sMoveCount; ++move) {
            if (move % 2 == 0) {
                curTree = 1;
            } else {
                curTree = 2;
            }

            if (curTree == fallTree) {
                sMostPlumCounts[0][move] = 1;
            }
        }

        for (int time = 1; time < sTimeCount; ++time) {
            fallTree = Integer.parseInt(br.readLine());

            sMostPlumCounts[time][0] = sMostPlumCounts[time - 1][0];
            if (fallTree == 1) {
                sMostPlumCounts[time][0]++;
            }

            for (int move = 1; move < sMoveCount; ++move) {
                if (move % 2 == 0) {
                    curTree = 1;
                } else {
                    curTree = 2;
                }

                sMostPlumCounts[time][move] = Math.max(sMostPlumCounts[time - 1][move - 1], sMostPlumCounts[time - 1][move]);
                if (curTree == fallTree) {
                    sMostPlumCounts[time][move]++;
                }
            }
        }

        int mostPlumCount = Integer.MIN_VALUE;
        for (int move = 0; move < sMoveCount; ++move) {
            if (mostPlumCount < sMostPlumCounts[sTimeCount - 1][move]) {
                mostPlumCount = sMostPlumCounts[sTimeCount - 1][move];
            }
        }

        System.out.print(mostPlumCount);
    }
}
