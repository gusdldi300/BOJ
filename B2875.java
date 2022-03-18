package algorithm.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class B2875 {
    public static void testCode(String[] args) throws IOException {
        int count = getMostTeamCount();

        System.out.println(count);
    }

    private static int getMostTeamCount() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = bf.readLine().split(" ");
        int femaleCount = Integer.parseInt(inputs[0]);
        int maleCount = Integer.parseInt(inputs[1]);
        int internCount = Integer.parseInt(inputs[2]);

        if (maleCount < 1 || femaleCount < 2) {
            return 0;
        }

        int remainStudents = 0;
        if ((femaleCount % 2) == 1) {
            femaleCount--;

            remainStudents++;
        }

        int teamCount = maleCount;
        int temp = maleCount * 2;

        if (temp > femaleCount) {
            while (temp > femaleCount) {
                temp -= 2;

                remainStudents++; // male
                teamCount--;
            }
        } else if (temp < femaleCount){
            remainStudents += (femaleCount - temp);
        }

        while (internCount > remainStudents) {
            if (teamCount <= 0) {
                break;
            }

            teamCount--;
            remainStudents += 3;
        }

        return teamCount;
    }
}
