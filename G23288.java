package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class G23288 {
    private static class Dice {
        private int row;
        private int col;
        private int dir;
        private int[] numbers = new int[] {2, 1, 5, 6, 4, 3};

        public Dice(int row, int col, int dir) {
            this.row = row;
            this.col = col;
            this.dir = dir;
        }
    }

    private static final int MAX_DICE_NUMBERS = 6;
    private static final int DICE_FLOOR_INDEX = 3;
    private static int sRowSize;
    private static int sColSize;
    private static int[][] sMap;

    private static int sMoveCount;
    private static int[][] sDirs = new int[][] {
            {-1, 0}, {0, 1}, {1, 0}, {0, -1}
    };

    private static Dice sDice = new Dice(0, 0, 1);
    private static int[] sTempNumbers = new int[] {2, 1, 5, 6, 4, 3};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sRowSize = Integer.parseInt(st.nextToken());
        sColSize = Integer.parseInt(st.nextToken());
        sMoveCount = Integer.parseInt(st.nextToken());

        sMap = new int[sRowSize][sColSize];
        for (int row = 0; row < sRowSize; ++row) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sColSize; ++col) {
                sMap[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        int score = 0;
        for (int move = 0; move < sMoveCount; ++move) {
            moveDice();
            score += getScore();

            int mapNumber = sMap[sDice.row][sDice.col];
            if (sDice.numbers[DICE_FLOOR_INDEX] > mapNumber) {
                sDice.dir = (sDice.dir + 1) % sDirs.length;
            } else if (sDice.numbers[DICE_FLOOR_INDEX] < mapNumber) {
                sDice.dir = (sDirs.length + (sDice.dir - 1)) % sDirs.length;
            }
        }

        System.out.print(score);
    }

    private static int getScore() {
        boolean[][] visited = new boolean[sRowSize][sColSize];

        Queue<int[]> positions = new LinkedList<>();
        positions.add(new int[] {sDice.row, sDice.col});
        visited[sDice.row][sDice.col] = true;
        int curScore = sMap[sDice.row][sDice.col];

        int scoreSum = 0;
        while (!positions.isEmpty()) {
            int[] position = positions.remove();
            scoreSum += sMap[position[0]][position[1]];

            for (int dir = 0; dir < sDirs.length; ++dir) {
                int nextRow = position[0] + sDirs[dir][0];
                int nextCol = position[1] + sDirs[dir][1];

                if (!isOnBoundary(nextRow, nextCol)) {
                    continue;
                }

                if (visited[nextRow][nextCol] || sMap[nextRow][nextCol] != curScore) {
                    continue;
                }

                positions.add(new int[] {nextRow, nextCol});
                visited[nextRow][nextCol] = true;
            }
        }

        return scoreSum;
    }

    private static void moveDice() {
        int moveRow = sDice.row + sDirs[sDice.dir][0];
        int moveCol = sDice.col + sDirs[sDice.dir][1];

        if (!isOnBoundary(moveRow, moveCol)) {
            sDice.dir = (sDice.dir + 2) % sDirs.length;

            moveRow = sDice.row + sDirs[sDice.dir][0];
            moveCol = sDice.col + sDirs[sDice.dir][1];
        }

        switch (sDice.dir) {
            case 0:
                sTempNumbers[0] = sDice.numbers[1];
                sTempNumbers[1] = sDice.numbers[2];
                sTempNumbers[2] = sDice.numbers[3];
                sTempNumbers[3] = sDice.numbers[0];
                sTempNumbers[4] = sDice.numbers[4];
                sTempNumbers[5] = sDice.numbers[5];
                break;
            case 1:
                sTempNumbers[0] = sDice.numbers[0];
                sTempNumbers[1] = sDice.numbers[4];
                sTempNumbers[2] = sDice.numbers[2];
                sTempNumbers[3] = sDice.numbers[5];
                sTempNumbers[4] = sDice.numbers[3];
                sTempNumbers[5] = sDice.numbers[1];
                break;
            case 2:
                sTempNumbers[0] = sDice.numbers[3];
                sTempNumbers[1] = sDice.numbers[0];
                sTempNumbers[2] = sDice.numbers[1];
                sTempNumbers[3] = sDice.numbers[2];
                sTempNumbers[4] = sDice.numbers[4];
                sTempNumbers[5] = sDice.numbers[5];
                break;
            case 3:
                sTempNumbers[0] = sDice.numbers[0];
                sTempNumbers[1] = sDice.numbers[5];
                sTempNumbers[2] = sDice.numbers[2];
                sTempNumbers[3] = sDice.numbers[4];
                sTempNumbers[4] = sDice.numbers[1];
                sTempNumbers[5] = sDice.numbers[3];
                break;
            default:
                assert (true);
                break;
        }

        int[] temp = sDice.numbers;
        sDice.numbers = sTempNumbers;
        sTempNumbers = temp;

        sDice.row = moveRow;
        sDice.col = moveCol;
    }

    private static boolean isOnBoundary(int row, int col) {
        if (row < 0 || row >= sRowSize || col < 0 || col >= sColSize) {
            return false;
        }

        return true;
    }
}
