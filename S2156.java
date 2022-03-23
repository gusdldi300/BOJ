package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class S2156 {
    public static void testCode(String[] args) throws IOException {
        int wineAmount = getMostWineAmount();
        System.out.println(wineAmount);

        //int answer = getMostWineAmountR();
        //System.out.println(answer);

    }

    private static int getMostWineAmount() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int countWine = Integer.parseInt(bf.readLine());

        int[] wineAmounts = new int[countWine];
        for (int index = 0; index < countWine; ++index) {
            wineAmounts[index] = Integer.parseInt(bf.readLine());
        }

        if (countWine == 1) {
            return wineAmounts[0];
        }

        if (countWine == 2) {
            return wineAmounts[0] + wineAmounts[1];
        }

        int[][] cache = new int[countWine][2];
        cache[0][0] = wineAmounts[0];
        cache[0][1] = wineAmounts[0];
        cache[1][0] = wineAmounts[1];
        cache[1][1] = wineAmounts[0] + wineAmounts[1];
        cache[2][0] = wineAmounts[0] + wineAmounts[2];
        cache[2][1] = cache[1][0] + wineAmounts[2];

        for (int wineIndex = 3; wineIndex < countWine; ++wineIndex) {
            int firstAmount = Math.max(cache[wineIndex - 2][0], cache[wineIndex - 2][1]);
            firstAmount = Math.max(firstAmount, cache[wineIndex - 3][1]);

            cache[wineIndex][0] =  firstAmount + wineAmounts[wineIndex];
            cache[wineIndex][1] = cache[wineIndex - 1][0] + wineAmounts[wineIndex];
        }

        int biggestAmount = cache[countWine - 2][1];
        int compareAmount = Math.max(cache[countWine - 1][0], cache[countWine - 1][1]);
        biggestAmount = Math.max(biggestAmount, compareAmount);

        return biggestAmount;
    }

    public static int getMostWineAmountR() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int countWine = Integer.parseInt(bf.readLine());

        int[] wineAmounts = new int[countWine];
        for (int index = 0; index < countWine; ++index) {
            wineAmounts[index] = Integer.parseInt(bf.readLine());
        }

        if (countWine == 1) {
            return wineAmounts[0];
        }

        if (countWine == 2) {
            return wineAmounts[0] + wineAmounts[1];
        }

        int[][] cache = new int[2][countWine];

        int biggestAmount = 0;
        for (int index = 0; index < 2; ++index) {
            int totalAmount = getMostWineAmountRecursive(wineAmounts, index, cache, 1);

            if (biggestAmount < totalAmount) {
                biggestAmount = totalAmount;
            }
        }

        return biggestAmount;
    }

    private static int getMostWineAmountRecursive(int[] wineAmounts, int index, int[][] cache, int count) {
        if (index >= wineAmounts.length) {
            return 0;
        }

        if (count == 3) {
            return 0;
        }

        if (cache[count - 1][index] != 0) {
            return cache[count - 1][index];
        }

        int biggestAmount = getMostWineAmountRecursive(wineAmounts, index + 1, cache, count + 1);
        int length = Math.min(index + 5, wineAmounts.length);
        for (int wineIndex = index + 2; wineIndex < length; ++wineIndex) {
            int totalAmount = getMostWineAmountRecursive(wineAmounts, wineIndex, cache, 1);

            if (biggestAmount < totalAmount) {
                biggestAmount = totalAmount;
            }
        }
        biggestAmount += wineAmounts[index];
        cache[count - 1][index] = biggestAmount;

        return biggestAmount;
    }
}
