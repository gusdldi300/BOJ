package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class G19942 {
    private static final int NUTRIENTS_SIZE = 4;

    private static int[] sMinNutrients = new int[NUTRIENTS_SIZE];
    private static int[][] sIngredients;

    private static int sIngredientsSize;
    private static int sMinPrice = Integer.MAX_VALUE;
    private static ArrayList<Integer> sIngredientsPicked = new ArrayList<>();
    private static String sIngredientsPickedString;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sIngredientsSize = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < NUTRIENTS_SIZE; ++i) {
            sMinNutrients[i] = Integer.parseInt(st.nextToken());
        }

        sIngredients = new int[sIngredientsSize][NUTRIENTS_SIZE + 1];
        for (int i = 0; i < sIngredientsSize; ++i) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < NUTRIENTS_SIZE + 1; ++j) {
                sIngredients[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[] nutrients = new int[NUTRIENTS_SIZE];
        getMinPriceRecursive(0, 0, nutrients);

        if (sMinPrice == Integer.MAX_VALUE) {
            System.out.print(-1);

            return;
        }

//        StringBuilder sb = new StringBuilder();
//        sb.append(sMinPrice);
//        sb.append(System.lineSeparator());
//
//        for (int i = 0; i < sIngredientsPicked.size(); ++i) {
//            sb.append(sIngredientsPicked.get(i));
//            sb.append(' ');
//        }

        System.out.print(sIngredientsPickedString);
    }

    private static boolean isNutrientsSatisfied(final int[] nutrients) {
        for (int i = 0; i < NUTRIENTS_SIZE; ++i) {
            if (nutrients[i] < sMinNutrients[i]) {
                return false;
            }
        }

        return true;
    }

    private static void getMinPriceRecursive(int count, int price, final int[] nutrients) {
        if (isNutrientsSatisfied(nutrients)) {
            if (price < sMinPrice) {
                sMinPrice = price;

                StringBuilder sb = new StringBuilder();
                sb.append(sMinPrice);
                sb.append(System.lineSeparator());

                for (int i = 0; i < sIngredientsPicked.size(); ++i) {
                    sb.append(sIngredientsPicked.get(i) + 1);
                    sb.append(' ');
                }

                sIngredientsPickedString = sb.toString();
            }

            return;
        }

        if (count >= sIngredientsSize) {
            return;
        }

        for (int i = count; i < sIngredientsSize; ++i) {
            sIngredientsPicked.add(i);
            for (int j = 0; j < NUTRIENTS_SIZE; ++j) {
                nutrients[j] += sIngredients[i][j];
            }

            getMinPriceRecursive(i + 1, price + sIngredients[i][4], nutrients);

            for (int j = 0; j < NUTRIENTS_SIZE; ++j) {
                nutrients[j] -= sIngredients[i][j];
            }
            sIngredientsPicked.remove(sIngredientsPicked.size() - 1);
        }

    }



}
