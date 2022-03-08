package algorithm.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 최적화 필요
public class S14501 {
    public static void testCode(String[] args) throws IOException {
        int profit = getMostProfit();
        System.out.println(profit);
    }

    private static int getMostProfit() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int lastDate = Integer.parseInt(bf.readLine());
        Schedule[] schedules = new Schedule[lastDate];

        for (int index = 0; index < lastDate; ++index) {
            String[] input = bf.readLine().split(" ");
            int startDate = index + 1;
            int endDate = Integer.parseInt(input[0]) + startDate;
            int payment = Integer.parseInt(input[1]);

            if (endDate > lastDate + 1) {
                schedules[index] = null;

                continue;
            }

            schedules[index] = new Schedule(index + 1, endDate, payment);
        }

        int[] totalProfits = new int[lastDate];

        for (int dayIndex = 0; dayIndex < lastDate; ++dayIndex) {
            if (schedules[dayIndex] == null) {
                continue;
            }

            int mostProfitIndex = -1;
            for (int scheduleIndex = 0; scheduleIndex < dayIndex; ++scheduleIndex) {
                if (schedules[scheduleIndex] == null) {
                    continue;
                }

                if (schedules[scheduleIndex].getEndDate() <= schedules[dayIndex].getStartDate()) {
                    if (mostProfitIndex == -1) {
                        mostProfitIndex = scheduleIndex;

                        continue;
                    }

                    if (totalProfits[mostProfitIndex] < totalProfits[scheduleIndex]) {
                        mostProfitIndex = scheduleIndex;
                    }
                }
            }

            if (mostProfitIndex == -1) {
                totalProfits[dayIndex] = schedules[dayIndex].getPayment();

                continue;
            }

            totalProfits[dayIndex] = schedules[dayIndex].getPayment() + totalProfits[mostProfitIndex];
        }

        int mostProfit = totalProfits[0];
        for (int index = 1; index < lastDate; ++index) {
            if (mostProfit < totalProfits[index]) {
                mostProfit = totalProfits[index];
            }
        }

        return mostProfit;
    }

    private static class Schedule {
        private int startDate;
        private int endDate;
        private int payment;

        public Schedule(final int startDate, final int endDate, final int payment) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.payment = payment;
        }

        public int getStartDate() {
            return this.startDate;
        }

        public int getEndDate() {
            return this.endDate;
        }

        public int getPayment() {
            return this.payment;
        }
    }
}
