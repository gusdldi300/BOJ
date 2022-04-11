package algorithm.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class S1654 {
    private static final long MAX_WIRE_LENGTH = (long)Math.pow(2.0, 31.0) - 1;

    public static void testCode(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = bf.readLine().split(" ");

        int wireCount = Integer.parseInt(inputs[0]);
        int target = Integer.parseInt(inputs[1]);

        long[] wires = new long[wireCount];
        for (int index = 0; index < wireCount; ++index) {
            wires[index] = Long.parseLong(bf.readLine());
        }

        printLongestWire(wires, target);
    }

    private static void printLongestWire(final long[] wires, int target) {
        long currentLength = binarySearchLongestWireRecursive(wires, 1, MAX_WIRE_LENGTH, target);
        System.out.println(currentLength);
    }

    private static long binarySearchLongestWireRecursive(final long[] wires, long left, long right, int target) {
        if (left > right) {
            return right;
        }

        long mid = (left + right) / 2;
        int total = 0;
        for (int index = 0; index < wires.length; ++index) {
            total = total + (int)(wires[index] / mid);
        }

        if (total >= target) {
            return binarySearchLongestWireRecursive(wires, mid + 1, right, target);
        } else {
            return binarySearchLongestWireRecursive(wires, left, mid - 1, target);
        }
    }
}
