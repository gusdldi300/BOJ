package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G15661 {
    private static int sArraySize;
    private static int[][] sArray;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sArraySize = Integer.parseInt(br.readLine());
        assert (sArraySize >= 4 && sArraySize <= 20);

        sArray = new int[sArraySize][sArraySize];
        for (int row = 0; row < sArraySize; ++row) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sArraySize; ++col) {
                sArray[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        sPicked = new boolean[sArraySize];

        int totalCount = (sArraySize % 2) == 0 ? (sArraySize / 2) : (sArraySize / 2) + 1;
        for (int count = 1; count <= totalCount; ++count) {
            int[] startTeam = new int[count];
            int[] linkTeam = new int[sArraySize - count];

            getMinDifferenceRecursive(0, 0, count, startTeam, linkTeam);
        }

        System.out.print(sMinDiff);
    }

    private static int sMinDiff = Integer.MAX_VALUE;
    private static boolean[] sPicked;

    private static void getMinDifferenceRecursive(int nextPlayer, int count, int totalCount, final int[] startTeam, final int[] linkTeam) {
        if (count == totalCount) {
            int startIndex = 0;
            int linkIndex = 0;

            for (int player = 0; player < sArraySize; ++player) {
                if (sPicked[player] == true) {
                    startTeam[startIndex++] = player;
                } else {
                    linkTeam[linkIndex++] = player;
                }
            }

            int diff = Math.abs(getPointsOf(startTeam) - getPointsOf(linkTeam));
            sMinDiff = Math.min(sMinDiff, diff);

            return;
        }

        for (int player = nextPlayer; player < sArraySize; ++player) {
            sPicked[player] = true;
            getMinDifferenceRecursive(player + 1, count + 1, totalCount, startTeam, linkTeam);
            sPicked[player] = false;
        }
    }

    private static int getPointsOf(final int[] team) {
        int sum = 0;
        for (int i = 0; i < team.length; ++i) {
            for (int j = i + 1; j < team.length; ++j) {
                sum += (sArray[team[i]][team[j]] + sArray[team[j]][team[i]]);
            }
        }

        return sum;
    }
}
