package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class G17471 {
    private static int sDistrictSize;
    private static int[] sDistricts;
    private static HashSet<Integer>[] sPaths;

    private static int sMinDiff = Integer.MAX_VALUE;
    private static boolean[] sContains;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sDistrictSize = Integer.parseInt(br.readLine());
        sDistricts = new int[sDistrictSize];
        sPaths = new HashSet[sDistrictSize];
        sContains = new boolean[sDistrictSize];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < sDistrictSize; ++i) {
            sDistricts[i] = Integer.parseInt(st.nextToken());
            sPaths[i] = new HashSet<>();
        }

        for (int i = 0; i < sDistrictSize; ++i) {
            st = new StringTokenizer(br.readLine());

            int pathSize = Integer.parseInt(st.nextToken());
            for (int j = 0; j < pathSize; ++j) {
                sPaths[i].add(Integer.parseInt(st.nextToken()) - 1);
            }
        }

        for (int count = 1; count <= (sDistrictSize / 2); ++count) {
            getMinPopulationDiffRecursive(0, -1, 0, count);
        }

        if (sMinDiff == Integer.MAX_VALUE) {
            sMinDiff = -1;
        }

        System.out.print(sMinDiff);
    }

    private static void getMinPopulationDiffRecursive(int index, int districtNum, int curCount, final int totalCount) {
        if (curCount == totalCount) {
            if (isConnected(true) && isConnected(false)) {
                int diff = getPopulationDiff();
                sMinDiff = Math.min(sMinDiff, diff);
            }

            return;
        }

        if (index == sDistrictSize) {
            return;
        }

        sContains[index] = true;
        getMinPopulationDiffRecursive(index + 1, index, curCount + 1, totalCount);
        sContains[index] = false;

        getMinPopulationDiffRecursive(index + 1, districtNum, curCount, totalCount);
    }

    private static int getPopulationDiff() {
        int containsSum = 0;
        int otherSum = 0;
        for (int districtNum = 0; districtNum < sDistrictSize; ++districtNum) {
            int population = sDistricts[districtNum];

            if (sContains[districtNum]) {
                containsSum += population;
            } else {
                otherSum += population;
            }
        }

        return Math.abs(otherSum - containsSum);
    }

    private static boolean isConnected(boolean isThis) {
        HashSet<Integer> districts = new HashSet<>();

        int start = -1;
        for (int i = 0; i < sDistrictSize; ++i) {
            if (sContains[i] == isThis) {
                districts.add(i);
                start = i;
            }
        }

        Queue<Integer> districtNums = new LinkedList<>();
        districtNums.add(start);

        HashSet<Integer> visited = new HashSet<>();
        visited.add(start);
        while (!districtNums.isEmpty()) {
            int curNum = districtNums.remove();

            for (int nextNum : sPaths[curNum]) {
                if (visited.contains(nextNum) || !districts.contains(nextNum)) {
                    continue;
                }

                visited.add(nextNum);
                districtNums.add(nextNum);
            }
        }

        if (visited.size() == districts.size()) {
            return true;
        }

        return false;
    }
}
