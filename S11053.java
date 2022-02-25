
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class S11053 {
    public static void main(String[] args) throws IOException {
        int count = getLongestSequence();
        System.out.println(count);
    }

    private static int getLongestSequence() throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(bf.readLine());

        int[] sequence = new int[size];

        String numbers = bf.readLine();
        String[] splitNumbers = numbers.split(" ");
        for (int index = 0; index < size; ++index) {
            sequence[index] = Integer.parseInt(splitNumbers[index]);
        }

        if (size == 1) {
            return 1;
        }

        printNumbers(sequence);

        int[] cache = new int[size];
        cache[0] = 1;
        int count = 0;

        for (int index = 1; index < size; ++index) {
            int highestCount = 0;

            for (int checkIndex = index - 1; checkIndex >= 0; --checkIndex) {
                if (sequence[index] > sequence[checkIndex] && highestCount < cache[checkIndex]) {
                    highestCount = cache[checkIndex];
                }
            }

            cache[index] = highestCount + 1;
            if (count < cache[index]) {
                count = cache[index];
            }
        }

        printNumbers(cache);

        return count;
    }

    private static void printNumbers(int[] numbers) {
        for (int index = 0; index < numbers.length; ++index) {
            System.out.print(numbers[index]);
            System.out.print(" ");
        }

        System.out.println();
    }

}
