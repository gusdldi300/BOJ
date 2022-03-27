package search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class S2667 {
    public static void testCode(String[] args) throws IOException {
        printHouseNumberCounts();
    }

    private static void printHouseNumberCounts() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int mapSize = Integer.parseInt(bf.readLine());

        int[][] houses = new int[mapSize][mapSize];

        for (int row = 0; row < mapSize; ++row) {
            String widthInput = bf.readLine();
            for (int col = 0; col < mapSize; ++col) {
                if (widthInput.charAt(col) == '1') {
                    houses[row][col] = 1;
                } else {
                    houses[row][col] = 0;
                }
            }
        }

        boolean[][] isVisited = new boolean[mapSize][mapSize];
        PriorityQueue<Count> houseCounts = new PriorityQueue<>((Count a, Count b) -> a.getCount() - b.getCount());

        int houseCount = 0;
        for (int row = 0; row < mapSize; ++row) {
            for (int col = 0; col < mapSize; ++col) {
                if (isVisited[row][col] == false && houses[row][col] == 1) {
                    houseCount++;

                    Count counter = new Count(0);

                    dfsRecursive(houses, row, col, counter, isVisited);
                    houseCounts.add(counter);
                }
            }
        }

        System.out.println(houseCount);

        Count count = houseCounts.poll();
        while (count != null) {
            System.out.println(count.getCount());

            count = houseCounts.poll();
        }
    }

    private static void dfsRecursive(final int[][] houses, int row, int col, final Count counter, final boolean[][] isVisited) {
        if (row >= houses.length || col >= houses.length) {
            return;
        }

        if (row < 0 || col < 0) {
            return;
        }

        if (isVisited[row][col] == true) {
            return;
        }

        isVisited[row][col] = true;

        if (houses[row][col] == 0) {
            return;
        }

        counter.addCount();

        // North, South, East, West
        dfsRecursive(houses, row - 1, col, counter, isVisited);
        dfsRecursive(houses, row + 1, col, counter, isVisited);
        dfsRecursive(houses, row, col - 1, counter, isVisited);
        dfsRecursive(houses, row, col + 1, counter, isVisited);
    }

    private static class Count {
        private int count;

        public Count(int count) {
            this.count = count;
        }

        public int getCount() {
            return this.count;
        }

        public void addCount() {
            this.count++;
        }
    }

}
