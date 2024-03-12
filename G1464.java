package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class G1464 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] string = br.readLine().toCharArray();

        boolean bAscend = true;
        char lastChar = string[0];
        for (int index = 1; index < string.length; ++index) {
            char currentChar = string[index];
            if (bAscend == true) {
                if ((lastChar > currentChar) && (string[0] >= currentChar)) {
                    reverseString(string, index - 1);
                    bAscend = false;
                }
            } else {
                if (lastChar < currentChar) {
                    reverseString(string, index - 1);
                    bAscend = true;
                }
            }

            lastChar = currentChar;
        }

        if (bAscend == false) {
            reverseString(string, string.length - 1);
        }

        System.out.println(string);
    }

    private static void reverseString(char[] string, int end) {
        int left = 0;
        int right = end;
        while (left < right) {
            char temp = string[left];
            string[left] = string[right];
            string[right] = temp;

            ++left;
            --right;
        }
    }
}
