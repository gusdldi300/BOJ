package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class G23290 {
    private static class Fish {
        private int row;
        private int col;
        private int dir;

        public Fish(int row, int col, int dir) {
            this.row = row;
            this.col = col;
            this.dir = dir;
        }

        public Fish(final Fish fish) {
            this.row = fish.row;
            this.col = fish.col;
            this.dir = fish.dir;
        }
    }

    private static class Space {
        private Queue<Fish> fishes = new LinkedList<>();
        private int scent;

        public Space() {
        }
    }

    private static final int MAP_SIZE = 4;
    private static int sFishCount;
    private static int sMagicCount;
    private static Space[][] sMap = new Space[MAP_SIZE][MAP_SIZE];
    private static Queue<Fish> sClonedFishes = new LinkedList<>();

    private static int[][] sDirs = new int[][] {
            {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}
    };

    private static final int MAX_SHARK_MOVEMENT = 3;

    private static int[] sSharkDirs = new int[] {2, 0, 6, 4}; // N, W, S, E

    private static int[] sMaxSharkMovement = new int[MAX_SHARK_MOVEMENT];
    private static int sMaxCaughtCount = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int row = 0; row < MAP_SIZE; ++row) {
            for (int col = 0; col < MAP_SIZE; ++col) {
                sMap[row][col] = new Space();
            }
        }

        sFishCount = Integer.parseInt(st.nextToken());
        sMagicCount = Integer.parseInt(st.nextToken());

        for (int i = 0; i < sFishCount; ++i) {
            st = new StringTokenizer(br.readLine());

            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;
            int dir = Integer.parseInt(st.nextToken()) - 1;

            sClonedFishes.add(new Fish(row, col, dir));
        }

        st = new StringTokenizer(br.readLine());
        int sharkRow = Integer.parseInt(st.nextToken()) - 1;
        int sharkCol = Integer.parseInt(st.nextToken()) - 1;

        Fish shark = new Fish(sharkRow, sharkCol, -1);
        for (int i = 0; i < sMagicCount; ++i) {
            int curSize = sClonedFishes.size();
            for (int fishIndex = 0; fishIndex < curSize; ++fishIndex) {
                // copy
                Fish fish = sClonedFishes.remove();
                sClonedFishes.add(new Fish(fish));

                // move
                for (int dirCount = 0; dirCount < sDirs.length; ++dirCount) {
                    int nextDir = (sDirs.length + (fish.dir - dirCount)) % sDirs.length;
                    int nextRow = fish.row + sDirs[nextDir][0];
                    int nextCol = fish.col + sDirs[nextDir][1];

                    if (isMovable(nextRow, nextCol, shark)) {
                        fish.row = nextRow;
                        fish.col = nextCol;
                        fish.dir = nextDir;

                        break;
                    }
                }

                sMap[fish.row][fish.col].fishes.add(fish);
            }

            // move shark
            sMaxCaughtCount = Integer.MIN_VALUE;
            int[] sharkMovement = new int[MAX_SHARK_MOVEMENT];
            getMostFishCaughtSharkMovementRecursive(0, shark, sharkMovement);

            for (int moveIndex = 0; moveIndex < sMaxSharkMovement.length; ++moveIndex) {
                int moveDir = sSharkDirs[sMaxSharkMovement[moveIndex]];
                shark.row += sDirs[moveDir][0];
                shark.col += sDirs[moveDir][1];

                Queue<Fish> curFishes = sMap[shark.row][shark.col].fishes;
                if (curFishes.size() > 0) {
                    curFishes.clear();
                    sMap[shark.row][shark.col].scent = 3;
                }
            }

            // scent gone, clone
            for (int row = 0; row < MAP_SIZE; ++row) {
                for (int col = 0; col < MAP_SIZE; ++col) {
                    Space curSpace = sMap[row][col];

                    if (curSpace.scent > 0) {
                        curSpace.scent--;
                    }

                    while (!curSpace.fishes.isEmpty()) {
                        Fish fish = curSpace.fishes.remove();
                        sClonedFishes.add(fish);
                    }
                }
            }

            //System.out.println(sClonedFishes.size());
        }

        System.out.print(sClonedFishes.size());
    }

    private static int getFishCaughtCount(final Fish shark, final int[] sharkMovement) {
        int caughtCount = 0;

        int nextRow = shark.row;
        int nextCol = shark.col;
        for (int i = 0; i < sharkMovement.length; ++i) {
            int moveDir = sSharkDirs[sharkMovement[i]];

            nextRow += sDirs[moveDir][0];
            nextCol += sDirs[moveDir][1];

            if (nextRow < 0 || nextRow >= MAP_SIZE || nextCol < 0 || nextCol >= MAP_SIZE) {
                return -1;
            }

            if (i > 1 && Math.abs(sharkMovement[i - 1] - sharkMovement[i]) == 2) {
                continue;
            }

            caughtCount += sMap[nextRow][nextCol].fishes.size();
        }

        return caughtCount;
    }

    private static void getMostFishCaughtSharkMovementRecursive(int moveCount, final Fish shark, final int[] sharkMovement) {
        if (moveCount == MAX_SHARK_MOVEMENT) {
            int caughtCount = getFishCaughtCount(shark, sharkMovement);
            if (caughtCount == -1) {
                return;
            }

            if (caughtCount > sMaxCaughtCount) {
                sMaxCaughtCount = caughtCount;
                for (int i = 0; i < MAX_SHARK_MOVEMENT; ++i) {
                    sMaxSharkMovement[i] = sharkMovement[i];
                }
            }

            return;
        }

        for (int moveDir = 0; moveDir < sSharkDirs.length; ++moveDir) {
            sharkMovement[moveCount] = moveDir;
            getMostFishCaughtSharkMovementRecursive(moveCount + 1, shark, sharkMovement);
        }
    }

    private static boolean isMovable(int nextRow, int nextCol, final Fish shark) {
        if (nextRow < 0 || nextRow >= MAP_SIZE || nextCol < 0 || nextCol >= MAP_SIZE) {
            return false;
        }

        if (shark.row == nextRow && shark.col == nextCol) {
            return false;
        }

        if (sMap[nextRow][nextCol].scent > 0) {
            return false;
        }

        return true;
    }
}
