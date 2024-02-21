package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G17182 {
    private static int sPlanetsSize;
    private static int sStartPlanet;
    private static int[][] sPlanets;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sPlanetsSize = Integer.parseInt(st.nextToken());
        sStartPlanet = Integer.parseInt(st.nextToken());

        sPlanets = new int[sPlanetsSize][sPlanetsSize];
        for (int row = 0; row < sPlanetsSize; ++row) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sPlanetsSize; ++col) {
                sPlanets[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        sUsed = new boolean[sPlanetsSize][sPlanetsSize];
        for (int i = 0; i < sPlanetsSize; ++i) {
            sVisitedAll |= (1 << i);
        }

        int visited = (1 << sStartPlanet);
        getMinExploreTimeRecursive(sStartPlanet, 0, visited);

        System.out.print(sMinExploreTime);
    }

    private static int sMinExploreTime = Integer.MAX_VALUE;
    private static boolean[][] sUsed;
    private static int sVisitedAll;

    private static boolean hasVisitedAll(final int[] visited) {
        for (int i = 0; i < visited.length; ++i) {
            if (visited[i] <= 0) {
                return false;
            }
        }

        return true;
    }

    private static void getMinExploreTimeRecursive(int cur, int exploreTime, int visited) {
        if (visited == sVisitedAll) {
            sMinExploreTime = Math.min(sMinExploreTime, exploreTime);

            return;
        }

        for (int i = 0; i < sPlanetsSize; ++i) {
            if (cur == i) {
                continue;
            }

            if (sUsed[cur][i]) {
                continue;
            }

            int copied = visited | (1 << i);
            sUsed[cur][i] = true;
            getMinExploreTimeRecursive(i, exploreTime + sPlanets[cur][i], copied);
            sUsed[cur][i] = false;
        }
    }
}
