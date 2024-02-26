package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class G16986 {
    private static final int PERSON_SIZE = 3;
    private static final int PERSON_MOVE_SIZE = 20;
    private static int sHandMoveSize;
    private static int sWinSize;
    private static int[][] sCompatibilities;

    private static int[][] sMoves = new int[PERSON_SIZE][PERSON_MOVE_SIZE];
    private static int[] sMoveIndexes = new int[PERSON_SIZE];

    private static int[] sWins = new int[PERSON_SIZE];
    private static HashSet<Integer> sJiUsed = new HashSet<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        sHandMoveSize = Integer.parseInt(st.nextToken());
        sWinSize = Integer.parseInt(st.nextToken());

        sCompatibilities = new int[sHandMoveSize][sHandMoveSize];
        for (int row = 0; row < sHandMoveSize; ++row) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sHandMoveSize; ++col) {
                sCompatibilities[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        StringTokenizer st2 = new StringTokenizer(br.readLine());
        for (int i = 0; i < PERSON_MOVE_SIZE; ++i) {
            sMoves[1][i] = Integer.parseInt(st.nextToken()) - 1;
            sMoves[2][i] = Integer.parseInt(st2.nextToken()) - 1;
        }

        if (isJiWinnableRecursive(0, 1)) {
            System.out.print(1);
        } else {
            System.out.print(0);
        }
    }

    private static boolean isJiWinnableRecursive(int first, int second) {
        if (sWins[1] == sWinSize || sWins[2] == sWinSize) {
            return false;
        }

        if (sWins[0] == sWinSize) {
            return true;
        }

        if (sJiUsed.size() == sHandMoveSize) {
            return false;
        }

        int winner;
        if (first != 0 && second != 0) {
            int firstMoveIndex = sMoveIndexes[first]++;
            int secondMoveIndex = sMoveIndexes[second]++;

            int result = sCompatibilities[sMoves[first][firstMoveIndex]][sMoves[second][secondMoveIndex]];

            if (result == 0) {
                winner = second;
            } else if (result == 1) {
                if (first > second) {
                    winner = first;
                } else {
                    winner = second;
                }
            } else {
                winner = first;
            }

            sWins[winner]++;
            //System.out.format("%d(%d) vs %d(%d): %d, winner: %d, wins: %d, %d, %d\n", first, sMoves[first][firstMoveIndex], second, sMoves[second][secondMoveIndex], result, winner, sWins[0], sWins[1], sWins[2]);
            if (isJiWinnableRecursive(0, winner)) {
                return true;
            }
            //System.out.println();

            sMoveIndexes[first]--;
            sMoveIndexes[second]--;

            sWins[winner]--;

        } else {
            for (int i = 0; i < sHandMoveSize; ++i) {
                if (sJiUsed.contains(i)) {
                    continue;
                }

                int jiMove = i;
                int enemy; // not Ji
                int result;
                if (first == 0) {
                    enemy = second;

                    result = sCompatibilities[jiMove][sMoves[enemy][sMoveIndexes[enemy]]];
                } else {
                    enemy = first;

                    result = sCompatibilities[sMoves[enemy][sMoveIndexes[enemy]]][jiMove];
                }

                if (result == 0) {
                    winner = second;
                } else if (result == 1) {
                    if (first > second) {
                        winner = first;
                    } else {
                        winner = second;
                    }
                } else {
                    winner = first;
                }

                sWins[winner]++;
                //System.out.format("%d(%d) vs %d(%d): %d, winner: %d, wins: %d, %d, %d\n", first == 0 ? 0 : first, first == 0 ? jiMove : sMoves[enemy][sMoveIndexes[enemy]], second == 0 ? 0 : second, second == 0 ? jiMove : sMoves[enemy][sMoveIndexes[enemy]], result, winner, sWins[0], sWins[1], sWins[2]);

                sMoveIndexes[enemy]++;
                sJiUsed.add(jiMove);
                if (isJiWinnableRecursive(winner, PERSON_SIZE - enemy)) {
                    return true;
                }
                //System.out.println();

                sJiUsed.remove(jiMove);
                sMoveIndexes[enemy]--;
                sWins[winner]--;
            }
        }

        return false;
    }
}
