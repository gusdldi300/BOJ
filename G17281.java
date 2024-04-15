package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G17281 {
    private static final int MAX_PLAYER_COUNT = 9;

    private static int sTotalInningCount;
    private static int sBiggestScore = Integer.MIN_VALUE;
    private static int[][] sInningResults;
    private static boolean[] sPlayerSet = new boolean[MAX_PLAYER_COUNT];

    private static int[] sSquad = new int[MAX_PLAYER_COUNT];
    private static final int MAX_BASE_COUNT = 4;
    private static boolean[] sField = new boolean[MAX_BASE_COUNT];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sTotalInningCount = Integer.parseInt(br.readLine());
        sInningResults = new int[sTotalInningCount][MAX_PLAYER_COUNT];
        for (int i = 0; i < sTotalInningCount; ++i) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < MAX_PLAYER_COUNT; ++j) {
                sInningResults[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        sSquad[3] = 0;
        sPlayerSet[0] = true;

        getBiggestScoreRecursive(0);
        System.out.print(sBiggestScore);
    }

    private static void getBiggestScoreRecursive(int playerCount) {
        if (playerCount == MAX_PLAYER_COUNT) {
            int score = getScore();
            sBiggestScore = Math.max(sBiggestScore, score);

            return;
        }

        for (int i = 1; i < MAX_PLAYER_COUNT; ++i) {
            if (sPlayerSet[i]) {
                continue;
            }

            if (playerCount == 3) {
                playerCount++;
            }

            sSquad[playerCount] = i;
            sPlayerSet[i] = true;
            getBiggestScoreRecursive(playerCount + 1);
            sPlayerSet[i] = false;
        }
    }

    private static final int MAX_OUT_COUNT = 3;

    private static int getScore() {
        int score = 0;

        int curInning = 0;
        int squadIndex = 0;
        while (curInning < sTotalInningCount) {
            int outCount = 0;
            while (outCount < MAX_OUT_COUNT) {
                int player = sSquad[squadIndex];
                int result = sInningResults[curInning][player];

                if (result == 0) {
                    outCount++;
                } else {
                    score += movePlayers(result);
                }

                squadIndex = (squadIndex + 1) % MAX_PLAYER_COUNT;
            }

            resetField();
            ++curInning;
        }

        return score;
    }

    private static void resetField() {
        for (int i = 0; i < MAX_BASE_COUNT; ++i) {
            sField[i] = false;
        }
    }

    private static int movePlayers(int result) {
        int newScore = 0;

        for (int i = MAX_BASE_COUNT - 1; i >= 0; --i) {
            if (!sField[i]) {
                continue;
            }

            int curBase = i + result;
            if (curBase >= MAX_BASE_COUNT) {
                ++newScore;
            } else {
                sField[curBase] = true;
            }

            sField[i] = false;
        }

        if (result == 4) {
            newScore++;
        } else {
            sField[result] = true;
        }

        return newScore;
    }
}
