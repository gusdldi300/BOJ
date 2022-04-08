package algorithm.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class G13549 {
    private static final int MAX_POSITION = 100000;

    public static void testCode(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = bf.readLine().split(" ");

        int current = Integer.parseInt(inputs[0]);
        int target = Integer.parseInt(inputs[1]);

        printLeastTime(current, target);
    }

    private static void printLeastTime(final int current, final int target) {
        if (current == target) {
            System.out.println(0);

            return;
        }

        boolean visited[] = new boolean[MAX_POSITION + 1];

        Queue<Integer> positions = new LinkedList<>();
        positions.add(current);
        visited[current] = true;
        addTeleportPositionsRecursive(positions, current * 2, target, visited);

        int seconds = 0;
        int neighbors = positions.size();
        while (positions.isEmpty() == false) {
            if (neighbors == 0) {
                ++seconds;

                neighbors = positions.size();
            }

            int position = positions.poll();
            if (position == target) {
                break;
            }

            --neighbors;
            addMovePositions(positions, position - 1, target, visited);
            addMovePositions(positions, position + 1, target, visited);
        }

        System.out.println(seconds);
    }

    private static void addMovePositions(final Queue<Integer> positions, int position, final int target, final boolean[] visited) {
        if (position < 0 || position > MAX_POSITION) {
            return;
        }

        if (visited[position] == true) {
            return;
        }

        positions.add(position);
        visited[position] = true;

        addTeleportPositionsRecursive(positions, position * 2, target, visited);
    }

    private static void addTeleportPositionsRecursive(final Queue<Integer> positions, int position, final int target, final boolean[] visited) {
        if (position < 0 || position > MAX_POSITION) {
            return;
        }

        if (position >= (target * 2)) {
            return;
        }

        if (visited[position] == true) {
            return;
        }

        positions.add(position);
        visited[position] = true;
        addTeleportPositionsRecursive(positions, position * 2, target, visited);
    }
}
