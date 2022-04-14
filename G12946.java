package search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class G12946 {
    private static final int[] hexagonRow = {-1, -1, 0, 1, 1, 0};
    private static final int[] hexagonCol = {0, 1, 1, 0, -1, -1};
    private static final int MAX_COLOR_COUNT = 3;

    public static void testCode(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(bf.readLine());

        String[] hexagonBoard = new String[size];
        for (int index = 0; index < size; ++index) {
            hexagonBoard[index] = bf.readLine();
        }

        printLeastCount(hexagonBoard);
    }

    private static void printLeastCount(final String[] hexagonBoard) {
        int size = hexagonBoard.length;
        int[][] colors = new int[size][size];

        int count = 0;
        int mostCount = 0;
        outer:
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                if (hexagonBoard[row].charAt(col) == '-') {
                    continue;
                }

                if (colors[row][col] != 0) {
                    continue;
                }

                count = getUsedColorCount(hexagonBoard, row, col, colors);
                if (count == 3) {
                    mostCount = 3;

                    break outer;
                }

                if (mostCount < count) {
                    mostCount = count;
                }
            }
        }

        System.out.println(mostCount);
    }

    private static int getUsedColorCount(final String[] hexagonBoard, int row, int col, final int[][] colors) {
        Stack<Integer> rowStack = new Stack<>();
        Stack<Integer> colStack = new Stack<>();

        Queue<Integer> rowQueue = new LinkedList<>();
        Queue<Integer> colQueue = new LinkedList<>();

        rowStack.add(row);
        colStack.add(col);

        colors[row][col] = 1;

        int count = 0;
        int colorCount = 0;
        while (rowStack.isEmpty() == false) {
            int currentRow = rowStack.pop();
            int currentCol = colStack.pop();
            if (colors[currentRow][currentCol] == 3) {
                return 3;
            }

            for (int index = 0; index < hexagonRow.length; ++index) {
                int nextRow = currentRow + hexagonRow[index];
                int nextCol = currentCol + hexagonCol[index];

                addBlock(hexagonBoard, rowQueue, colQueue, rowStack, colStack, nextRow, nextCol, colors);
            }
            count = rowQueue.size();

            if (count > 2) {
                return 3;
            }

            if (count == 2) {
                int checkRow = rowQueue.poll();
                int checkCol = colQueue.poll();

                int nextRow = rowQueue.poll();
                int nextCol = colQueue.poll();

                for (int index = 0; index < hexagonRow.length; ++index) {
                    if (checkRow + hexagonRow[index] == nextRow && checkCol + hexagonCol[index] == nextCol) {
                        return 3;
                    }
                }

                count = 1;
            }

            rowQueue.poll();
            colQueue.poll();

            if (colorCount < count) {
                colorCount = count;
            }
        }

        return colorCount + 1;
    }

    private static void addBlock(final String[] hexagonBoard, final Queue<Integer> rowQueue, final Queue<Integer> colQueue, final Stack<Integer> rowStack, final Stack<Integer> colStack, int row, int col, final int[][] colors) {
        if (row < 0 || row >= hexagonBoard.length) {
            return;
        }

        if (col < 0 || col >= hexagonBoard.length) {
            return;
        }

        if (hexagonBoard[row].charAt(col) == '-') {
            return;
        }

        if (colors[row][col] != 0) {
            return;
        }

        boolean[] selectColor = new boolean[MAX_COLOR_COUNT + 1];
        selectColor[0] = true;

        for (int index = 0; index < hexagonRow.length; ++index) {
            int nextRow = row + hexagonRow[index];
            int nextCol = col + hexagonCol[index];

            if (nextRow < 0 || nextRow >= hexagonBoard.length) {
                continue;
            }

            if (nextCol < 0 || nextCol >= hexagonBoard.length) {
                continue;
            }

            if (hexagonBoard[nextRow].charAt(nextCol) == '-') {
                continue;
            }

            int color = colors[nextRow][nextCol];
            if (color == 0) {
                continue;
            }

            selectColor[color] = true;
        }

        for (int color = 1; color <= MAX_COLOR_COUNT; ++color) {
            if (selectColor[color] == false) {
                colors[row][col] = color;

                break;
            }
        }

        rowQueue.add(row);
        colQueue.add(col);

        rowStack.push(row);
        colStack.push(col);
    }
}

