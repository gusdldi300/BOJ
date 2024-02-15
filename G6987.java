package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class G6987 {
    private static final int RESULT_SIZE = 4;
    private static final int ROW_SIZE = 6;
    private static final int COL_SIZE = 3;

    private static int[][][] sResults = new int[RESULT_SIZE][ROW_SIZE][COL_SIZE];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int resultIndex = 0; resultIndex < RESULT_SIZE; ++resultIndex) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int row = 0; row < ROW_SIZE; ++row) {
                for (int col = 0; col < COL_SIZE; ++col) {
                    sResults[resultIndex][row][col] = Integer.parseInt(st.nextToken());
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int resultIndex = 0; resultIndex < RESULT_SIZE; ++resultIndex) {
            if (isPossibleRecursive(resultIndex, 0, 1)) {
                sb.append(1);
            } else {
                sb.append(0);
            }

            sb.append(' ');
        }

        System.out.print(sb.toString());
    }

    private static boolean isPossibleRecursive(final int resultIndex, int currentTeam, int opposeTeam) {
        if (opposeTeam >= ROW_SIZE) {
            for (int state = 0; state < COL_SIZE; ++state) {
                if (sResults[resultIndex][currentTeam][state] != 0) {
                    return false;
                }
            }

            if (currentTeam == ROW_SIZE - 1) {
                return true;
            }

            return isPossibleRecursive(resultIndex, currentTeam + 1, currentTeam + 2);
        }

        for (int state = 0; state < COL_SIZE; ++state) {
            if (sResults[resultIndex][currentTeam][state] <= 0) {
                continue;
            }

            int opposeState = state;
            if (state == 0) {
                opposeState = 2;
            } else if (state == 2) {
                opposeState = 0;
            }

            if (sResults[resultIndex][opposeTeam][opposeState] <= 0) {
                continue;
            }

            sResults[resultIndex][currentTeam][state]--;
            sResults[resultIndex][opposeTeam][opposeState]--;
            if (isPossibleRecursive(resultIndex, currentTeam, opposeTeam + 1)) {
                return true;
            }
            sResults[resultIndex][opposeTeam][opposeState]++;
            sResults[resultIndex][currentTeam][state]++;
        }

        return false;
    }

    private static void updateResult(final int index, final int team, final int state, boolean[] visited) {
        for (int row = team + 1; row < ROW_SIZE; ++row) {
            if (sResults[index][team][state] <= 0) {
                return;
            }

            if (visited[row]) {
                continue;
            }

            switch (state) {
                case 0:
                    if (sResults[index][row][2] <= 0) {
                        continue;
                    }

                    sResults[index][row][2]--;

                    break;
                case 1:
                    if (sResults[index][row][1] <= 0) {
                        continue;
                    }

                    sResults[index][row][1]--;
                    break;
                case 2:
                    if (sResults[index][row][0] <= 0) {
                        continue;
                    }

                    sResults[index][row][0]--;
                    break;
                default:
                    break;
            }

            visited[row] = true;
            sResults[index][team][state]--;
        }
    }

    private static boolean isResultPossible(final int index) {
        for (int row = 0; row < ROW_SIZE; ++row) {
            for (int col = 0; col < COL_SIZE; ++col) {
                if (sResults[index][row][col] != 0) {
                    return false;
                }
            }
        }

        return true;
    }
}
