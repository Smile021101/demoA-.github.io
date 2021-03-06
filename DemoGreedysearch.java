
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;


public class DemoGreedysearch {
    
    public static double distance = 0;
    public static Node aStar(Node start, Node target) {
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();

        start.f = start.calculateHeuristic(target);
        openList.add(start);

        while (!openList.isEmpty()) {
            Node n = openList.peek();
            if (n == target) {
                return n;
            }
            double check = 99999;

            for (Node.Edge edge : n.neighbors) {
                Node m = edge.node;
                double totalWeight = n.g + edge.weight;

                if (!openList.contains(m)) {
                    m.parent = n;
                    m.g=totalWeight;
                    m.f = m.g;
                    
                    openList.add(m);
                } else {
                    if (check < m.calculateHeuristic(target)) {
                        m.parent = n;
                        m.g = totalWeight;
                        check = m.calculateHeuristic(target);

                        if (closedList.contains(m)) {
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
                distance = m.f;
            }

            openList.remove(n);
            closedList.add(n);
        }
        return null;
    }

    public static void printPath(Node target) {
        Node n = target;

        if (n == null) {
            return;
        }

        List<String> ids = new ArrayList<>();

        while (n.parent != null) {
            ids.add(n.id);
            n = n.parent;
        }
        ids.add(n.id);
        Collections.reverse(ids);

        for (String id : ids) {
            System.out.print(id + " ");
        }
        System.out.println("");
        System.out.println("Do dai duong di giua 2 diem:"+distance);
    }

    public static void main(String[] args) {
        Node head = new Node(6,"S");
        head.g = 0;

        Node n1 = new Node(4,"B");
        Node n2 = new Node(4,"A");

        head.addBranch(3, n1);
        head.addBranch(2, n2);

        Node n4 = new Node(3,"C");
        Node n5 = new Node(4,"D");

        n1.addBranch(3, n4);
        n1.addBranch(1, n5);
        n2.addBranch(3, n5);
        n5.addBranch(1, n4);
        
        Node n6 = new Node(1,"E");
        Node n7 = new Node(1,"F");
        Node target = new Node(0,"G");

        n4.addBranch(2, n6);
        n5.addBranch(3, n7);
        n6.addBranch(1, target);
        n7.addBranch(2, target);

        Node res = aStar(head, target);
        printPath(res);
    }
}