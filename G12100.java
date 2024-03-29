package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G12100 {
    private static final int MAX_MOVE_COUNT = 5;
    private static int sBoardSize;
    private static int[][] sBoard;
    private static int sBiggestBlock = Integer.MIN_VALUE;
    private static boolean[][] sCombined;
    private static Move[] sMoves = new Move[] {Move.UP, Move.RIGHT, Move.DOWN, Move.LEFT};

    private static int[] sRowDirs = new int[] {-1, 0, 1, 0};
    private static int[] sColDirs = new int[] {0, 1, 0, -1};

    private static enum Move {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sBoardSize = Integer.parseInt(br.readLine());
        sBoard = new int[sBoardSize][sBoardSize];
        sCombined = new boolean[sBoardSize][sBoardSize];
        for (int row = 0; row < sBoardSize; ++row) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sBoardSize; ++col) {
                int block = Integer.parseInt(st.nextToken());
                sBoard[row][col] = block;
            }
        }

        getBiggestBlockRecursive(0, sBoard);

        System.out.print(sBiggestBlock);
    }

    private static void getBiggestBlockRecursive(int moveCount, final int[][] board) {
        if (moveCount == MAX_MOVE_COUNT) {
            getBiggestBlock(board);

            return;
        }

        for (int nextMoveIndex = 0; nextMoveIndex < sMoves.length; ++nextMoveIndex) {
            int[][] copiedBoard = copyBoard(board);
            resetCombined();
            // 스캔 방향
            switch (sMoves[nextMoveIndex]) {
                case UP:
                    for (int row = 0; row < sBoardSize; row++) {
                        for (int col = 0; col < sBoardSize; col++) {
                            if (board[row][col] > 0) {
                                moveAndCombine(copiedBoard, row, col, nextMoveIndex);
                            }
                        }
                    }

                    break;
                case RIGHT:
                    for (int col = sBoardSize - 1; col >= 0; col--) {
                        for (int row = 0; row < sBoardSize; row++) {
                            if (board[row][col] > 0) {
                                moveAndCombine(copiedBoard, row, col, nextMoveIndex);
                            }
                        }
                    }

                    break;
                case DOWN:
                    for (int row = sBoardSize - 1; row >= 0; row--) {
                        for (int col = 0; col < sBoardSize; col++) {
                            if (board[row][col] > 0) {
                                moveAndCombine(copiedBoard, row, col, nextMoveIndex);
                            }
                        }
                    }

                    break;
                case LEFT:
                    for (int col = 0; col < sBoardSize; col++) {
                        for (int row = 0; row < sBoardSize; row++) {
                            if (board[row][col] > 0) {
                                moveAndCombine(copiedBoard, row, col, nextMoveIndex);
                            }
                        }
                    }

                    break;
                default:
                    assert (false);
                    break;
            }

            getBiggestBlockRecursive(moveCount + 1, copiedBoard);
        }
    }

    private static void moveAndCombine(final int[][] board, int blockRow, int blockCol, final int moveIndex) {
        int block = board[blockRow][blockCol];
        board[blockRow][blockCol] = 0;

        int nextRow = blockRow + sRowDirs[moveIndex];
        int nextCol = blockCol + sColDirs[moveIndex];

        while (true) {
            if (nextRow < 0 || nextRow >= board.length || nextCol < 0 || nextCol >= board[0].length) {
                break;
            }

            if (board[nextRow][nextCol] > 0) {
                if (block == board[nextRow][nextCol] && !sCombined[nextRow][nextCol]) {
                    board[nextRow][nextCol] = 0;
                    block *= 2;

                    sCombined[nextRow][nextCol] = true;

                    nextRow += sRowDirs[moveIndex];
                    nextCol += sColDirs[moveIndex];
                }

                break;
            }

            nextRow += sRowDirs[moveIndex];
            nextCol += sColDirs[moveIndex];
        }

        board[nextRow - sRowDirs[moveIndex]][nextCol - sColDirs[moveIndex]] = block;
    }

    private static void getBiggestBlock(final int[][] board) {
        for (int row = 0; row < sBoardSize; ++row) {
            for (int col = 0; col < sBoardSize; ++col) {
                if (board[row][col] > sBiggestBlock) {
                    sBiggestBlock = board[row][col];
                }
            }
        }
    }

    private static void resetCombined() {
        for (int row = 0; row < sBoardSize; ++row) {
            for (int col = 0; col < sBoardSize; ++col) {
                sCombined[row][col] = false;
            }
        }
    }

    private static int[][] copyBoard(final int[][] board) {
        int[][] copiedBoard = new int[board.length][board[0].length];

        for (int row = 0; row < sBoardSize; ++row) {
            for (int col = 0; col < sBoardSize; ++col) {
                copiedBoard[row][col] = board[row][col];
            }
        }

        return copiedBoard;
    }

    /*
    private static int MOVE_COUNT = 5;

    private static int sBoardSize;
    private static int[][] sBoard;
    private static Direction[] sDirections = {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};

    private static int sAnswer;

    private static enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    private static boolean[][] sCrashed;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sBoardSize = Integer.parseInt(br.readLine());
        sBoard = new int[sBoardSize][sBoardSize];
        sCrashed = new boolean[sBoardSize][sBoardSize];
        for (int row = 0; row < sBoardSize; ++row) {
            String[] inputs = br.readLine().split(" ");
            for (int col = 0; col < sBoardSize; ++col) {
                int number = Integer.parseInt(inputs[col]);
                if (sAnswer < number) {
                    sAnswer = number;
                }

                sBoard[row][col] = number;
            }
        }

        getBiggestBlockRecursive(0, sBoard);
        System.out.print(sAnswer);
    }

    private static void getBiggestBlockRecursive(int count, int[][] cache) {
        if (count == MOVE_COUNT) {

            return;
        }

        for (Direction direction : sDirections) {
            int[][] copied = new int[sBoardSize][sBoardSize];
            for (int row = 0; row < sBoardSize; ++row) {
                for (int col = 0; col < sBoardSize; ++col) {
                    copied[row][col] = cache[row][col];
                }
            }

            switch (direction) {
                case UP:
                    for (int col = 0; col < sBoardSize; ++col) {
                        int seq = 0;

                        for (int tempRow = 0; tempRow < sBoardSize; ++tempRow) {
                            sCrashed[tempRow][col] = false;
                        }

                        for (int row = 0; row < sBoardSize; ++row) {
                            if (copied[row][col] != 0) {
                                if (seq > 0 && (sCrashed[seq - 1][col] == false)) {
                                    if (copied[row][col] == copied[seq - 1][col]) {
                                        copied[seq - 1][col] *= 2;
                                        copied[row][col] = 0;

                                        if (copied[seq - 1][col] > sAnswer) {
                                            sAnswer = copied[seq - 1][col];
                                        }

                                        sCrashed[seq - 1][col] = true;

                                        continue;
                                    }
                                }

                                int temp = copied[row][col];
                                copied[row][col] = 0;
                                copied[seq][col] = temp;

                                ++seq;
                            }
                        }
                    }

                    break;
                case RIGHT:
                    for (int row = 0; row < sBoardSize; ++row) {
                        int seq = sBoardSize - 1;

                        for (int tempCol = 0; tempCol < sBoardSize; ++tempCol) {
                            sCrashed[row][tempCol] = false;
                        }

                        for (int col = sBoardSize - 1; col >= 0; --col) {
                            if (copied[row][col] != 0) {
                                if (seq < sBoardSize - 1 && (sCrashed[row][seq + 1] == false)) {
                                    if (copied[row][col] == copied[row][seq + 1]) {
                                        copied[row][seq + 1] *= 2;
                                        copied[row][col] = 0;

                                        if (copied[row][seq + 1] > sAnswer) {
                                            sAnswer = copied[row][seq + 1];
                                        }

                                        sCrashed[row][seq + 1] = true;
                                        continue;
                                    }
                                }

                                int temp = copied[row][col];
                                copied[row][col] = 0;
                                copied[row][seq] = temp;

                                --seq;
                            }
                        }
                    }

                    break;
                case DOWN:
                    for (int col = 0; col < sBoardSize; ++col) {
                        int seq = sBoardSize - 1;

                        for (int tempRow = 0; tempRow < sBoardSize; ++tempRow) {
                            sCrashed[tempRow][col] = false;
                        }

                        for (int row = sBoardSize - 1; row >= 0; --row) {
                            if (copied[row][col] != 0) {
                                if (seq < sBoardSize - 1 && (sCrashed[seq + 1][col] == false)) {
                                    if (copied[row][col] == copied[seq + 1][col]) {
                                        copied[seq + 1][col] *= 2;
                                        copied[row][col] = 0;

                                        if (copied[seq + 1][col] > sAnswer) {
                                            sAnswer = copied[seq + 1][col];
                                        }

                                        sCrashed[seq + 1][col] = true;

                                        continue;
                                    }
                                }

                                int temp = copied[row][col];
                                copied[row][col] = 0;
                                copied[seq][col] = temp;

                                --seq;
                            }
                        }
                    }

                    break;
                case LEFT:
                    for (int row = 0; row < sBoardSize; ++row) {
                        int seq = 0;

                        for (int tempCol = 0; tempCol < sBoardSize; ++tempCol) {
                            sCrashed[row][tempCol] = false;
                        }

                        for (int col = 0; col < sBoardSize; ++col) {
                            if (copied[row][col] != 0) {
                                if (seq > 0 && (sCrashed[row][seq - 1] == false)) {
                                    if (copied[row][col] == copied[row][seq - 1]) {
                                        copied[row][seq - 1] *= 2;
                                        copied[row][col] = 0;

                                        if (copied[row][seq - 1] > sAnswer) {
                                            sAnswer = copied[row][seq - 1];
                                        }

                                        sCrashed[row][seq - 1] = true;
                                        continue;
                                    }
                                }

                                int temp = copied[row][col];
                                copied[row][col] = 0;
                                copied[row][seq] = temp;

                                ++seq;
                            }
                        }
                    }

                    break;
                default:
                    break;
            }

            boolean isEqual = true;
            outer:
            for (int row = 0; row < sBoardSize; ++row) {
                for (int col = 0; col < sBoardSize; ++col) {
                    if (copied[row][col] != cache[row][col]) {
                        isEqual = false;
                        break outer;
                    }
                }
            }

            if (isEqual) {
                continue;
            }

            getBiggestBlockRecursive(count + 1, copied);
        }
    }
    */
}
