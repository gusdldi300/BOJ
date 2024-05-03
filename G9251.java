package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class G9251 {
    private static int[][] sSubSeqCounts;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String firstString = br.readLine();
        String secondString = br.readLine();
        sSubSeqCounts = new int[secondString.length()][firstString.length()];

        if (secondString.charAt(0) == firstString.charAt(0)) {
            sSubSeqCounts[0][0] = 1;
        }

        char firstChar = secondString.charAt(0);
        for (int i = 1; i < firstString.length(); ++i) {
            if (firstChar == firstString.charAt(i)) {
                sSubSeqCounts[0][i] = 1;
            } else {
                sSubSeqCounts[0][i] = Math.max(sSubSeqCounts[0][i - 1], sSubSeqCounts[0][i]);
            }
        }

        firstChar = firstString.charAt(0);
        for (int i = 1; i < secondString.length(); ++i) {
            if (firstChar == secondString.charAt(i)) {
                sSubSeqCounts[i][0] = 1;
            } else {
                sSubSeqCounts[i][0] = Math.max(sSubSeqCounts[i - 1][0], sSubSeqCounts[i][0]);
            }
        }

        for (int i = 1; i < secondString.length(); ++i) {
            for (int j = 1; j < firstString.length(); ++j) {
                char curChar = secondString.charAt(i);
                if (curChar == firstString.charAt(j)) {
                    sSubSeqCounts[i][j] = sSubSeqCounts[i - 1][j - 1] + 1;
                } else {
                    sSubSeqCounts[i][j] = Math.max(sSubSeqCounts[i][j - 1], sSubSeqCounts[i - 1][j]);
                }
            }
        }

        System.out.print(sSubSeqCounts[secondString.length() - 1][firstString.length() - 1]);
    }
}
