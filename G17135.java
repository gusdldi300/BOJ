package bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class G17135 {
    private static final int ARCHER_SIZE = 3;
    private static final int NOTHING = 0;
    private static final int ENEMY = 1;
    private static int sRowSize;
    private static int sColSize;
    private static int sDistance;
    private static int[][] sMap;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        sRowSize = Integer.parseInt(st.nextToken());
        sColSize = Integer.parseInt(st.nextToken());
        sDistance = Integer.parseInt(st.nextToken());

        sMap = new int[sRowSize][sColSize];
        for (int row = 0; row < sRowSize; ++row) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sColSize; ++col) {
                int state = Integer.parseInt(st.nextToken());
                if (state == 1) {
                    sEnemySize++;

                    sMap[row][col] = state;
                }
            }
        }

        getMostKillCountRecursive(0, 0);
        System.out.print(sMostKillCount);
    }

    private static int sEnemySize;
    private static int sMostKillCount = Integer.MIN_VALUE;
    private static int[] sArcherCols = new int[ARCHER_SIZE];

    private static void getMostKillCountRecursive(int archerCount, int nextCol) {
        if (archerCount == ARCHER_SIZE) {
            int killCount = getKillCountDfs();
            sMostKillCount = Math.max(sMostKillCount, killCount);

            return;
        }

        for (int archerCol = nextCol; archerCol < sColSize; ++archerCol) {
            sArcherCols[archerCount] = archerCol;
            getMostKillCountRecursive(archerCount + 1, archerCol + 1);
        }
    }

    private static int[][] copyMap(final int[][] map) {
        int[][] copied = new int[sRowSize][sColSize];
        for (int row = 0; row < sRowSize; ++row) {
            for (int col = 0; col < sColSize; ++col) {
                copied[row][col] = map[row][col];
            }
        }

        return copied;
    }

    private static int[] sRowDirs = new int[] {0, -1, 0}; // 왼쪽부터
    private static int[] sColDirs = new int[] {-1, 0, 1};

    private static class Position {
        private int row;
        private int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private static int getKillCountDfs() {
        int[][] map = copyMap(sMap);
        int killCount = 0;

        int enemyCount = sEnemySize;
        Queue<Position> enemies = new LinkedList<>();

        while (enemyCount > 0) {

            for (int i = 0; i < ARCHER_SIZE; ++i) {
                Queue<Position> positionQueue = new LinkedList<>();
                positionQueue.add(new Position(sRowSize - 1, sArcherCols[i]));

                boolean[][] visited = new boolean[sRowSize][sColSize];
                while (!positionQueue.isEmpty()) {
                    Position curPosition = positionQueue.remove();

                    if (map[curPosition.row][curPosition.col] == ENEMY) {
                        enemies.add(curPosition);

                        break;
                    }

                    for (int dir = 0; dir < sRowDirs.length; ++dir) {
                        int newRow = curPosition.row + sRowDirs[dir];
                        int newCol = curPosition.col + sColDirs[dir];

                        if (newRow < 0 || newRow >= sRowSize || newCol < 0 || newCol >= sColSize) {
                            continue;
                        }

                        if (visited[newRow][newCol]) {
                            continue;
                        }

                        int curDistance = Math.abs(sRowSize - newRow) + Math.abs(sArcherCols[i] - newCol);
                        if (curDistance > sDistance) {
                            continue;
                        }

                        visited[newRow][newCol] = true;
                        positionQueue.add(new Position(newRow, newCol));
                    }
                }
            }

            while (!enemies.isEmpty()) {
                Position enemy = enemies.remove();
                if (map[enemy.row][enemy.col] != ENEMY) {
                    continue;
                }

                map[enemy.row][enemy.col] = NOTHING;
                ++killCount;

                --enemyCount;
            }

            enemyCount -= moveEnemiesAndGetEliminated(map);
        }

        return killCount;
    }

    private static int moveEnemiesAndGetEliminated(final int[][] map) {
        int disappearCount = 0;

        for (int col = 0; col < sColSize; ++col) {
            if (map[sRowSize - 1][col] == ENEMY) {
                disappearCount++;
                map[sRowSize - 1][col] = NOTHING;
            }
        }

        for (int row = sRowSize - 1; row > 0; --row) {
            for (int col = 0; col < sColSize; ++col) {
                if (map[row - 1][col] == ENEMY) {
                    map[row - 1][col] = NOTHING;
                    map[row][col] = ENEMY;
                }
            }
        }

        return disappearCount;
    }
}
