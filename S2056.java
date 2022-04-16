package algorithm.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class S2056 {
    public static void testCode(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int workCount = Integer.parseInt(bf.readLine());
        int[] workDoneTimes = new int[workCount];

        for (int index = 0; index < workCount; ++index) {
            String[] inputs = bf.readLine().split(" ");

            int time = Integer.parseInt(inputs[0]);
            int formerCount = Integer.parseInt(inputs[1]) + 2;

            int mostLastTime = 0;
            for (int inputIndex = 2; inputIndex < formerCount; ++inputIndex) {
                int lastTime = workDoneTimes[Integer.parseInt(inputs[inputIndex]) - 1];

                if (mostLastTime < lastTime) {
                    mostLastTime = lastTime;
                }
            }

            workDoneTimes[index] = time + mostLastTime;
        }

        int leastTime = 0;
        for (int index = 0; index < workCount; ++index) {
            if (leastTime < workDoneTimes[index]) {
                leastTime = workDoneTimes[index];
            }
        }

        System.out.println(leastTime);
    }
}
