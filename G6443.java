package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class G6443 {
    private static final int MAX_LENGTH = 26;

    private static int[] sAlphabets = new int[MAX_LENGTH];

    private static int sTestSize;

    private static StringBuilder sStringBuilder = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sTestSize = Integer.parseInt(br.readLine());

        for (int test = 0; test < sTestSize; ++test) {
            String input = br.readLine();
            for (int index = 0; index < input.length(); ++index) {
                sAlphabets[input.charAt(index) - 'a']++;
            }

            printAnagramRecursive(0, new char[input.length()]);

            resetAlphabets();
        }

        System.out.print(sStringBuilder.toString());

        br.close();
    }

    private static void printAnagramRecursive(int index, final char[] newString) {
        if (index == newString.length) {
            for (int i = 0; i < newString.length; ++i) {
                sStringBuilder.append(newString[i]);
            }
            sStringBuilder.append(System.lineSeparator());

            return;
        }

        for (int i = 0; i < MAX_LENGTH; ++i) {
            if (sAlphabets[i] == 0) {
                continue;
            }

            sAlphabets[i]--;
            newString[index] = (char) ('a' + i);
            printAnagramRecursive(index + 1, newString);
            sAlphabets[i]++;
        }
    }

    private static void resetAlphabets() {
        for (int i = 0; i < MAX_LENGTH; ++i) {
            sAlphabets[i] = 0;
        }
    }

}
