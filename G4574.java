package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G4574 {
    //private static final int MAX_DOMINO_COUNT = 36;
    private static final int BOARD_SIZE = 9;
    private static int[][] sBoard = new int[BOARD_SIZE][BOARD_SIZE];
    private static boolean[][] sDominoes = new boolean[BOARD_SIZE + 1][BOARD_SIZE + 1];
    private static boolean[][] sRowVisited = new boolean[BOARD_SIZE + 1][BOARD_SIZE + 1];
    private static boolean[][] sColVisited = new boolean[BOARD_SIZE + 1][BOARD_SIZE + 1];
    private static boolean[][] sAreaVisited = new boolean[BOARD_SIZE + 1][BOARD_SIZE + 1];

    private static int[] sRowDirs = new int[] {-1, 0, 1, 0};
    private static int[] sColDirs = new int[] {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder sb = new StringBuilder();
        int testCount = 1;
        while (true) {
            int dominoCount = Integer.parseInt(br.readLine());
            if (dominoCount == 0) {
                break;
            }

            StringTokenizer st;
            for (int i = 0; i < dominoCount; ++i) {
                st = new StringTokenizer(br.readLine());

                int firstNum = Integer.parseInt(st.nextToken());
                setNumber(st.nextToken(), firstNum);

                int secondNum = Integer.parseInt(st.nextToken());
                setNumber(st.nextToken(), secondNum);

                sDominoes[firstNum][secondNum] = true;
                sDominoes[secondNum][firstNum] = true;
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= BOARD_SIZE; ++i) {
                setNumber(st.nextToken(), i);
            }

            solveSudominokuRecursive(0);
            saveBoard(testCount, sb);

            resetBoards();
            testCount++;
        }

        System.out.print(sb.toString());
    }

    private static void resetBoards() {
        for (int row = 0; row < BOARD_SIZE; ++row) {
            for (int col = 0; col < BOARD_SIZE; ++col) {
                sBoard[row][col] = 0;
                sDominoes[row + 1][col + 1] = false;
                sRowVisited[row][col + 1] = false;
                sColVisited[row + 1][col] = false;
                sAreaVisited[row][col + 1] = false;
            }
        }
    }

    private static boolean solveSudominokuRecursive(int count) {
        if (count == BOARD_SIZE * BOARD_SIZE) {
            return true;
        }

        int row = count / BOARD_SIZE;
        int col = count % BOARD_SIZE;
        if (sBoard[row][col] != 0) {
            if (solveSudominokuRecursive(count + 1)) {
                return true;
            }

            return false;
        }

        for (int dirIndex = 0; dirIndex < sRowDirs.length; ++dirIndex) {
            int newRow = row + sRowDirs[dirIndex];
            int newCol = col + sColDirs[dirIndex];

            if (newRow < 0 || newRow >= BOARD_SIZE || newCol < 0 || newCol >= BOARD_SIZE) {
                continue;
            }

            if (sBoard[newRow][newCol] != 0) {
                continue;
            }

            for (int firstNum = 1; firstNum <= BOARD_SIZE; ++firstNum) {
                if (!isPlaceable(firstNum, row, col)) {
                    continue;
                }

                setVisited(firstNum, row, col, true);
                sBoard[row][col] = firstNum;

                for (int secondNum = 1; secondNum <= BOARD_SIZE; ++secondNum) {
                    if (sDominoes[firstNum][secondNum]) {
                        continue;
                    }

                    if (!isPlaceable(secondNum, newRow, newCol)) {
                        continue;
                    }

                    setVisited(secondNum, newRow, newCol, true);
                    sBoard[newRow][newCol] = secondNum;
                    sDominoes[firstNum][secondNum] = true;
                    sDominoes[secondNum][firstNum] = true;

                    if (solveSudominokuRecursive(count + 1)) {
                        return true;
                    }

                    sDominoes[firstNum][secondNum] = false;
                    sDominoes[secondNum][firstNum] = false;
                    sBoard[newRow][newCol] = 0;
                    setVisited(secondNum, newRow, newCol, false);
                }

                sBoard[row][col] = 0;
                setVisited(firstNum, row, col, false);
            }
        }

        return false;
    }

    private static int getAreaRow(int row, int col) {
        return (row / 3) * 3 + (col / 3);
    }

    private static boolean isPlaceable(int num, int row, int col) {
        int areaRow = getAreaRow(row, col);
        return !sRowVisited[row][num] && !sColVisited[num][col] && !sAreaVisited[areaRow][num];
    }

    private static void setVisited(int num, int row, int col, boolean state) {
        int areaRow = getAreaRow(row, col);

        sRowVisited[row][num] = state;
        sColVisited[num][col] = state;
        sAreaVisited[areaRow][num] = state;
    }

    private static void saveBoard(final int testCount, final StringBuilder sb) {
        sb.append("Puzzle ")
            .append(testCount)
            .append(System.lineSeparator());

        for (int row = 0; row < BOARD_SIZE; ++row) {
            for (int col = 0; col < BOARD_SIZE; ++col) {
                sb.append(sBoard[row][col]);
            }
            sb.append(System.lineSeparator());
        }
    }

    private static void setNumber(final String location, final int num) {
        int row = location.charAt(0) - 'A';
        int col = location.charAt(1) - '1';

        sBoard[row][col] = num;
        setVisited(num, row, col, true);
    }
}
