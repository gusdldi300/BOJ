package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

public class S1342 {
    //private static boolean[] sVisited;
    private static String sString;
    //private static char[] sCharArray;
    private static int sAnswer;
    private static HashMap<Character, Integer> sAlphas = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sString = br.readLine();
        //sVisited = new boolean[sString.length()];
        //sCharArray = new char[sString.length()];

        for (int i = 0; i < sString.length(); ++i) {
            char alpha = sString.charAt(i);
            int count = sAlphas.containsKey(alpha) ? sAlphas.get(alpha) + 1 : 1;

            sAlphas.put(alpha, count);
        }

        getNumberOfCasesRecursive(0, ' ');
        System.out.print(sAnswer);

        br.close();
    }

    private static void getNumberOfCasesRecursive(int count, final char last) {
        if (count == sString.length()) {
            //System.out.println(String.valueOf(sCharArray));
            ++sAnswer;

            return;
        }

        for (char current : sAlphas.keySet()) {
            if (sAlphas.get(current) == 0) {
                continue;
            }

            if (last == current) {
                continue;
            }

            sAlphas.put(current, sAlphas.get(current) - 1);
            //sCharArray[count] = current;
            getNumberOfCasesRecursive(count + 1, current);
            sAlphas.put(current, sAlphas.get(current) + 1);
        }
    }
}
