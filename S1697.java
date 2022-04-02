package search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class S1697 {
    private final static int MAX_POSITION = 100000;

    public static void testCode(String[] args) throws IOException {
        int seconds = getShortestSeconds();
        System.out.println(seconds);
    }

    private static int getShortestSeconds() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = bf.readLine().split(" ");

        int position = Integer.parseInt(inputs[0]);
        int target = Integer.parseInt(inputs[1]);

        if (position == target) {
            return 0;
        }


        boolean[] cache = new boolean[MAX_POSITION + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(position);

        int seconds = 0;
        int depthSize = 1;
        while (queue.isEmpty() == false) {
            if (depthSize == 0) {
                depthSize = queue.size();

                seconds++;
            }

            int currentMove = queue.poll();
            depthSize--;

            if (currentMove == target) {
                return seconds;
            }

            addMove(queue, currentMove - 1, cache);
            addMove(queue, currentMove + 1, cache);
            addMove(queue,currentMove * 2, cache);
        }

        return -1;
    }

    private static void addMove(final Queue<Integer> queue, int move, final boolean[] cache) {
        if (move < 0 || move > MAX_POSITION) {
            return;
        }

        if (cache[move] == true) {
            return;
        }

        cache[move] = true;

        queue.add(move);
    }

}
