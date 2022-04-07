package search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class G14226 {
    private static final int MAX_EMOTICON_COUNT = 1000;

    public static void testCode(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int target = Integer.parseInt(bf.readLine());

        printLeastTime(target);
    }

    private static void printLeastTime(int target) {
        if (target == 2) {
            System.out.println(2);

            return;
        }

        Queue<Integer> counts = new LinkedList<>();
        Queue<Integer> clips = new LinkedList<>();
        counts.add(2);
        clips.add(1);

        boolean[][] cache = new boolean[MAX_EMOTICON_COUNT + 1][MAX_EMOTICON_COUNT + 1];
        cache[1][0] = true;
        cache[2][1] = true;

        int seconds = 2;
        int neighbors = 1;
        while (counts.isEmpty() == false) {
            if (neighbors == 0) {
                seconds++;

                neighbors = counts.size();
            }

            int count = counts.poll();
            int clip = clips.poll();
            if (count == target) {
                break;
            }

            neighbors--;

            addCount(counts, clips, count, count, cache);
            addCount(counts, clips, count + clip, clip, cache);
            addCount(counts, clips, count - 1, clip, cache);

        }

        System.out.println(seconds);
    }

    private static void addCount(final Queue<Integer> counts, final Queue<Integer> clips, int count, int clip, final boolean[][] cache) {
        if (count < 1 || count > MAX_EMOTICON_COUNT) {
            return;
        }

        if (cache[count][clip] == true) {
            return;
        }

        cache[count][clip] = true;
        counts.add(count);
        clips.add(clip);
    }

}
