package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class G2026 {
    private static class Student {
        private int id;
        private HashSet<Integer> friendIds = new HashSet<>();

        public Student(final int id) {
            this.id = id;
        }
    }

    private static int sMaxSelectCount;
    private static int sMaxStudentCount;
    private static int sMaxRelationshipCount;

    private static Student[] sStudents;

    private static int[] sSelectedIds;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        sMaxSelectCount = Integer.parseInt(st.nextToken());
        sMaxStudentCount = Integer.parseInt(st.nextToken());
        sMaxRelationshipCount = Integer.parseInt(st.nextToken());

        sStudents = new Student[sMaxStudentCount + 1];
        sSelectedIds = new int[sMaxSelectCount];

        for (int id = 1; id <= sMaxStudentCount; ++id) {
            sStudents[id] = new Student(id);
        }

        for (int i = 0; i < sMaxRelationshipCount; ++i) {
            st = new StringTokenizer(br.readLine());

            int firstId = Integer.parseInt(st.nextToken());
            int secondId = Integer.parseInt(st.nextToken());

            sStudents[firstId].friendIds.add(secondId);
            sStudents[secondId].friendIds.add(firstId);
        }

        if (!getSelectedStudentsAscendRecursive(1, 0)) {
            System.out.print(-1);

            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sMaxSelectCount; ++i) {
            sb.append(sSelectedIds[i]);
            sb.append(System.lineSeparator());
        }

        System.out.print(sb.toString());
    }

    private static boolean getSelectedStudentsAscendRecursive(int nextId, int selectedIndex) {
        if (selectedIndex == sMaxSelectCount) {
            return true;
        }

        outer:
        for (int id = nextId; id <= sMaxStudentCount; ++id) {
            for (int j = 0; j < selectedIndex; ++j) {
                if (!sStudents[id].friendIds.contains(sSelectedIds[j])) {
                    continue outer;
                }
            }

            sSelectedIds[selectedIndex] = id;
            if (getSelectedStudentsAscendRecursive(id + 1, selectedIndex + 1)) {
                return true;
            }
        }

        return false;
    }
}
