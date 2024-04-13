package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class G3190 {
    private static class Snake {
        // N, E, S, W
        private int direction;

        // 0
        // R: +1 E (4 + 0 + 1) % 4
        // L: -1 W (4 + 0 - 1) % 4

        // 1
        // R: +1 S (4 + 1 + 1) % 4
        // L: -1 N (4 + 1 - 1) % 4

        // 2 S
        // R: +1 W (4 + 2 + 1) % 4
        // L: -1 E (4 + 2 - 1) % 4

        // 3 W
        // R: +1 N (4 + 3 + 1) % 4
        // L: -1 S (4 + 3 - 1) % 4
        private Position position;
        private Queue<Position> lastPositions = new LinkedList<>();

        public Snake(final Position position, int direction) {
            this.position = position;
            this.direction = direction;
        }
    }

    private static class Position {
        private int row;
        private int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private static class Rotate {
        private int time;
        private int rotate; // L: -1, D: 1

        public Rotate(int time, int rotate) {
            this.time = time;
            this.rotate = rotate;
        }
    }

    private static final int MAX_DIRECTION_COUNT = 4;
    private static int sBoardSize;
    private static int sMaxAppleCount;
    private static int sMaxRotateCount;

    private static char[][] sBoard;
    private static Queue<Rotate> sRotates = new LinkedList<>();
    private static int sCurTime;

    private static int[] sRowDirs = new int[] {-1, 0, 1, 0};
    private static int[] sColDirs = new int[] {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sBoardSize = Integer.parseInt(br.readLine());
        sBoard = new char[sBoardSize][sBoardSize];
        for (int row = 0; row < sBoardSize; ++row) {
            for (int col = 0; col < sBoardSize; ++col) {
                sBoard[row][col] = '.';
            }
        }

        sMaxAppleCount = Integer.parseInt(br.readLine());
        String[] inputs;
        for (int i = 0; i < sMaxAppleCount; ++i) {
            inputs = br.readLine().split(" ");

            int appleRow = Integer.parseInt(inputs[0]) - 1;
            int appleCol = Integer.parseInt(inputs[1]) - 1;

            sBoard[appleRow][appleCol] = 'a';
        }

        sMaxRotateCount = Integer.parseInt(br.readLine());
        for (int i = 0; i < sMaxRotateCount; ++i) {
            inputs = br.readLine().split(" ");

            int time = Integer.parseInt(inputs[0]);
            int rotate = inputs[1].equals("D") ? 1 : -1;

            sRotates.add(new Rotate(time, rotate));
        }

        Position position = new Position(0, 0);
        Snake snake = new Snake(position, 1);
        snake.lastPositions.add(position);
        sBoard[0][0] = 's';

        while (true) {
            if (!sRotates.isEmpty() && sCurTime == sRotates.peek().time) {
                Rotate rotate = sRotates.remove();
                snake.direction = (MAX_DIRECTION_COUNT + snake.direction + rotate.rotate) % MAX_DIRECTION_COUNT;
            }

            ++sCurTime;
            int nextRow = snake.position.row + sRowDirs[snake.direction];
            int nextCol = snake.position.col + sColDirs[snake.direction];

            if (nextRow < 0 || nextRow >= sBoardSize || nextCol < 0 || nextCol >= sBoardSize) {
                break;
            }

            if (sBoard[nextRow][nextCol] == 's') {
                break;
            }

            Position nextPosition = new Position(nextRow, nextCol);
            snake.lastPositions.add(nextPosition);
            if (sBoard[nextRow][nextCol] != 'a') {
                Position lastPosition = snake.lastPositions.remove();
                sBoard[lastPosition.row][lastPosition.col] = '.';
            }

            snake.position = nextPosition;
            sBoard[nextRow][nextCol] = 's';
        }

        System.out.print(sCurTime);
    }
}
