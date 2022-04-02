package search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class G13023 {
    public static void testCode(String[] args) throws IOException {
        printRelationship();
    }

    private static void printRelationship() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = bf.readLine().split(" ");

        int personCount = Integer.parseInt(inputs[0]);
        int relationCount = Integer.parseInt(inputs[1]);

        Person[] people = new Person[personCount];
        for (int index = 0; index < personCount; ++index) {
            people[index] = new Person(index, new boolean[personCount]);
        }

        for (int index = 0; index < relationCount; ++index) {
            inputs = bf.readLine().split(" ");

            int firstId = Integer.parseInt(inputs[0]);
            int secondId = Integer.parseInt(inputs[1]);

            people[firstId].addFriend(people[secondId]);
            people[secondId].addFriend(people[firstId]);
        }

        boolean[] visitedNode = new boolean[personCount];
        for (int index = 0; index < personCount; ++index) {
            searchFriendRecursive(people[index], visitedNode, 0);
        }

        System.out.println(0);
    }

    private static int searchFriendRecursive(final Person person, final boolean[] visitedNode, int count) {
        if (count >= 4) {
            System.out.println(1);

            System.exit(0);
        }

        visitedNode[person.getId()] = true;

        for (Person nextPerson : person.getFriends()) {
            if (visitedNode[nextPerson.getId()] == true) {
                continue;
            }

            searchFriendRecursive(nextPerson, visitedNode, count + 1);
        }

        visitedNode[person.getId()] = false;

        return 0;
    }

    private static class Person {
        private int id;
        private ArrayList<Person> friends;

        public Person(int id, boolean[] checked) {
            this.id = id;
            this.friends = new ArrayList<>();
        }

        public int getId() {
            return this.id;
        }

        public ArrayList<Person> getFriends() {
            return this.friends;
        }

        public void addFriend(final Person person) {
            this.friends.add(person);
        }
    }
}
