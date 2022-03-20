package algorithm.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class G1744 {
    public static void testCode(String[] args) throws IOException {
        int total = getBiggestTotal();
        System.out.println(total);
    }

    private static int getBiggestTotal() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(bf.readLine());

        int[] numbers = new int[count];
        int number;
        int invertIndex = -1;

        for (int index = 0; index < count; ++index) {
            number = Integer.parseInt(bf.readLine());
            if (number == 0) {
                invertIndex = index;
            }

            numbers[index] = number;
        }

        Arrays.sort(numbers);

        int total = 0;
        int lastNumber = 0;

        int mulCount = 0;
        int index = 0;

        // Minus numbers and zero
        while (index < count) {
            if (numbers[index] > 0) {
                break;
            }

            ++mulCount;
            if (mulCount == 2) {
                mulCount = 0;
                total += (lastNumber * numbers[index]);

                lastNumber = 0;
                ++index;

                continue;
            }

            lastNumber = numbers[index];
            ++index;
        }

        // last minus number or zero
        total += lastNumber;

        if (index == count) {
            return total;
        }

        while (index < count) {
            if (numbers[index] != 1) {
                break;
            }

            total += numbers[index];
            ++index;
        }

        // Plus numbers
        int plusIndex = count - 1;
        mulCount = 0;
        lastNumber = 0;
        while (plusIndex >= index) {
            ++mulCount;
            if (mulCount == 2) {
                mulCount = 0;
                total += (numbers[plusIndex] * lastNumber);

                lastNumber = 0;
                --plusIndex;

                continue;
            }

            lastNumber = numbers[plusIndex];

            --plusIndex;
        }
        total += lastNumber;

        return total;
    }

}
