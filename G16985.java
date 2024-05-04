package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class G16985 {
    private static class Block {
        private int stage;
        private int row;
        private int col;

        public Block(int stage, int row, int col) {
            this.stage = stage;
            this.row = row;
            this.col = col;
        }
    }
    private static final int MAX_MAZE_SIZE = 5;
    private static int[][][] sFirstMaze = new int[MAX_MAZE_SIZE][MAX_MAZE_SIZE][MAX_MAZE_SIZE];
    private static int[][][] sMaze = new int[MAX_MAZE_SIZE][MAX_MAZE_SIZE][MAX_MAZE_SIZE];
    private static int[][] sTemp = new int[MAX_MAZE_SIZE][MAX_MAZE_SIZE];
    private static int sLeastExitMove = Integer.MAX_VALUE;
    private static final int MAX_ROTATE_COUNT = 4;
    private static final int MAX_START_COUNT = 4;
    private static Queue<Block> sBlockQueue = new LinkedList<>();
    private static int[][] sDirs = new int[][] {
            {0, -1, 0}, {0, 0, 1}, {0, 1, 0}, {0, 0, -1}, {1, 0, 0}, {-1, 0, 0}
    };

    private static Block[] sStartBlocks = new Block[] {
        new Block(0, 0, 0),
        new Block(0, 0, MAX_MAZE_SIZE - 1),
        new Block(0, MAX_MAZE_SIZE - 1, 0),
        new Block(0, MAX_MAZE_SIZE - 1, MAX_MAZE_SIZE - 1)
    };

    private static Block[] sExitBlocks = new Block[] {
            new Block(MAX_MAZE_SIZE - 1, MAX_MAZE_SIZE - 1, MAX_MAZE_SIZE - 1),
            new Block(MAX_MAZE_SIZE - 1, MAX_MAZE_SIZE - 1, 0),
            new Block(MAX_MAZE_SIZE - 1, 0, MAX_MAZE_SIZE - 1),
            new Block(MAX_MAZE_SIZE - 1, 0, 0)
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < MAX_MAZE_SIZE; ++i) {
            for (int j = 0; j < MAX_MAZE_SIZE; ++j) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int k = 0; k < MAX_MAZE_SIZE; ++k) {
                    sFirstMaze[i][j][k] = Integer.parseInt(st.nextToken());
                }
            }
        }

        getStagesRecursive(0);

        if (sLeastExitMove == Integer.MAX_VALUE) {
            sLeastExitMove = -1;
        }
        System.out.print(sLeastExitMove);
    }

    private static boolean[] hasPlaced = new boolean[MAX_MAZE_SIZE];
    private static void getStagesRecursive(int count) {
        if (count == MAX_MAZE_SIZE) {
            getLeastExitMoveRecursive(0);

            return;
        }

        for (int stageIndex = 0; stageIndex < MAX_MAZE_SIZE; ++stageIndex) {
            if (hasPlaced[stageIndex]) {
                continue;
            }

            sMaze[count] = sFirstMaze[stageIndex];
            hasPlaced[stageIndex] = true;
            getStagesRecursive(count + 1);
            hasPlaced[stageIndex] = false;
        }
    }

    private static void getLeastExitMoveRecursive(int stage) {
        if (stage == MAX_MAZE_SIZE) {
            for (int i = 0; i < MAX_START_COUNT; ++i) {
                int move = getExitMove(sStartBlocks[i], sExitBlocks[i]);
                sLeastExitMove = Math.min(sLeastExitMove, move);
            }

            return;
        }

        for (int rotateCount = 0; rotateCount < MAX_ROTATE_COUNT; ++rotateCount) {
            rotate90ClockWise(stage);
            getLeastExitMoveRecursive(stage + 1);
        }
    }

    private static int getExitMove(final Block startBlock, final Block exitBlock) {
        if (sMaze[startBlock.stage][startBlock.row][startBlock.col] == 0 || sMaze[exitBlock.stage][exitBlock.row][exitBlock.col] == 0) {
            return Integer.MAX_VALUE;
        }

        boolean[][][] visited = new boolean[MAX_MAZE_SIZE][MAX_MAZE_SIZE][MAX_MAZE_SIZE];
        visited[startBlock.stage][startBlock.row][startBlock.col] = true;
        sBlockQueue.add(startBlock);

        boolean hasFound = false;
        int moveCount = 0;
        outer:
        while (!sBlockQueue.isEmpty()) {
            int curSize = sBlockQueue.size();
            for (int i = 0; i < curSize; ++i) {
                Block block = sBlockQueue.remove();

                if (block.stage == exitBlock.stage && block.row == exitBlock.row && block.col == exitBlock.col) {
                    hasFound = true;
                    break outer;
                }

                for (int dirIndex = 0; dirIndex < sDirs.length; ++dirIndex) {
                    int nextStage = block.stage + sDirs[dirIndex][0];
                    int nextRow = block.row + sDirs[dirIndex][1];
                    int nextCol = block.col + sDirs[dirIndex][2];

                    if (nextStage < 0 || nextStage >= MAX_MAZE_SIZE || nextRow < 0 || nextRow >= MAX_MAZE_SIZE || nextCol < 0 || nextCol >= MAX_MAZE_SIZE) {
                        continue;
                    }

                    if (visited[nextStage][nextRow][nextCol] || sMaze[nextStage][nextRow][nextCol] == 0) {
                        continue;
                    }

                    visited[nextStage][nextRow][nextCol] = true;
                    sBlockQueue.add(new Block(nextStage, nextRow, nextCol));
                }
            }

            ++moveCount;
        }

        sBlockQueue.clear();
        if (!hasFound) {
            return Integer.MAX_VALUE;
        }

        return moveCount;
    }

    private static void rotate90ClockWise(int stage) {
        for (int row = 0; row < MAX_MAZE_SIZE; ++row) {
            for (int col = 0; col < MAX_MAZE_SIZE; ++col) {
                sTemp[col][MAX_MAZE_SIZE - row - 1] = sMaze[stage][row][col];
            }
        }

        int[][] temp = sMaze[stage];
        sMaze[stage] = sTemp;
        sTemp = temp;
    }
}
