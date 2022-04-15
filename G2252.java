package algorithm.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

// Implement with adjacency matrix
public class G2252 {
    public static void testCode(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = bf.readLine().split(" ");

        int studentCount = Integer.parseInt(inputs[0]);
        int compareCount = Integer.parseInt(inputs[1]);

        if (studentCount == 1) {
            System.out.println(1);

            return;
        }

        Student[] students = new Student[studentCount + 1];
        students[0] = null;

        for (int count = 1; count <= studentCount; ++count) {
            students[count] = new Student(count);
        }

        for (int compareIndex = 0; compareIndex < compareCount; ++compareIndex) {
            inputs = bf.readLine().split(" ");

            int firstStudent = Integer.parseInt(inputs[0]);
            int secondStudent = Integer.parseInt(inputs[1]);

            students[firstStudent].addBiggerStudent(students[secondStudent]);
        }

        printStudentHeightsAscending(students);
    }

    private static void printStudentHeightsAscending(Student[] students) {
        LinkedList<Student> printList = new LinkedList<>();
        boolean[] pushed = new boolean[students.length];
        pushed[0] = true;

        for (int count = 1; count < students.length; ++count) {
            addStudentToListRecursive(students[count], printList, pushed);
        }

        for (Student student : printList) {
            System.out.format("%s ", student.getId());
        }
    }

    private static void addStudentToListRecursive(final Student student, final LinkedList<Student> printList, final boolean[] pushed) {
        if (student == null) {
            return;
        }

        if (pushed[student.getId()] == true) {
            return;
        }

        for (Student nextStudent : student.getBiggerStudents()) {
            addStudentToListRecursive(nextStudent, printList, pushed);
        }

        pushed[student.getId()] = true;
        printList.addFirst(student);
    }

    private static class Student {
        private int id;
        private ArrayList<Student> biggerStudents;

        public Student(int id) {
            this.id = id;
            this.biggerStudents = new ArrayList<>();
        }

        public int getId() {
            return this.id;
        }

        public ArrayList<Student> getBiggerStudents() {
            return this.biggerStudents;
        }

        public void addBiggerStudent(final Student student) {
            this.biggerStudents.add(student);
        }
    }
}
