package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class G16234 {
    private static class Position {
        private int row;
        private int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private static int sMapSize;
    private static int sMinPopulation;
    private static int sMaxPopulation;
    private static int[][] sMap;
    private static int[] sNewPopulations;
    private static Queue<Position> sPositions = new LinkedList<>();
    private static int[] sRowDirs = new int[] {-1, 0, 1, 0};
    private static int[] sColDirs = new int[] {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sMapSize = Integer.parseInt(st.nextToken());
        sMinPopulation = Integer.parseInt(st.nextToken());
        sMaxPopulation = Integer.parseInt(st.nextToken());

        sMap = new int[sMapSize][sMapSize];
        for (int row = 0; row < sMapSize; ++row) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sMapSize; ++col) {
                sMap[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        sNewPopulations = new int[sMapSize * sMapSize];
        int moveCount = 0;
        while (true) {
            boolean bMoved = false;
            int newPopulationIndex = -1;

            boolean[][] visited = new boolean[sMapSize][sMapSize];
            for (int row = 0; row < sMapSize; ++row) {
                for (int col = 0; col < sMapSize; ++col) {
                    if (visited[row][col]) {
                        continue;
                    }

                    boolean bFound = false;
                    for (int dirIndex = 0; dirIndex < sRowDirs.length; ++dirIndex) {
                        int nextRow = row + sRowDirs[dirIndex];
                        int nextCol = col + sColDirs[dirIndex];

                        if (!isOnBoundary(nextRow, nextCol)) {
                            continue;
                        }

                        int populationDiff = Math.abs(sMap[row][col] - sMap[nextRow][nextCol]);
                        if (populationDiff >= sMinPopulation && populationDiff <= sMaxPopulation) {
                            bFound = true;

                            break;
                        }
                    }

                    if (bFound) {
                        movePopulation(row, col, newPopulationIndex--, visited);
                        bMoved = true;
                    }
                }
            }

            if (!bMoved) {
                break;
            }

            for (int row = 0; row < sMapSize; ++row) {
                for (int col = 0; col < sMapSize; ++col) {
                    if (sMap[row][col] < 0) {
                        sMap[row][col] = sNewPopulations[Math.abs(sMap[row][col])];
                    }
                }
            }

            ++moveCount;
        }

        System.out.print(moveCount);
    }

    private static void movePopulation(int row, int col, int index, final boolean[][] visited) {
        sPositions.add(new Position(row, col));
        visited[row][col] = true;

        int newPopulation = 0;
        int count = 0;
        while (!sPositions.isEmpty()) {
            Position position = sPositions.remove();

            for (int dirIndex = 0; dirIndex < sRowDirs.length; ++dirIndex) {
                int nextRow = position.row + sRowDirs[dirIndex];
                int nextCol = position.col + sColDirs[dirIndex];

                if (!isOnBoundary(nextRow, nextCol)) {
                    continue;
                }

                if (visited[nextRow][nextCol]) {
                    continue;
                }

                int populationDiff = Math.abs(sMap[position.row][position.col] - sMap[nextRow][nextCol]);
                if (populationDiff >= sMinPopulation && populationDiff <= sMaxPopulation) {
                    visited[nextRow][nextCol] = true;
                    sPositions.add(new Position(nextRow, nextCol));
                }
            }

            newPopulation += sMap[position.row][position.col];
            count++;

            sMap[position.row][position.col] = index;
        }

        sNewPopulations[Math.abs(index)] = newPopulation / count;
    }

    private static boolean isOnBoundary(int row, int col) {
        if (row < 0 || row >= sMapSize || col < 0 || col >= sMapSize) {
            return false;
        }

        if (sMap[row][col] < 0) {
            return false;
        }

        return true;
    }
}
