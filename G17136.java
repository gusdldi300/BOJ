package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class G17136 {
    private static final int PASTE_PAPER_SIZE = 5;
    private static final int PASTE_PAPER_COUNT = 5;
    private static final int TARGET_PAPER_SIZE = 10;
    private static boolean[][] sTargetPaper = new boolean[TARGET_PAPER_SIZE][TARGET_PAPER_SIZE];
    private static int[] sPastePapers = new int[PASTE_PAPER_SIZE + 1];
    private static int sMinPasteCount = Integer.MAX_VALUE;

    private static ArrayList<Position> sPastePositions = new ArrayList<>();

    private static class Position {
        private int row;
        private int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int row = 0; row < TARGET_PAPER_SIZE; ++row) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int col = 0; col < TARGET_PAPER_SIZE; ++col) {
                String state = st.nextToken();
                if (state.equals("1")) {
                    sTargetPaper[row][col] = true;
                    sPastePositions.add(new Position(row, col)); //1만 모아서 연산
                }
            }
        }

        for (int pastePaperSize = 1; pastePaperSize < PASTE_PAPER_SIZE + 1; ++pastePaperSize) {
            sPastePapers[pastePaperSize] = PASTE_PAPER_COUNT;
        }

        getMinPasteCountRecursive(0, 0);

        if (sMinPasteCount == Integer.MAX_VALUE) {
            sMinPasteCount = -1;
        }

        System.out.print(sMinPasteCount);
    }

    private static void getMinPasteCountRecursive(int pasteCount, int leftCount) {
        if (leftCount == sPastePositions.size()) {
            sMinPasteCount = Math.min(sMinPasteCount, pasteCount);

            return;
        }

        Position pastePosition = sPastePositions.get(leftCount);
        for (int pasteSize = 1; pasteSize <= PASTE_PAPER_SIZE; ++pasteSize) {
            if (sPastePapers[pasteSize] <= 0) {
                continue;
            }

            if (!sTargetPaper[pastePosition.row][pastePosition.col]) {
                getMinPasteCountRecursive(pasteCount, leftCount + 1);

                return;
            }

            int updateCount = updateTargetPaper(pastePosition.row, pastePosition.col, pasteSize);
            if (updateCount == 0) {
                return;
            }

            sPastePapers[pasteSize]--;
            getMinPasteCountRecursive(pasteCount + 1, leftCount + 1);
            sPastePapers[pasteSize]++;

            updateTo(pastePosition.row, pastePosition.col, pasteSize, true, updateCount);
        }
    }

    private static int updateTargetPaper(final int startRow, final int startCol, final int pasteSize) {
        int rowSize = startRow + pasteSize;
        int colSize = startCol + pasteSize;

        if (rowSize > TARGET_PAPER_SIZE || colSize > TARGET_PAPER_SIZE) {
            return 0;
        }

        int updateCount = 0;
        boolean bPastable = true;
        outer:
        for (int row = startRow; row < rowSize; ++row) {
            for (int col = startCol; col < colSize; ++col) {
                if (!sTargetPaper[row][col]) {
                    bPastable = false;
                    break outer;
                }

                ++updateCount;
                sTargetPaper[row][col] = false;
            }
        }

        if (!bPastable) {
            updateTo(startRow, startCol, pasteSize, true, updateCount);
            updateCount = 0;
        }

        return updateCount;
    }

    private static void updateTo(int startRow, int startCol, int pasteSize, boolean to, int updateCount) {
        int rowSize = startRow + pasteSize;
        int colSize = startCol + pasteSize;

        int count = 0;
        for (int row = startRow; row < rowSize; ++row) {
            for (int col = startCol; col < colSize; ++col) {
                sTargetPaper[row][col] = to;
                ++count;

                if (count == updateCount) {
                    return;
                }
            }
        }
    }
}
