package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class G22944 {
    /*
    private static class State {
        private int row;
        private int col;
        private int health;
        private int umbrellaDurability;
        private int moveCount;

        public State(int row, int col, int health, int umbrellaDurability, int moveCount) {
            this.row = row;
            this.col = col;
            this.health = health;
            this.umbrellaDurability = umbrellaDurability;
            this.moveCount = moveCount;
        }
    }

    private static int sMapSize;
    private static int sHealth;
    private static int sUmbrellaDurability;
    private static char[][] sMap;
    private static Queue<State> sStartStateQueue = new LinkedList<>();
    private static Queue<boolean[][]> sVisitedQueue = new LinkedList<>();

    private static int sSafetyZoneRow;
    private static int sSafetyZoneCol;

    private static int sMinMoveCount = Integer.MAX_VALUE;
    private static int[] sRowDirs = new int[] {-1, 0, 1, 0};
    private static int[] sColDirs = new int[] {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sMapSize = Integer.parseInt(st.nextToken());
        sHealth = Integer.parseInt(st.nextToken());
        sUmbrellaDurability = Integer.parseInt(st.nextToken());

        sMap = new char[sMapSize][sMapSize];

        for (int row = 0; row < sMapSize; ++row) {
            String input = br.readLine();
            for (int col = 0; col < sMapSize; ++col) {
                sMap[row][col] = input.charAt(col);

                if (sMap[row][col] == 'S') {
                    sStartStateQueue.add(new State(row, col, sHealth, 0, 0));
                }

                if (sMap[row][col] == 'E') {
                    sSafetyZoneRow = row;
                    sSafetyZoneCol = col;
                }
            }
        }

        boolean[][] visited = new boolean[sMapSize][sMapSize];
        sVisitedQueue.add(visited);

        getMinMoveCountToSafetyZone();
        if (sMinMoveCount == Integer.MAX_VALUE) {
            sMinMoveCount = -1;
        }

        System.out.print(sMinMoveCount);
    }

    private static void getMinMoveCountToSafetyZone() {
        while (!sStartStateQueue.isEmpty()) {
            State startState = sStartStateQueue.poll();
            boolean[][] visited = sVisitedQueue.poll();
            visited[startState.row][startState.col] = true;

            Queue<State> stateQueue = new LinkedList<>();
            stateQueue.add(startState);

            while (!stateQueue.isEmpty()) {
                State state = stateQueue.poll();

                if (state.row == sSafetyZoneRow && state.col == sSafetyZoneCol) {
                    if (state.moveCount < sMinMoveCount) {
                        sMinMoveCount = state.moveCount;
                    }

                    break;
                }

                if (state.health <= 0) {
                    continue;
                }

                for (int dir = 0; dir < sRowDirs.length; ++dir) {
                    int nextRow = state.row + sRowDirs[dir];
                    int nextCol = state.col + sColDirs[dir];

                    if (nextRow < 0 || nextRow >= sMapSize || nextCol < 0 || nextCol >= sMapSize) {
                        continue;
                    }

                    if (visited[nextRow][nextCol]) {
                        continue;
                    }

                    int nextMoveCount = state.moveCount + 1;
                    if (sMap[nextRow][nextCol] == 'U') {
                        sStartStateQueue.add(new State(nextRow, nextCol, state.health, sUmbrellaDurability - 1, nextMoveCount));

                        boolean[][] copied = copyVisited(visited);
                        sVisitedQueue.add(copied);

                        continue;
                    }

                    visited[nextRow][nextCol] = true;
                    State newState;
                    if (state.umbrellaDurability > 0) {
                        newState = new State(nextRow, nextCol, state.health, state.umbrellaDurability - 1, nextMoveCount);
                    } else {
                        newState = new State(nextRow, nextCol, state.health - 1, state.umbrellaDurability, nextMoveCount);
                    }

                    stateQueue.add(newState);
                }
            }
        }
    }

    private static boolean[][] copyVisited(final boolean[][] visited) {
        boolean[][] copied = new boolean[sMapSize][sMapSize];

        for (int row = 0; row < sMapSize; ++row) {
            for (int col = 0; col < sMapSize; ++col) {
                copied[row][col] = visited[row][col];
            }
        }

        return copied;
    }
    */

    private static int sMapSize;
    private static int sHealth;
    private static int sUmbrellaDurability;
    private static Position sStart;
    private static Position sSafetyZone;

    private static int sMinMoveCount = Integer.MAX_VALUE;

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
        StringTokenizer st = new StringTokenizer(br.readLine());

        sMapSize = Integer.parseInt(st.nextToken());
        sHealth = Integer.parseInt(st.nextToken());
        sUmbrellaDurability = Integer.parseInt(st.nextToken());

        HashSet<Position> visited = new HashSet<>();
        ArrayList<Position> umbrellas = new ArrayList<>();

        for (int row = 0; row < sMapSize; ++row) {
            String input = br.readLine();
            for (int col = 0; col < sMapSize; ++col) {
                char state = input.charAt(col);

                if (state == 'S') {
                    sStart = new Position(row, col);
                } else if (state == 'E') {
                    sSafetyZone = new Position(row, col);
                } else if (state == 'U') {
                    umbrellas.add(new Position(row, col));
                }
            }
        }

        getMinMoveCountToSafetyZoneRecursive(0, sStart, sHealth, 0, umbrellas, visited);
        if (sMinMoveCount == Integer.MAX_VALUE) {
            sMinMoveCount = -1;
        }

        System.out.print(sMinMoveCount);
    }

    private static int getDistanceBetween(final Position first, final Position second) {
        return Math.abs(first.row - second.row) + Math.abs(first.col - second.col);
    }

    private static void getMinMoveCountToSafetyZoneRecursive(int count, final Position curPosition, int health, int durability, final ArrayList<Position> umbrellas, final HashSet<Position> visited) {
        int distance = getDistanceBetween(curPosition, sSafetyZone);
        if (distance <= (health + durability)) {
            sMinMoveCount = Math.min(sMinMoveCount, count + distance);

            return;
        }

        for (Position umbrella : umbrellas) {
            if (visited.contains(umbrella)) {
                continue;
            }

            distance = getDistanceBetween(curPosition, umbrella);
            if (distance > (health + durability)) {
                continue;
            }

            int newHealth = health;
            if (distance > durability) {
                newHealth -= (distance - durability - 1);
            }

            visited.add(umbrella);
            getMinMoveCountToSafetyZoneRecursive(count + distance, umbrella, newHealth, sUmbrellaDurability - 1, umbrellas, visited);
            visited.remove(umbrella);
        }
    }
}
