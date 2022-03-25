package algorithm.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class S11724 {
    public static void testCode(String[] args) throws IOException {
        int count = getConnectedComponentCount();
        System.out.println(count);
    }

    private static int getConnectedComponentCount() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = bf.readLine().split(" ");

        int nodeCount = Integer.parseInt(inputs[0]);
        int lineCount = Integer.parseInt(inputs[1]);

        Node[] nodes = new Node[nodeCount];
        for (int index = 0; index < nodeCount; ++index) {
            nodes[index] = new Node(index + 1);
        }

        for (int index = 0; index < lineCount; ++index) {
            inputs = bf.readLine().split(" ");

            Node firstNode = nodes[Integer.parseInt(inputs[0]) - 1];
            Node secondNode = nodes[Integer.parseInt(inputs[1]) - 1];

            firstNode.addNeighbor(secondNode);
            secondNode.addNeighbor(firstNode);
        }

        if (nodeCount == 1) {
            return 1;
        }

        int count = 0;
        boolean[] visited = new boolean[nodeCount];

        for (int index = 0; index < nodeCount; ++index) {
            if (visited[index] == true) {
                continue;
            }

            count++;
            dfsRecursive(nodes[index], visited);
        }

        return count;
    }

    private static void dfsRecursive(Node node, boolean[] visited) {
        if (node == null) {
            return;
        }

        if (visited[node.getData() - 1] == true) {
            return;
        }

        visited[node.getData() - 1] = true;
        for (Node nextNode : node.getNeighbors()) {
            dfsRecursive(nextNode, visited);
        }
    }

    private static class Node {
        private int data;
        private HashSet<Node> neighbors;

        public Node(final int data) {
            this.data = data;
            neighbors = new HashSet<>();
        }

        public int getData() {
            return this.data;
        }

        public void addNeighbor(final Node node) {
            this.neighbors.add(node);
        }

        public HashSet<Node> getNeighbors() {
            return this.neighbors;
        }
    }
}
