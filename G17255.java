package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class G17255 {
    private static int sNumMakeCount;
    private static String sNumString;
    private static char[] sNumMakeSeq; // int[] 할 경우, 수의 순서가 달라도 동일한 해쉬값 나옴
    private static Set<String> sDiscovered = new HashSet<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sNumString = br.readLine();

        int seqLength = (sNumString.length() * (sNumString.length() + 1)) / 2;
        sNumMakeSeq = new char[seqLength];
        for (int i = 0; i < sNumString.length(); ++i) {
            getNumMakeCountRecursive(0, i, i);
        }

        System.out.print(sNumMakeCount);
    }

    private static void getNumMakeCountRecursive(int seqIndex, final int leftIndex, final int rightIndex) {
        if (seqIndex == sNumMakeSeq.length) {
            String seqString = sNumMakeSeq.toString();

            if (!sDiscovered.contains(seqString)) {
                ++sNumMakeCount;
                sDiscovered.add(seqString);
            }

            return;
        }

        if (leftIndex < 0 || rightIndex >= sNumString.length()) {
            return;
        }

        for (int i = leftIndex; i <= rightIndex; ++i) {
            sNumMakeSeq[seqIndex] = sNumString.charAt(i);
            ++seqIndex;
        }

        getNumMakeCountRecursive(seqIndex, leftIndex - 1, rightIndex);
        getNumMakeCountRecursive(seqIndex, leftIndex, rightIndex + 1);
    }

    /*
    private static class Number {
        private int num;
        private HashMap<Integer, Number> children = new HashMap<>();

        public Number(int num) {
            this.num = num;
        }
    }

    private static HashMap<Integer, Number> sVisited = new HashMap<>();
    private static boolean[] sCache;
    private static String sNumberString;

    private static int sAnswer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sNumberString = br.readLine();
        sCache = new boolean[sNumberString.length()];
        for (int index = 0; index < sNumberString.length(); ++index) {
            int num = sNumberString.charAt(index) - '0';
            if (!sVisited.containsKey(num)) {
                sVisited.put(num, new Number(num));
            }
        }

        if (sNumberString.length() == 1) {
            System.out.print(1);
            return;
        }

        ArrayList<Integer> visited = new ArrayList<>();
        for (int index = 0; index < sNumberString.length(); ++index) {
            int num = sNumberString.charAt(index) - '0';

            sCache[index] = true;
            visited.add(num);
            getCasesOfNumberRecursive(1, num, visited, Integer.parseInt(sNumberString));
            visited.remove(visited.size() - 1);
            sCache[index] = false;
        }

        System.out.print(sAnswer);
    }

    private static boolean hasVisited(final ArrayList<Integer> visited) {
        Number currentNum = sVisited.get(visited.get(0));

        for (int i = 1; i < visited.size(); ++i) {
            int visitedNum = visited.get(i);

            if (!currentNum.children.containsKey(visitedNum)) {
                return false;
            }

            currentNum = currentNum.children.get(visitedNum);
        }

        return true;
    }

    private static void printVisited(final ArrayList<Integer> visited) {
        for (int i = 0; i < visited.size(); ++i) {
            System.out.format("%d ", visited.get(i));
        }
        System.out.println();
    }

    private static void updateVisited(final ArrayList<Integer> visited) {
        Number currentNum = sVisited.get(visited.get(0));

        for (int i = 1; i < visited.size(); ++i) {
            if (!currentNum.children.containsKey(visited.get(i))) {
                currentNum.children.put(visited.get(i), new Number(visited.get(i)));
            }

            currentNum = currentNum.children.get(visited.get(i));
        }
    }

    private static void getCasesOfNumberRecursive(int count, final int number, final ArrayList<Integer> visited, final int targetNum) {
        if (count == sNumberString.length()) {
            if (number == targetNum) {
                if (!hasVisited(visited)) {
                    //printVisited(visited);
                    ++sAnswer;
                    updateVisited(visited);
                }
            }

            return;
        }

        for (int index = 0; index < sNumberString.length(); ++index) {
            if (sCache[index] == true) {
                continue;
            }

            int source = sNumberString.charAt(index) - '0';
            sCache[index] = true;
            int leftAddedNum =((int) Math.pow(10, count) * source) + number;
            visited.add(leftAddedNum);
            getCasesOfNumberRecursive(count + 1, leftAddedNum, visited, targetNum);
            visited.remove(visited.size() - 1);

            int rightAddedNum = (number * 10) + source;
            visited.add(rightAddedNum);
            getCasesOfNumberRecursive(count + 1, rightAddedNum, visited, targetNum);
            visited.remove(visited.size() - 1);
            sCache[index] = false;
        }
    }
    */
}
