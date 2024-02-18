package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G18430 {
    private static int sRowSize;
    private static int sColSize;

    private static int[][] sTree;

    private static int sBiggestSolidity = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sRowSize = Integer.parseInt(st.nextToken());
        sColSize = Integer.parseInt(st.nextToken());
        sTree = new int[sRowSize][sColSize];

        sTotalSize = sRowSize * sColSize;

        for (int row = 0; row < sRowSize; ++row) {
            st = new StringTokenizer(br.readLine());

            for (int col = 0; col < sColSize; ++col) {
                sTree[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        getBiggestSolidityRecursive(0, 0, new boolean[sRowSize][sColSize]);

        System.out.print(sBiggestSolidity);
    }

    private static final int SHAPE_SIZE = 4;
    private static int sTotalSize;

    private static void getBiggestSolidityRecursive(int count, int solidity, final boolean[][] visited) {
        for (int index = count; index < sTotalSize; ++index) {
            int row = index / sColSize;
            int col = index % sColSize;

            if (visited[row][col]) {
                continue;
            }

            for (int shape = 0; shape < SHAPE_SIZE; ++shape) {
                int firstRow = row;
                int lastRow = row;

                int firstCol = col;
                int lastCol = col;

                switch (shape) {
                    case 0:
                        firstCol--;
                        lastRow++;

                        break;
                    case 1:
                        firstRow--;
                        lastCol--;

                        break;
                    case 2:
                        firstRow--;
                        lastCol++;

                        break;
                    case 3:
                        firstCol++;
                        lastRow++;

                        break;
                    default:
                        break;
                }

                if (firstRow < 0 || firstRow >= sRowSize || firstCol < 0 || firstCol >= sColSize) {
                    continue;
                }

                if (lastRow < 0 || lastRow >= sRowSize || lastCol < 0 || lastCol >= sColSize) {
                    continue;
                }

                if (visited[firstRow][firstCol] == true || visited[lastRow][lastCol]) {
                    continue;
                }

                visited[row][col] = true;
                visited[firstRow][firstCol] = true;
                visited[lastRow][lastCol] = true;

                int currentSolidity = (sTree[row][col] * 2) + sTree[firstRow][firstCol] + sTree[lastRow][lastCol];
                getBiggestSolidityRecursive(index + 1, solidity + currentSolidity, visited);

                visited[row][col] = false;
                visited[firstRow][firstCol] = false;
                visited[lastRow][lastCol] = false;
            }
        }

        if (solidity > sBiggestSolidity) {
            sBiggestSolidity = solidity;
        }
    }
}
