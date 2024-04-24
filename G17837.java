package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class G17837 {
    private static final int END_TURN = 1000;
    private static final int GAME_COMPLETE_PIECE_COUNT = 4;

    private static int sMapSize;
    private static int sPieceCount;
    private static int[][] sMapColor;
    private static LinkedList<Piece>[][] sMap;
    private static Piece[] sPieceOrder;

    private static int[][] sDirs = new int[][] {
            {-1, 0}, {0, 1}, {1, 0}, {0, -1}
    };

    private static class Piece {
        private int seq;
        private int row;
        private int col;
        private int dir;

        public Piece(int seq, int row, int col, int dir) {
            this.seq = seq;
            this.row = row;
            this.col = col;
            this.dir = dir;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sMapSize = Integer.parseInt(st.nextToken());
        sPieceCount = Integer.parseInt(st.nextToken());

        sMapColor = new int[sMapSize][sMapSize];
        sMap = new LinkedList[sMapSize][sMapSize];

        for (int row = 0; row < sMapSize; ++row) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sMapSize; ++col) {
                sMapColor[row][col] = Integer.parseInt(st.nextToken());
                sMap[row][col] = new LinkedList<>();
            }
        }

        sPieceOrder = new Piece[sPieceCount];
        for (int i = 0; i < sPieceCount; ++i) {
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;

            int dir = Integer.parseInt(st.nextToken()) - 1;
            switch (dir) {
                case 0:
                    dir = 1;
                    break;
                case 1:
                    dir = 3;
                    break;
                case 2:
                    dir = 0;
                    break;
                case 3:
                    dir = 2;
                    break;
                default:
                    assert (true);
                    break;
            }

            Piece piece = new Piece(i, row, col, dir);
            sPieceOrder[i] = piece;
            sMap[row][col].add(piece);
        }

        int turn = 1;

        outer:
        while (turn <= END_TURN) {
            for (int i = 0; i < sPieceCount; ++i) {
                Piece piece = sPieceOrder[i];

                int nextRow = piece.row + sDirs[piece.dir][0];
                int nextCol = piece.col + sDirs[piece.dir][1];

                if (isColorBlue(nextRow, nextCol)) {
                    piece.dir = (piece.dir + 2) % sDirs.length;
                    nextRow = piece.row + sDirs[piece.dir][0];
                    nextCol = piece.col + sDirs[piece.dir][1];
                }

                if (isColorBlue(nextRow, nextCol)) {
                    continue;
                }

                int nextColor = sMapColor[nextRow][nextCol];
                LinkedList<Piece> movePieces = sMap[piece.row][piece.col];

                /*
                if (nextColor == 0) {
                    while (!movePieces.isEmpty()) {
                        sMap[nextRow][nextCol].addLast(movePieces.remove());
                    }
                } else {
                    while (!movePieces.isEmpty()) {
                        sMap[nextRow][nextCol].addLast(movePieces.removeFirst());
                    }
                }
                */

                if (nextColor == 0) {
                    int moveSize = movePieces.size();
                    int moveIndex;
                    for (moveIndex = 0; moveIndex < moveSize; ++moveIndex) {
                        Piece movePiece = movePieces.peekFirst();
                        if (movePiece.equals(piece)) {
                            break;
                        }

                        movePiece = movePieces.removeFirst();
                        movePieces.addLast(movePiece);
                    }

                    for (int lastIndex = moveIndex; lastIndex < moveSize; ++lastIndex) {
                        Piece movePiece = movePieces.removeFirst();
                        moveTo(movePiece, nextRow, nextCol);
                    }
                } else {
                    Piece movePiece;
                    while (true) {
                        movePiece = movePieces.peekLast();
                        if (movePiece.equals(piece)) {
                            break;
                        }

                        movePiece = movePieces.removeLast();
                        moveTo(movePiece, nextRow, nextCol);
                    }

                    movePiece = movePieces.removeLast();
                    moveTo(movePiece, nextRow, nextCol);
                }

                if (sMap[nextRow][nextCol].size() >= GAME_COMPLETE_PIECE_COUNT) {
                    break outer;
                }
            }

            ++turn;
        }

        if (turn > END_TURN) {
            turn = -1;
        }

        System.out.print(turn);
    }

    private static void moveTo(final Piece piece, int nextRow, int nextCol) {
        sMap[nextRow][nextCol].addLast(piece);
        piece.row = nextRow;
        piece.col = nextCol;
    }

    private static boolean isColorBlue(int nextRow, int nextCol) {
        if (nextRow < 0 || nextRow >= sMapSize || nextCol < 0 || nextCol >= sMapSize
                || sMapColor[nextRow][nextCol] == 2) {
            return true;
        }

        return false;
    }

}
