import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class S1149 {
    public static void testCode(String[] args) throws IOException {
        int minimumCost = getMinimumCost();
        System.out.println(minimumCost);
    }

    private static int getMinimumCost() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(bf.readLine());

        int[][] costs = new int[count][3];
        for (int index = 0; index < count; ++index) {
            String[] inputs = bf.readLine().split(" ");

            for (int colorIndex = 0; colorIndex < 3; ++colorIndex) {
                costs[index][colorIndex] = Integer.parseInt(inputs[colorIndex]);
            }
        }

        int[][] cache = new int[count][3];
        int depth = 0;
        for (int colorIndex = 0; colorIndex < 3; ++colorIndex) {
            cache[count - 1][colorIndex] = costs[count - 1][colorIndex];
        }

        int lowerCost = Integer.MAX_VALUE;
        for (int colorIndex = 0; colorIndex < 3; ++colorIndex) {
            int addCost = getMinimumCostRecursive(costs, colorIndex, depth, cache);
            int cost = addCost + costs[depth][colorIndex];

            if (cost < lowerCost) {
                lowerCost = cost;
            }
        }

        return lowerCost;
    }

    private static int getMinimumCostRecursive(int[][] costs, int lastIndex, int depth, int[][] cache) {
        ++depth;

        if (depth >= costs.length) {
            return 0;
        }

        int lowerCost = Integer.MAX_VALUE;
        for (int colorIndex = 0; colorIndex < 3; ++colorIndex) {
            if (lastIndex == colorIndex) {
                continue;
            }

            int cost;
            if (cache[depth][colorIndex] == 0) {
                int addCost = getMinimumCostRecursive(costs, colorIndex, depth, cache);

                cache[depth][colorIndex] = addCost + costs[depth][colorIndex];
            }

            cost = cache[depth][colorIndex];

            if (cost < lowerCost) {
                lowerCost = cost;
            }
        }

        return lowerCost;
    }


}
