package algorithm.greedy;

import java.util.Scanner;

public class S11047 {
    public static void testCode() {
        int minimumCount = getMinimumCoins();
        System.out.println(minimumCount);
    }

    private static int getMinimumCoins() {
        Scanner scanner = new Scanner(System.in);
        final int coinTypesCount = scanner.nextInt();
        int targetPrice = scanner.nextInt();

        assert (coinTypesCount >= 1 && coinTypesCount <= 10);
        assert (targetPrice >= 1 && targetPrice <= 100000000);

        int[] coinTypes = new int[coinTypesCount];

        for (int index = 0; index < coinTypesCount; ++index) {
            coinTypes[index] = scanner.nextInt();
        }

        quickSortDescending(coinTypes);

        int minimumCoinsCount = 0;
        int index = 0;

        while (targetPrice > 0) {
            int coinType = coinTypes[index];

            if ((targetPrice - coinType) < 0) {
                ++index;

                continue;
            }

            while (targetPrice >= coinType) {
                targetPrice -= coinType;

                ++minimumCoinsCount;
            }

            ++index;
        }

        return minimumCoinsCount;
    }

    private static void quickSortDescending(final int[] coinTypes) {
        assert (coinTypes != null);

        quickSortDescendingRecursive(coinTypes, 0, coinTypes.length - 1);
    }

    private static void quickSortDescendingRecursive(final int[] coinTypes, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) {
            return;
        }

        int pivotIndex = partitionDescending(coinTypes, leftIndex, rightIndex);

        quickSortDescendingRecursive(coinTypes, 0, pivotIndex - 1);
        quickSortDescendingRecursive(coinTypes, pivotIndex + 1, rightIndex);
    }

    private static int partitionDescending(final int[] coinTypes, int leftIndex, int rightIndex) {
        int pivot = coinTypes[rightIndex];

        int compareIndex = leftIndex;
        for (int index = compareIndex; index < rightIndex; ++index) {
            if (coinTypes[index] > pivot) {
                swap(compareIndex, index, coinTypes);

                ++compareIndex;
            }
        }

        swap(compareIndex, rightIndex, coinTypes);

        return compareIndex;
    }

    private static void swap(final int firstIndex, final int secondIndex, final int[] coinTypes) {
        int temp = coinTypes[firstIndex];
        coinTypes[firstIndex] = coinTypes[secondIndex];
        coinTypes[secondIndex] = temp;
    }

}
