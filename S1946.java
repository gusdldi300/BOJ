package algorithm.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class S1946 {
    public static void testCode(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        int testCaseSize = Integer.parseInt(bf.readLine());
        int[] answers = new int[testCaseSize];
        for (int testCaseCount = 0; testCaseCount < testCaseSize; ++testCaseCount) {
            int workerSize = Integer.parseInt(bf.readLine());

            Worker[] workers = new Worker[workerSize];
            for (int workerCount = 0; workerCount < workerSize; ++workerCount) {
                String[] inputs = bf.readLine().split(" ");
                workers[workerCount] = new Worker(Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]));
            }

            answers[testCaseCount] = getMostWorkerCount(workers);
        }

        for (int index = 0; index < testCaseSize; ++index) {
            System.out.println(answers[index]);
        }
    }

    private static int getMostWorkerCount(final Worker[] workers) {
        Arrays.sort(workers, (Worker a, Worker b) -> a.getPaperRank() - b.getPaperRank());

        int count = 1;
        Worker hiredWorker = workers[0];

        for (int index = 1; index < workers.length; ++index) {
            if (workers[index].getInterviewRank() < hiredWorker.getInterviewRank()) {
                ++count;

                hiredWorker = workers[index];
            }
        }

        return count;
    }

    private static class Worker {
        private int paperRank;
        private int interviewRank;

        public Worker(int paperRank, int interviewRank) {
            this.paperRank = paperRank;
            this.interviewRank = interviewRank;
        }

        public int getPaperRank() {
            return this.paperRank;
        }

        public int getInterviewRank() {
            return this.interviewRank;
        }
    }
}
