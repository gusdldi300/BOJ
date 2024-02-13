package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class G15661 {
    private static int sPointsSize;
    private static int[] sSumRow;
    private static int[] sSumCol;

    private static int sMinDiff = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sPointsSize = Integer.parseInt(br.readLine());
        assert (sPointsSize >= 4 && sPointsSize <= 20);

        sSumRow = new int[sPointsSize];
        sSumCol = new int[sPointsSize];

        int sum = 0;
        for (int row = 0; row < sPointsSize; ++row) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sPointsSize; ++col) {
                int point = Integer.parseInt(st.nextToken());

                sSumRow[row] += point;
                sSumCol[col] += point;

                sum += point;
            }
        }

        getMinDifferenceRecursive(0, 0, sum);

        System.out.print(sMinDiff);
        br.close();
    }

    private static void getMinDifferenceRecursive(int index, int count, int sum) {
        if (index == sPointsSize || count == (sPointsSize / 2)) {
            sMinDiff = Math.min(sMinDiff, Math.abs(sum));

            return;
        }

        getMinDifferenceRecursive(index + 1, count + 1, sum - sSumRow[index] - sSumCol[index]);
        getMinDifferenceRecursive(index + 1, count, sum);
    }

    /*
    private static int sArraySize;
    private static int[][] sArray;

    private static int sMinDiff = Integer.MAX_VALUE;
    private static boolean[] sPicked;

    private static HashMap<String, Integer> cache = new HashMap<>();

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
        for (int count = 1; count <= (sArraySize / 2); ++count) {
            int[] startTeam = new int[count];
            int[] linkTeam = new int[sArraySize - count];

            getMinDifferenceRecursive(0, 0, count, startTeam, linkTeam);
        }

        System.out.print(sMinDiff);
    }

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

    */
}
