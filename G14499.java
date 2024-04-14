package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G14499 {
    private static int[][] sMap;
    private static int sRowSize;
    private static int sColSize;
    private static int sMoveSize;

    private static int[] sRowDirs = new int[] {0, 0, -1 ,1};
    private static int[] sColDirs = new int[] {1, -1, 0, 0};

    private static final int HORIZONTAL_DICE_SIZE = 3;
    private static final int VERTICAL_DICE_SIZE = 4;

    private static final int DICE_VERTICAL_INDEX = 1;
    private static final int DICE_HORIZONTAL_INDEX = 1;

    private static final int DICE_CEIL_INDEX = 3;
    private static final int DICE_FLOOR_INDEX = 1;

    private static int[][] sDice = new int[VERTICAL_DICE_SIZE][HORIZONTAL_DICE_SIZE];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        sRowSize = Integer.parseInt(st.nextToken());
        sColSize = Integer.parseInt(st.nextToken());

        int curRow = Integer.parseInt(st.nextToken());
        int curCol = Integer.parseInt(st.nextToken());
        sMoveSize = Integer.parseInt(st.nextToken());

        sMap = new int[sRowSize][sColSize];
        for (int row = 0; row < sRowSize; ++row) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sColSize; ++col) {
                sMap[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        StringBuilder sb = new StringBuilder();
        st = new StringTokenizer(br.readLine());
        int moveDir;
        for (int i = 0; i < sMoveSize; ++i) {
            moveDir = Integer.parseInt(st.nextToken()) - 1;

            int nextRow = curRow + sRowDirs[moveDir];
            int nextCol = curCol + sColDirs[moveDir];

            if (nextRow < 0 || nextRow >= sRowSize || nextCol < 0 || nextCol >= sColSize) {
                continue;
            }

            int tempNumber;
            switch (moveDir) {
                case 0:
                    tempNumber = sDice[DICE_HORIZONTAL_INDEX][0];
                    for (int j = 0; j < HORIZONTAL_DICE_SIZE - 1; ++j) {
                        sDice[DICE_HORIZONTAL_INDEX][j] = sDice[DICE_HORIZONTAL_INDEX][j + 1];
                    }

                    sDice[DICE_HORIZONTAL_INDEX][HORIZONTAL_DICE_SIZE - 1] = sDice[DICE_CEIL_INDEX][DICE_VERTICAL_INDEX];
                    sDice[DICE_CEIL_INDEX][DICE_VERTICAL_INDEX] = tempNumber;
                    break;
                case 1:
                    tempNumber = sDice[DICE_HORIZONTAL_INDEX][HORIZONTAL_DICE_SIZE - 1];
                    for (int j = HORIZONTAL_DICE_SIZE - 1; j >= 1; --j) {
                        sDice[DICE_HORIZONTAL_INDEX][j] = sDice[DICE_HORIZONTAL_INDEX][j - 1];
                    }

                    sDice[DICE_HORIZONTAL_INDEX][0] = sDice[DICE_CEIL_INDEX][DICE_VERTICAL_INDEX];
                    sDice[DICE_CEIL_INDEX][DICE_VERTICAL_INDEX] = tempNumber;
                    break;
                case 2:
                    tempNumber = sDice[VERTICAL_DICE_SIZE - 1][DICE_VERTICAL_INDEX];
                    for (int j = VERTICAL_DICE_SIZE - 1; j >= 1; --j) {
                        sDice[j][DICE_VERTICAL_INDEX] = sDice[j - 1][DICE_VERTICAL_INDEX];
                    }

                    sDice[0][DICE_VERTICAL_INDEX] = tempNumber;
                    break;
                case 3:
                    tempNumber = sDice[0][DICE_VERTICAL_INDEX];
                    for (int j = 0; j < VERTICAL_DICE_SIZE - 1; ++j) {
                        sDice[j][DICE_VERTICAL_INDEX] = sDice[j + 1][DICE_VERTICAL_INDEX];
                    }

                    sDice[VERTICAL_DICE_SIZE - 1][DICE_VERTICAL_INDEX] = tempNumber;
                    break;
                default:
                    assert (false);
                    break;
            }

            if (sMap[nextRow][nextCol] == 0) {
                sMap[nextRow][nextCol] = sDice[DICE_HORIZONTAL_INDEX][DICE_FLOOR_INDEX];
            } else {
                sDice[DICE_HORIZONTAL_INDEX][DICE_FLOOR_INDEX] = sMap[nextRow][nextCol];
                sMap[nextRow][nextCol] = 0;
            }

            sb.append(sDice[DICE_CEIL_INDEX][DICE_VERTICAL_INDEX])
                .append(System.lineSeparator());

            curRow = nextRow;
            curCol = nextCol;
        }

        System.out.print(sb.toString());
    }
}
