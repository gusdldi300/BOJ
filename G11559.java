package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class G11559 {
    private static class Puyo {
        private int row;
        private int col;
        private char color;

        public Puyo(int row, int col, char color) {
            this.row = row;
            this.col = col;
            this.color = color;
        }
    }

    private static final int ROW_SIZE = 12;
    private static final int COL_SIZE = 6;
    private static final int EXPLODE_LINKED_COUNT = 4;
    private static char[][] sMap = new char[ROW_SIZE][COL_SIZE];

    private static int[] sRowDirs = new int[] {-1, 0, 1, 0};
    private static int[] sColDirs = new int[] {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int row = 0; row < ROW_SIZE; ++row) {
            String input = br.readLine();
            for (int col = 0; col < COL_SIZE; ++col) {
                sMap[row][col] = input.charAt(col);
            }
        }

        Queue<Puyo> puyos = new LinkedList<>();
        Queue<Puyo> linkedPuyos = new LinkedList<>();

        int explodeCount = 0;
        while (true) {
            boolean bExplode = false;
            boolean[][] isVisited = new boolean[ROW_SIZE][COL_SIZE];

            for (int row = ROW_SIZE - 1; row >= 0; --row) {
                for (int col = 0; col < COL_SIZE; ++col) {
                    if (isVisited[row][col] || sMap[row][col] == '.') {
                        continue;
                    }

                    puyos.add(new Puyo(row, col, sMap[row][col]));
                    isVisited[row][col] = true;
                    getLinkedPuyosBfs(linkedPuyos, puyos, isVisited);

                    if (linkedPuyos.size() >= EXPLODE_LINKED_COUNT) {
                        while (!linkedPuyos.isEmpty()) {
                            Puyo puyo = linkedPuyos.remove();

                            sMap[puyo.row][puyo.col] = '.';
                        }

                        bExplode = true;
                    } else {
                        linkedPuyos.clear();
                    }
                }
            }

            if (!bExplode) {
                break;
            }

            for (int row = ROW_SIZE - 1; row >= 0; --row) {
                for (int col = 0; col < COL_SIZE; ++col) {
                    updateMap(row, col);
                }
            }

            explodeCount++;
        }

        System.out.print(explodeCount);
    }

    private static void getLinkedPuyosBfs(final Queue<Puyo> linkedPuyos, final Queue<Puyo> puyos, final boolean[][] isVisited) {
        while (!puyos.isEmpty()) {
            Puyo puyo = puyos.remove();
            linkedPuyos.add(puyo);

            for (int dirIndex = 0; dirIndex < sRowDirs.length; ++dirIndex) {
                int nextRow = puyo.row + sRowDirs[dirIndex];
                int nextCol = puyo.col + sColDirs[dirIndex];

                if (nextRow < 0 || nextRow >= ROW_SIZE || nextCol < 0 || nextCol >= COL_SIZE) {
                    continue;
                }

                if (isVisited[nextRow][nextCol]) {
                    continue;
                }

                if (sMap[nextRow][nextCol] != puyo.color) {
                    continue;
                }

                isVisited[nextRow][nextCol] = true;
                puyos.add(new Puyo(nextRow, nextCol, sMap[nextRow][nextCol]));
            }
        }
    }

    private static void updateMap(int row, int col) {
        if (row == ROW_SIZE - 1 || sMap[row + 1][col] != '.') {
            return;
        }

        int nextRow;
        for (nextRow = row + 1; nextRow < ROW_SIZE; ++nextRow) {
            if (sMap[nextRow][col] != '.') {
                break;
            }
        }

        sMap[nextRow - 1][col] = sMap[row][col];
        sMap[row][col] = '.';
    }
}
