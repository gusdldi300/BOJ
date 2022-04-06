package algorithm.search;

import javax.sound.sampled.Line;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class G13913 {
    private static final int MAX_POSITION = 100000;

    public static void testCode(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = bf.readLine().split(" ");

        int current = Integer.parseInt(inputs[0]);
        int target = Integer.parseInt(inputs[1]);

        printTimeAndMoves(current, target);
    }

    private static void printTimeAndMoves(int current, int target) {

        if (current == target) {
            System.out.println(0);
            System.out.println(current);

            return;
        }

        // Put last position
        int[] visited = new int[MAX_POSITION + 1];
        for (int index = 0; index < MAX_POSITION; ++index) {
            visited[index] = -1;
        }

        LinkedList<Integer> moveQueue = new LinkedList<>();
        moveQueue.add(current);
        visited[current] = current;

        int move = 0;
        int count = 0;

        int neighborCount = 1;
        while (moveQueue.isEmpty() == false) {
            if (neighborCount == 0) {
                count++;

                neighborCount = moveQueue.size();
            }

            move = moveQueue.poll();
            neighborCount--;

            if (move == target) {
                System.out.println(count);

                break;
            }

            addMove(moveQueue, move - 1, move, visited);
            addMove(moveQueue, move + 1, move, visited);
            addMove(moveQueue, move * 2, move, visited);
        }

        moveQueue.clear();
        moveQueue.add(move);
        while (move != current) {
            move = visited[move];

            moveQueue.addFirst(move);
        }

        for (int printMove : moveQueue) {
            System.out.format("%d ", printMove);
        }
    }

    private static void addMove(final LinkedList<Integer> moveQueue, int nextMove, int lastMove, final int[] visited) {
        if (nextMove < 0 || nextMove > MAX_POSITION) {
            return;
        }

        if (visited[nextMove] != -1) {
            return;
        }

        moveQueue.addLast(nextMove);
        visited[nextMove] = lastMove;
    }

}
