package algorithm.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class S1463 {
    public static void testCode(String[] args) throws IOException {
        int count = getLeastComputation();
        System.out.println(count);
    }

    public static int getLeastComputation() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int number = Integer.parseInt(bf.readLine());

        if (number == 1) {
            return 0;
        }

        if (number == 2 || number == 3) {
            return 1;
        }

        int[] cache = new int[number + 1];
        cache[2] = 1;
        cache[3] = 1;

        // Put least calculation count in the cell
        for (int index = 4; index <= number; ++index) {
            if ((index % 3) == 0) {
                int count = cache[index / 3];
                if ((index % 2) == 0) {
                    count = Math.min(count, cache[index / 2]);
                }

                cache[index] = 1 + Math.min(count, cache[index - 1]);
            } else if ((index % 2) == 0) {
                cache[index] = 1 + Math.min(cache[index / 2], cache[index - 1]);
            } else {
                cache[index] = 1 + cache[index - 1];
            }
        }

        return cache[number];
    }
}


//    public static int getLeastComputation() throws IOException {
//        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
//
//        int number = Integer.parseInt(bf.readLine());
//
//        int count = 0;
//
//        int temp = number;
//
//
//        while (number != 1) {
//            System.out.println(number);
//
//            temp = number % 3;
//
//            if (temp == 0) {
//                number /= 3;
//            } else if (temp == 1) {
//                if (((number / 2) % 2) == 0) {
//                    number /= 2;
//                } else {
//                    number -= 1;
//                }
//            } else {
//                if ((number % 2) == 0) {
//                    number /= 2;
//                } else {
//                    number -= 1;
//                }
//            }
//
//            ++count;
//        }
//
//        return count;
//    }
