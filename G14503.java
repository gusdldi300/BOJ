package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class G14503 {
    private static int sRowSize;
    private static int sColSize;

    private static int[] sRowDirs = new int[] {-1, 0, 1, 0};
    private static int[] sColDirs = new int[] {0, 1, 0, -1};
    private static Direction[] sDirections = new Direction[] {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
    private static int[][] sMap;
    private static enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sRowSize = Integer.parseInt(st.nextToken());
        sColSize = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int curRow = Integer.parseInt(st.nextToken());
        int curCol = Integer.parseInt(st.nextToken());
        int dirIndex = Integer.parseInt(st.nextToken());

        sMap = new int[sRowSize][sColSize];
        for (int row = 0; row < sRowSize; ++row) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sColSize; ++col) {
                sMap[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        sRobotQueue.add(new Robot(curRow, curCol, dirIndex));
        cleanFloorsBfs();

        System.out.print(sCleanCount);
    }

    private static void cleanFloorsBfs() {

        while (!sRobotQueue.isEmpty()) {
            Robot robot = sRobotQueue.remove();

            if (sMap[robot.row][robot.col] == 0) {
                ++sCleanCount;
                sMap[robot.row][robot.col] = -1;
            }

            boolean isDirtyFloorFound = false;
            for (int index = 0; index < sRowDirs.length; ++index) {
                int nextRow = robot.row + sRowDirs[index];
                int nextCol = robot.col + sColDirs[index];

                if (!isInMapBound(nextRow, nextCol)) {
                    continue;
                }

                if (sMap[nextRow][nextCol] == 0) {
                    isDirtyFloorFound = true;
                    break;
                }
            }

            int nextRow = 0;
            int nextCol = 0;
            int nextDirIndex = robot.direction;

            if (isDirtyFloorFound) {
                int direction = robot.direction;

                for (int index = 0; index < sRowDirs.length; ++index) {
                    direction--;
                    nextDirIndex = ((sRowDirs.length + direction) % sRowDirs.length);

                    nextRow = robot.row + sRowDirs[nextDirIndex];
                    nextCol = robot.col + sColDirs[nextDirIndex];

                    if (isInMapBound(nextRow, nextCol) && sMap[nextRow][nextCol] == 0) {
                        break;
                    }
                }
            } else {
                int backwardIndex = (robot.direction + 2) % sRowDirs.length;
                nextRow = robot.row + sRowDirs[backwardIndex];
                nextCol = robot.col + sColDirs[backwardIndex];

                if (!isInMapBound(nextRow, nextCol) || sMap[nextRow][nextCol] == 1) {
                    return;
                }
            }

            sRobotQueue.add(new Robot(nextRow, nextCol, nextDirIndex));
        }
    }

    private static boolean isInMapBound(int row, int col) {
        if (row < 0 || row >= sRowSize || col < 0 || col >= sColSize) {
            return false;
        }

        return true;
    }

    private static class Robot {
        private int row;
        private int col;
        private int direction;

        public Robot(int row, int col, int direction) {
            this.row = row;
            this.col = col;
            this.direction = direction;
        }
    }

    private static Queue<Robot> sRobotQueue = new LinkedList<>();
    private static int sCleanCount = 0;
}
