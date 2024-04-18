package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class G17140 {
    private static final int MAX_TIME = 100;
    private static final int MAX_ROW_SIZE = 100;
    private static final int MAX_COL_SIZE = 100;

    private static int sCurRowSize = 3;
    private static int sCurColSize = 3;
    private static int[][] sArray = new int[MAX_ROW_SIZE][MAX_COL_SIZE];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int targetRow = Integer.parseInt(st.nextToken()) - 1;
        int targetCol = Integer.parseInt(st.nextToken()) - 1;
        int targetNumber = Integer.parseInt(st.nextToken());

        for (int row = 0; row < sCurRowSize; ++row) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sCurColSize; ++col) {
                sArray[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        int time = 0;
        while (time <= MAX_TIME) {
            if (targetRow < sCurRowSize && targetCol < sCurColSize && sArray[targetRow][targetCol] == targetNumber) {
                break;
            }

            if (sCurRowSize >= sCurColSize) {
                updateArray(sCurRowSize, sCurColSize, true);
            } else {
                updateArray(sCurColSize, sCurRowSize, false);
            }

            ++time;
        }

        if (time > MAX_TIME) {
            time = -1;
        }

        System.out.print(time);
    }

    private static void updateArray(int firstSize, int secondSize, boolean isRow) {
        int maxSize = 0;
        for (int i = 0; i < firstSize; ++i) {
            HashMap<Integer, Integer> numberMap = new HashMap<>();
            for (int j = 0; j < secondSize; ++j) {
                int number;
                if (isRow) {
                    number = sArray[i][j];
                    sArray[i][j] = 0;
                } else {
                    number = sArray[j][i];
                    sArray[j][i] = 0;
                }

                if (number == 0) {
                    continue;
                }

                int count = 1;
                if (numberMap.containsKey(number)) {
                    count += numberMap.get(number);
                }

                numberMap.put(number, count);
            }

            ArrayList<Integer> keySet = new ArrayList<>(numberMap.keySet());
            keySet.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer firstNum, Integer secondNum) {
                    if (numberMap.get(firstNum) == numberMap.get(secondNum)) {
                        return firstNum - secondNum;
                    }

                    return numberMap.get(firstNum) - numberMap.get(secondNum);
                }
            });

            int curSize = keySet.size() * 2;
            if (curSize > maxSize) {
                maxSize = curSize;
            }

            int index = 0;
            for (Integer number : keySet) {
                if (isRow) {
                    sArray[i][index++] = number;
                    sArray[i][index++] = numberMap.get(number);
                } else {
                    sArray[index++][i] = number;
                    sArray[index++][i] = numberMap.get(number);
                }
            }
        }

        maxSize = Math.min(maxSize, MAX_ROW_SIZE);
        if (isRow) {
            sCurColSize = maxSize;
        } else {
            sCurRowSize = maxSize;
        }
    }
}
