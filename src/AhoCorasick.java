import java.util.*;

public class AhoCorasick {

    static class Node {
        Map<Character, Node> children = new HashMap<>();
        Node failure;
        Node outputLink;
        List<String> outputs = new ArrayList<>();
    }

    private final Node root = new Node();

    public void addPattern(String pattern) {
        Node current = root;

        for (char c : pattern.toCharArray()) {
            current.children.putIfAbsent(c, new Node());
            current = current.children.get(c);
        }

        current.outputs.add(pattern);
    }

    public void buildFailureLinks() {
        Queue<Node> queue = new LinkedList<>();

        root.failure = root;

        for (Node child : root.children.values()) {
            child.failure = root;
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (Map.Entry<Character, Node> entry :
                    current.children.entrySet()) {

                char c = entry.getKey();
                Node child = entry.getValue();

                Node failure = current.failure;

                while (failure != root &&
                        !failure.children.containsKey(c)) {
                    failure = failure.failure;
                }

                if (failure.children.containsKey(c)) {
                    child.failure = failure.children.get(c);
                } else {
                    child.failure = root;
                }

                if (!child.failure.outputs.isEmpty()) {
                    child.outputLink = child.failure;
                } else {
                    child.outputLink = child.failure.outputLink;
                }

                queue.add(child);
            }
        }
    }

    public int search(String text) {
        Node current = root;
        int matches = 0;

        for (char c : text.toCharArray()) {

            while (current != root &&
                    !current.children.containsKey(c)) {
                current = current.failure;
            }

            if (current.children.containsKey(c)) {
                current = current.children.get(c);
            }

            Node temp = current;

            while (temp != null) {

                for (String pattern : temp.outputs) {
                    matches++;
                }

                temp = temp.outputLink;
            }
        }

        return matches;
    }
}