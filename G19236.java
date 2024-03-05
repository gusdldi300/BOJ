package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class G19236 {
    private static final int FISH_SIZE = 16;
    private static final int MAP_SIZE = 4;
    private static final int[] sRowDirs = new int[] {-1, -1, 0, 1, 1, 1, 0, -1}; // counterclockwise
    private static final int[] sColDirs = new int[] {0, -1, -1, -1, 0, 1, 1, 1};

    private static final Direction[] sDirections = new Direction[] {Direction.NORTH, Direction.NORTH_WEST, Direction.WEST, Direction.SOUTH_WEST, Direction.SOUTH, Direction.SOUTH_EAST, Direction.EAST, Direction.NORTH_EAST};
    private static final Fish[][] sMap = new Fish[MAP_SIZE][MAP_SIZE];

    private static enum Direction {
        NORTH,
        NORTH_WEST,
        WEST,
        SOUTH_WEST,
        SOUTH,
        SOUTH_EAST,
        EAST,
        NORTH_EAST
    }

    private static class Fish {
        private int row;
        private int col;

        private int num;
        private int dir;
        public Fish(final int row, final int col, final int num, final int dir) {
            this.row = row;
            this.col = col;

            this.num = num;
            this.dir = dir;
       }
    }

    private static Fish[] sFishList = new Fish[FISH_SIZE + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < MAP_SIZE; ++i) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < MAP_SIZE; ++j) {
                int num = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken()) - 1;
                Fish fish = new Fish(i, j, num, dir);
                sFishList[num] = fish;

                sMap[i][j] = fish;
            }
        }

        Fish fish = sMap[0][0];
        sFishList[fish.num] = null;
        Fish shark = new Fish(0, 0, 0, fish.dir);
        sMap[0][0] = shark;

        int sum = fish.num;

        getMostEatableNumSumRecursive(shark, sum, sFishList, sMap);

        System.out.print(sNumSum);
    }

    private static void printMap(final Fish[][] map) {
        for (int i = 0; i < MAP_SIZE; ++i) {
            for (int j = 0; j < MAP_SIZE; ++j) {
                Fish fish = map[i][j];
                if (fish == null) {
                    System.out.format("(null), ");

                    continue;
                }

                System.out.format("(%d, %s), ", fish.num, sDirections[fish.dir]);
            }
            System.out.println();
        }

        System.out.println();
    }

    private static void getMostEatableNumSumRecursive(Fish shark, int sum, final Fish[] fishList, final Fish[][] map) {
        for (int i = 1; i <= FISH_SIZE; ++i) {
            Fish fish = fishList[i];
            if (fish == null) {
                continue;
            }

            boolean bMoved = false;
            for (int j = 0; j < sRowDirs.length; ++j) {
                if (bMoved) {
                    break;
                }

                int nextDir = (fish.dir + j) % sRowDirs.length;

                int nextRow = fish.row + sRowDirs[nextDir];
                int nextCol = fish.col + sColDirs[nextDir];

                if (nextRow < 0 || nextRow >= MAP_SIZE || nextCol < 0 || nextCol >= MAP_SIZE) {
                    continue;
                }

                if (map[nextRow][nextCol] != null) {
                    if (map[nextRow][nextCol].num == shark.num) {
                        continue;
                    }

                    Fish otherFish = map[nextRow][nextCol];

                    otherFish.row = fish.row;
                    otherFish.col = fish.col;

                    map[fish.row][fish.col] = otherFish;
                } else {
                    map[fish.row][fish.col] = null;
                }

                fish.row = nextRow;
                fish.col = nextCol;
                fish.dir = nextDir;

                map[nextRow][nextCol] = fish;
                bMoved = true;

                //printMap(map);
            }
        }
        //System.out.println("fish move complete");

        boolean bEatable = false;

        int nextSharkRow = shark.row;
        int nextSharkCol = shark.col;
        while (true) {
            nextSharkRow += sRowDirs[shark.dir];
            nextSharkCol += sColDirs[shark.dir];

            if (nextSharkRow < 0 || nextSharkRow >= MAP_SIZE || nextSharkCol < 0 || nextSharkCol >= MAP_SIZE) {
                break;
            }

            if (map[nextSharkRow][nextSharkCol] == null) {
                continue;
            }

            Fish[][] copiedMap = new Fish[MAP_SIZE][MAP_SIZE];
            Fish[] copiedList = new Fish[FISH_SIZE + 1];
            copy(copiedMap, map, copiedList);

            bEatable = true;
            Fish eatFish = copiedMap[nextSharkRow][nextSharkCol];
            copiedList[eatFish.num] = null;

            Fish nextShark = copiedList[0];
            copiedMap[nextShark.row][nextShark.col] = null;

            nextShark.row = nextSharkRow;
            nextShark.col = nextSharkCol;
            nextShark.dir = eatFish.dir;
            copiedMap[nextSharkRow][nextSharkCol] = nextShark;

            //System.out.println("shark move");
            //printMap(copiedMap);

            getMostEatableNumSumRecursive(nextShark, sum + eatFish.num, copiedList, copiedMap);
        }

        if (!bEatable) {
            sNumSum = Math.max(sNumSum, sum);
        }
    }

    private static int sNumSum = Integer.MIN_VALUE;
    private static void copy(final Fish[][] outMap, final Fish[][] map, final Fish[] outList) {
        for (int i = 0; i < MAP_SIZE; ++i) {
            for (int j = 0; j < MAP_SIZE; ++j) {
                if (map[i][j] == null) {
                    continue;
                }

                Fish copiedFish = new Fish(i, j, map[i][j].num, map[i][j].dir);
                outMap[i][j] = copiedFish;
                outList[copiedFish.num] = copiedFish;
            }
        }
    }
}
