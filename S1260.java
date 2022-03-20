package algorithm.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class S1260 {
    public static void testCode(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = bf.readLine().split(" ");

        int nodeCount = Integer.parseInt(inputs[0]);
        int lineCount = Integer.parseInt(inputs[1]);
        int startNodeIndex = Integer.parseInt(inputs[2]) - 1;


        Node[] nodes = new Node[nodeCount];
        for (int index = 0; index < nodeCount; ++index) {
            nodes[index] = new Node(index + 1);
        }

        for (int index = 0; index < lineCount; ++index) {
            inputs = bf.readLine().split(" ");

            int nodeIndex = Integer.parseInt(inputs[0]) - 1;
            int neighborIndex = Integer.parseInt(inputs[1]) - 1;

            nodes[nodeIndex].addNeighbor(nodes[neighborIndex]);
            nodes[neighborIndex].addNeighbor(nodes[nodeIndex]);
        }

        Node startNode = nodes[startNodeIndex];

        printDfs(startNode);
        printBfs(startNode);
    }

    private static void printDfs(final Node startNode) {
        StringBuilder sb = new StringBuilder();
        HashSet<Node> visited = new HashSet<>();

        printDfsRecursive(startNode, visited, sb);

        System.out.println(sb.toString());
    }

    private static void printDfsRecursive(final Node node, final HashSet<Node> visited, StringBuilder sb) {
        if (node == null) {
            return;
        }

        sb.append(node.getData() + " ");
        visited.add(node);

        ArrayList<Node> neighbors = node.getNeighbors();
        Collections.sort(neighbors, (Node a, Node b) -> a.getData() - b.getData());
        for (Node neighbor : neighbors) {
            if (visited.contains(neighbor) == false) {
                printDfsRecursive(neighbor, visited, sb);
            }
        }
    }

    private static void printBfs(final Node startNode) {
        HashSet<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();

        queue.add(startNode);
        visited.add(startNode);

        while (queue.isEmpty() == false) {
            Node currentNode = queue.remove();

            sb.append(currentNode.getData() + " ");

            ArrayList<Node> neighbors = currentNode.getNeighbors();
            Collections.sort(neighbors, (Node a, Node b) -> a.getData() - b.getData());
            for (Node neighbor : neighbors) {
                if (visited.contains(neighbor) == false) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        System.out.println(sb.toString());
    }

    private static class Node {
        private int data;
        private ArrayList<Node> neighbors;

        public Node(final int data) {
            this.data = data;
            this.neighbors = new ArrayList<>();
        }

        public int getData() {
            return this.data;
        }

        public ArrayList<Node> getNeighbors() {
            return this.neighbors;
        }

        public void addNeighbor(final Node node) {
            this.neighbors.add(node);
        }
    }
}
