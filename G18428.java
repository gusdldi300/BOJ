package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class G18428 {
    private static class Teacher {
        private int row;
        private int col;

        public Teacher(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    private static final int MAX_OBSTACLE_COUNT = 3;
    private static int sMapSize;
    private static String[][] sMap;
    private static ArrayList<Teacher> sTeachers = new ArrayList<>();
    private static Direction[] sDirections = new Direction[] {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sMapSize = Integer.parseInt(br.readLine());
        sMap = new String[sMapSize][sMapSize];
        for (int row = 0; row < sMapSize; ++row) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sMapSize; ++col) {
                sMap[row][col] = st.nextToken();

                if (sMap[row][col].equals("T")) {
                    sTeachers.add(new Teacher(row, col));
                }
            }
        }

        if (isAvoidableRecursive(0, 0) == false) {
            System.out.print("NO");
        } else {
            System.out.print("YES");
        }
    }

    private static boolean isStudentHid() {
        for (Teacher teacher : sTeachers) {
            for (Direction direction : sDirections) {
                int row = teacher.row;
                int col = teacher.col;

                while (true) {
                    switch (direction) {
                        case UP:
                            --row;
                            break;
                        case RIGHT:
                            ++col;
                            break;
                        case DOWN:
                            ++row;
                            break;
                        case LEFT:
                            --col;
                            break;
                        default:
                            break;
                    }

                    if (row >= sMapSize || row < 0 || col >= sMapSize || col < 0) {
                        break;
                    }

                    if (sMap[row][col].equals("O")) {
                        break;
                    }

                    if (sMap[row][col].equals("S")) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static boolean isAvoidableRecursive(int nextIndex, int obsCount) {
        if (obsCount == MAX_OBSTACLE_COUNT) {
            return isStudentHid();
        }

        final int MAP_SIZE_2D = sMapSize * sMapSize;
        while (nextIndex < MAP_SIZE_2D) {
            int row = nextIndex / sMapSize;
            int col = nextIndex % sMapSize;
            String state = sMap[row][col];

            if (state.equals("X")) {
                sMap[row][col] = "O";
                if (isAvoidableRecursive(nextIndex + 1, obsCount + 1)) {
                    return true;
                }
                sMap[row][col] = "X";
            }

            ++nextIndex;
        }

        return false;
    }
}
