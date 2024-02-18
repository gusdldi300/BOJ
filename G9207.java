package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class G9207 {
    private static class Pin {
        private int row;
        private int col;

        public Pin(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private static final int ROW_SIZE = 5;
    private static final int COL_SIZE = 9;
    private static char[][] sMap = new char[ROW_SIZE][COL_SIZE];
    private static int sTestSize;
    private static int sMinPinLeft;
    private static int sMinMove;
    private static int[] sRowDirs = new int[] {-1, 0, 1, 0};
    private static int[] sColDirs = new int[] {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sTestSize = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int test = 0; test < sTestSize; ++test) {
            int pinSize = 0;
            for (int row = 0; row < ROW_SIZE; ++row) {
                String input = br.readLine();
                for (int col = 0; col < COL_SIZE; ++col) {
                    sMap[row][col] = input.charAt(col);

                    if (sMap[row][col] == 'o') {
                        ++pinSize;
                    }
                }
            }

            if (sTestSize != 1 && test != (sTestSize - 1)) {
                br.readLine();
            }

            sMinPinLeft = pinSize;
            sMinMove = 0;

            getMinMoveRecursive(sMap, pinSize, 0);
            sb.append(sMinPinLeft);
            sb.append(' ');
            sb.append(sMinMove);
            sb.append(System.lineSeparator());
        }

        System.out.print(sb.toString());
    }

    private static ArrayList<Pin> getPins(final char[][] map) {
        ArrayList<Pin> pins = new ArrayList<>();
        for (int row = 0; row < ROW_SIZE; ++row) {
            for (int col = 0; col < COL_SIZE; ++col) {
                if (map[row][col] == 'o') {
                    pins.add(new Pin(row, col));
                }
            }
        }

        return pins;
    }

    private static boolean isPinRemovable(final char[][] map, int row, int col, int dir) {
        if (row < 0 || row >= map.length || col < 0 || col >= map[0].length) {
            return false;
        }

        if (map[row][col] != 'o') {
            return false;
        }

        int arriveRow = row + sRowDirs[dir];
        int arriveCol = col + sColDirs[dir];
        if (arriveRow < 0 || arriveRow >= map.length || arriveCol < 0 || arriveCol >= map[0].length) {
            return false;
        }

        if (map[arriveRow][arriveCol] != '.') {
            return false;
        }

        return true;
    }

    private static void getMinMoveRecursive(final char[][] map, int pinLeft, int move) {
        char[][] copiedMap = copyMap(map);
        ArrayList<Pin> pins = getPins(copiedMap);
        boolean bRemoved = false;
        for (Pin pin : pins) {
            for (int dir = 0; dir < sRowDirs.length; ++dir) {
                int checkRow = pin.row + sRowDirs[dir];
                int checkCol = pin.col + sColDirs[dir];

                if (isPinRemovable(copiedMap, checkRow, checkCol, dir)) {
                    bRemoved = true;

                    copiedMap[pin.row][pin.col] = '.';
                    copiedMap[checkRow][checkCol] = '.';
                    copiedMap[checkRow + sRowDirs[dir]][checkCol + sColDirs[dir]] = 'o';
                    getMinMoveRecursive(copiedMap, pinLeft - 1, move + 1);
                    copiedMap[pin.row][pin.col] = 'o';
                    copiedMap[checkRow][checkCol] = 'o';
                    copiedMap[checkRow + sRowDirs[dir]][checkCol + sColDirs[dir]] = '.';
                }
            }
        }

        if (bRemoved == false) {
            if (pinLeft == sMinPinLeft) {
                if (move < sMinMove) {
                    sMinMove = move;
                }
            } else if (pinLeft < sMinPinLeft) {
                sMinPinLeft = pinLeft;
                sMinMove = move;
            }
        }
    }

    private static char[][] copyMap(final char[][] map) {
        char[][] copied = new char[ROW_SIZE][COL_SIZE];
        for (int row = 0; row < ROW_SIZE; ++row) {
            for (int col = 0; col < COL_SIZE; ++col) {
                copied[row][col] = map[row][col];
            }
        }

        return copied;
    }
}
