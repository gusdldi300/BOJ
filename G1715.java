package algorithm.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class G1715 {
    public static void testCode(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(bf.readLine());

        if (count == 1) {
            bf.readLine();
            System.out.println(0);

            return;
        }

        PriorityQueue<Integer> numberHeap = new PriorityQueue<>();
        for (int index = 0; index < count; ++index) {
            numberHeap.add(Integer.parseInt(bf.readLine()));
            //numberHeap.add(1000);
        }

        int answer = 0;
        while (numberHeap.size() > 1) {
            int firstCount = numberHeap.poll();
            int secondCount = numberHeap.poll();

            int totalCount = firstCount + secondCount;
            answer += totalCount;

            numberHeap.add(totalCount);
        }

        System.out.println(answer);
    }
}
