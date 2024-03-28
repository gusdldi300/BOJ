package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G18808 {
    private static final int ROTATE_SIZE = 4;
    private static boolean[][] sNoteBook;
    private static int sNoteBookRowSize;
    private static int sNoteBookColSize;
    private static int sStickerTotalCount;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sNoteBookRowSize = Integer.parseInt(st.nextToken());
        sNoteBookColSize = Integer.parseInt(st.nextToken());
        sStickerTotalCount = Integer.parseInt(st.nextToken());

        sNoteBook = new boolean[sNoteBookRowSize][sNoteBookColSize];
        int stuckTotalCount = 0;
        for (int stickerCount = 0; stickerCount < sStickerTotalCount; ++stickerCount) {
            st = new StringTokenizer(br.readLine());
            int stickerRowSize = Integer.parseInt(st.nextToken());
            int stickerColSize = Integer.parseInt(st.nextToken());

            boolean[][] sticker = new boolean[stickerRowSize][stickerColSize];
            int stuckCount = 0;
            for (int row = 0; row < stickerRowSize; ++row) {
                st = new StringTokenizer(br.readLine());
                for (int col = 0; col < stickerColSize; ++col) {
                    if (st.nextToken().equals("1")) {
                        sticker[row][col] = true;
                        stuckCount++;
                    }
                }
            }

            outer:
            for (int rotate = 0; rotate < ROTATE_SIZE; ++rotate) {
                if (rotate != 0) {
                    sticker = create_90_degree_rotated_sticker(sticker);
                }

                for (int row = 0; row < sNoteBookRowSize; ++row) {
                    for (int col = 0; col < sNoteBookColSize; ++col) {
                        if (isStickable(sticker, row, col)) {
                            stickToNoteBook(sticker, row, col);
                            stuckTotalCount += stuckCount;

                            break outer;
                        }
                    }
                }
            }
        }

        System.out.print(stuckTotalCount);
    }

    private static boolean[][] create_90_degree_rotated_sticker(final boolean[][] src) {
        boolean[][] rotated_sticker = new boolean[src[0].length][src.length];

        for (int row = 0; row < src.length; ++row) {
            for (int col = 0; col < src[0].length; ++col) {
                rotated_sticker[col][rotated_sticker[0].length - row - 1] = src[row][col];
            }
        }

        return rotated_sticker;
    }

    private static void stickToNoteBook(final boolean[][] sticker, int startRow, int startCol) {
        for (int row = 0; row < sticker.length; ++row) {
            for (int col = 0; col < sticker[0].length; ++col) {
                int noteBookRow = startRow + row;
                int noteBookCol = startCol + col;

                if (sticker[row][col] && !sNoteBook[noteBookRow][noteBookCol]) {
                    sNoteBook[noteBookRow][noteBookCol] = true;
                }
            }
        }
    }

    private static boolean isStickable(final boolean[][] sticker, int startRow, int startCol) {
        if (sNoteBookRowSize < (startRow + sticker.length) || sNoteBookColSize < (startCol + sticker[0].length)) {
            return false;
        }

        for (int row = 0; row < sticker.length; ++row) {
            for (int col = 0; col < sticker[0].length; ++col) {
                int noteBookRow = startRow + row;
                int noteBookCol = startCol + col;

                if (sticker[row][col] && sNoteBook[noteBookRow][noteBookCol]) {
                    return false;
                }
            }
        }

        return true;
    }
}
