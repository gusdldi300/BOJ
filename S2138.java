package algorithm.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class S2138 {
    public static void testCode(String[] args) throws IOException {
        int count = getMinSwitchCount();

        System.out.println(count);
    }

    private static int getMinSwitchCount() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        int size = Integer.parseInt(bf.readLine());
        boolean[] bulbs = new boolean[size];
        boolean[] targetBulbs = new boolean[size];

        String original = bf.readLine();
        String target = bf.readLine();

        copyBulbsToBoolArray(bulbs, original);
        copyBulbsToBoolArray(targetBulbs, target);

        // Case first switch always ON
        switchBulb(bulbs, 0);
        switchBulb(bulbs, 1);
        printBulbs(bulbs);

        int firstOnCount = switchCount(targetBulbs, bulbs) + 1;
        printBulbs(bulbs);

        boolean isEqual = checkEqual(targetBulbs, bulbs);
        if (isEqual == false) {
            firstOnCount = -1;
        }

        // Case first switch always off
        copyBulbsToBoolArray(bulbs, original);
        int firstOffCount = switchCount(targetBulbs, bulbs);
        printBulbs(bulbs);

        isEqual = checkEqual(targetBulbs, bulbs);
        if (isEqual == false) {
            firstOffCount = -1;
        }

        if (firstOnCount == -1 && firstOffCount == -1) {
            return -1;
        }

        if (firstOnCount == -1) {
            return firstOffCount;
        }

        if (firstOffCount == -1) {
            return firstOnCount;
        }

        return Math.min(firstOnCount, firstOffCount);
    }

    private static void printBulbs(final boolean[] bulbs) {
        for (int index = 0; index < bulbs.length; ++index) {
            System.out.print(bulbs[index] == true ? '0' : '1');
        }
        System.out.println();
    }

    private static void copyBulbsToBoolArray(final boolean[] bulbs, final String stringBulbs) {
        for (int index = 0; index < bulbs.length; ++index) {
            if (stringBulbs.charAt(index) == '0') {
                bulbs[index] = true;

            } else {
                bulbs[index] = false;
            }
        }
    }

    private static int switchCount(final boolean[] targetBulbs, final boolean[] bulbs) {
        int size = targetBulbs.length;
        int count = 0;

        for (int switchIndex = 1; switchIndex < size - 1; ++switchIndex) {
            if (targetBulbs[switchIndex - 1] != bulbs[switchIndex - 1]) {
                count++;
                switchBulb(bulbs, switchIndex - 1);
                switchBulb(bulbs, switchIndex);
                switchBulb(bulbs, switchIndex + 1);
            }
        }

        if (targetBulbs[size - 2] != bulbs[size - 2]) {
            count++;
            switchBulb(bulbs, size - 2);
            switchBulb(bulbs, size - 1);
        }

        return count;
    }

    private static void switchBulb(final boolean[] bulbs, final int index) {
        bulbs[index] = (bulbs[index] == false) ? true : false;
    }

    private static boolean checkEqual(final boolean[] targetBulbs, final boolean[] bulbs) {
        for (int index = 0; index < targetBulbs.length; ++index) {
            if (targetBulbs[index] != bulbs[index]) {
                return false;
            }
        }

        return true;
    }
}
