package algorithm.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class S11060 {
    public static void testCode(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(bf.readLine());
        String[] inputs = bf.readLine().split(" ");

        int[] jumps = new int[size];
        for (int index = 0; index < size; ++index) {
            jumps[index] = Integer.parseInt(inputs[index]);
        }

        printLeastJumpCount(jumps);
    }

    private static void printLeastJumpCount(final int[] jumps) {
        boolean[] visited = new boolean[jumps.length];

        Queue<Integer> jumpIndexes = new LinkedList<>();
        jumpIndexes.add(0);
        visited[0] = true;

        int jumpCount = 0;
        int neighbors = 1;
        while (jumpIndexes.isEmpty() == false) {
            if (neighbors == 0) {
                jumpCount++;

                neighbors = jumpIndexes.size();
            }

            int jumpIndex = jumpIndexes.poll();
            if (jumpIndex >= visited.length - 1) {
                System.out.println(jumpCount);

                return;
            }
            neighbors--;

            int jump = jumps[jumpIndex];
            for (int index = 0; index < jump; ++index) {
                addJumpIndexes(jumpIndexes, jumpIndex + (index + 1), visited);
            }
        }

        System.out.println(-1);
    }

    private static void addJumpIndexes(final Queue<Integer> jumpIndexes, int jumpIndex, final boolean[] visited) {
        if (jumpIndex < visited.length) {
            if (visited[jumpIndex] == true) {
                return;
            }

            visited[jumpIndex] = true;
        }

        jumpIndexes.add(jumpIndex);
    }
}
