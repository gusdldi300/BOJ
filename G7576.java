package search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class G7576 {
    public static void testCode(String[] args) throws IOException {
        int dayCount = getDayCount();
        System.out.println(dayCount);
    }

    private static int getDayCount() throws IOException {
        // Need optimization
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = bf.readLine().split(" ");

        int colSize = Integer.parseInt(inputs[0]);
        int rowSize = Integer.parseInt(inputs[1]);

        Tomato[][] tomatoes = new Tomato[rowSize][colSize];
        Queue<Tomato> tomatoQueue = new LinkedList<>();

        int notMaturedCount = 0;
        boolean[][] visited = new boolean[rowSize][colSize];

        for (int row = 0; row < rowSize; ++row) {
            inputs = bf.readLine().split(" ");

            for (int col = 0; col < colSize; ++col) {
                int isMatured = Integer.parseInt(inputs[col]);
                tomatoes[row][col] = new Tomato(row, col, isMatured);

                if (isMatured == 0) {
                    notMaturedCount++;
                } else {
                    if (isMatured == 1) {
                        tomatoQueue.add(tomatoes[row][col]);
                    }

                    visited[row][col] = true;
                }
            }
        }

        if (notMaturedCount == 0) {
            return 0;
        }

        int maturedTomatoCount = tomatoQueue.size();
        int dayCount = 0;
        while (tomatoQueue.isEmpty() == false) {
            if (maturedTomatoCount == 0) {
                dayCount++;

                maturedTomatoCount = tomatoQueue.size();
            }

            Tomato currentTomato = tomatoQueue.poll();
            maturedTomatoCount--;

            int row = currentTomato.getRow();
            int col = currentTomato.getCol();

            if (currentTomato.isMatured() == -1) {
                continue;
            }

            currentTomato.hasMatured();

            // North, South, East, West
            int direction = row - 1;
            if (direction >= 0 && visited[direction][col] == false) {
                tomatoQueue.add(tomatoes[direction][col]);
                visited[direction][col] = true;
            }

            direction = row + 1;
            if (direction < rowSize && visited[direction][col] == false) {
                tomatoQueue.add(tomatoes[direction][col]);
                visited[direction][col] = true;
            }

            direction = col - 1;
            if (direction >= 0 && visited[row][direction] == false) {
                tomatoQueue.add(tomatoes[row][direction]);
                visited[row][direction] = true;
            }

            direction = col + 1;
            if (direction < colSize && visited[row][direction] == false) {
                tomatoQueue.add(tomatoes[row][direction]);
                visited[row][direction] = true;
            }
        }

        for (int row = 0; row < rowSize; ++row) {
            for (int col = 0; col < colSize; ++col) {
                if (tomatoes[row][col].isMatured() == 0) {
                    return -1;
                }
            }
        }

        return dayCount;
    }

    private static class Tomato {
        private int row;
        private int col;

        private int isMatured;

        public Tomato(int row, int col, int isMatured) {
            this.row = row;
            this.col = col;
            this.isMatured = isMatured;
        }

        public int getRow() {
            return this.row;
        }

        public int getCol() {
            return this.col;
        }

        public int isMatured() {
            return this.isMatured;
        }

        public void hasMatured() {
            this.isMatured = 1;
        }
    }
}
