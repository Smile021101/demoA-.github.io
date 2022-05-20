import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class DemoA {

    public static double distance = 0;

    public static Node aStar(Node start, Node target) {
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();

        start.f = start.g + start.calculateHeuristic(target);
        openList.add(start);

        while (!openList.isEmpty()) {
            Node n = openList.peek(); //lây nút ra khoi hàng doi
            if (n == target) {// nêu la nut dich thì tra vê
                return n;
            }

            for (Node.Edge edge : n.neighbors) {
                Node m = edge.node; // xet cac nut con
                double totalWeight = n.g + edge.weight; // gia tri h cua nút con

                if (!openList.contains(m) && !closedList.contains(m)) { // nêu chua có trong hang doi thì cho vào hang doi
                    m.parent = n;
                    m.g = totalWeight;
                    m.f = m.g + m.calculateHeuristic(target);
                    openList.add(m);
                } else {
                    if (totalWeight < m.g) { // nêu dã có thì câp nhât nut mói nêu tôi uu hon
                        m.parent = n;
                        m.g = totalWeight;
                        m.f = m.g + m.calculateHeuristic(target);

                        if (closedList.contains(m)) {
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
                System.out.println(totalWeight + " " + m.f + " " + m.g);
                distance = m.f;
            }

            openList.remove(n);
            closedList.add(n);
            for (Node nd : openList) {
                System.out.print(nd + " ");
            }
            System.out.println("");
            for (Node nd : closedList) {
                System.out.print(nd + " ");
            }
            System.out.println("");
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
        System.out.println("Do dai duong di giua 2 diem:" + distance);
    }

    public static void main(String[] args) throws FileNotFoundException {
//        Node head = new Node(6,"S");
//        head.g = 0;
//
//        Node n1 = new Node(4,"B");
//        Node n2 = new Node(4,"A");
//
//        head.addBranch(3, n1);
//        head.addBranch(2, n2);
//
//        Node n4 = new Node(3,"C");
//        Node n5 = new Node(4,"D");
//
//        n1.addBranch(3, n4);
//        n1.addBranch(1, n5);
//        n2.addBranch(3, n5);
//        n5.addBranch(1, n4);
//        
//        Node n6 = new Node(1,"E");
//        Node n7 = new Node(1,"F");
//        Node target = new Node(0,"G");
//
//        n4.addBranch(2, n6);
//        n5.addBranch(3, n7);
//        n6.addBranch(1, target);
//        n7.addBranch(2, target);
//        Node res = aStar(head, target);
//        
//        printPath(res);
        String url1 = "C:\\Users\\levan\\Documents\\NetBeans Projects\\DemoTTCS\\src\\data1.txt";
        String url2 = "C:\\Users\\levan\\Documents\\NetBeans Projects\\DemoTTCS\\src\\data2.txt";
        // Đọc dữ liệu từ File với Scanner
        FileInputStream data1 = new FileInputStream(url1);
        FileInputStream data2 = new FileInputStream(url2);
        ArrayList<Node> listNode = new ArrayList<>();
        Scanner in1 =  new Scanner(data1);
        Scanner in2 = new Scanner(data2);
        while(in1.hasNextLine()){
            String nod = in1.nextLine();
            String nod1[] = nod.split(" ");
            Node node = new Node(Double.parseDouble(nod1[1]),nod1[0]);
            listNode.add(node);
        }
        int count = 0;
        while(in2.hasNextLine()){
            String line = in2.nextLine();
            String weight[] = line.split(" ");           
            for(int i=0; i<weight.length;i++){
               int g = Integer.parseInt(weight[i]);
               if(g>0){
                   listNode.get(count).addBranch(g, listNode.get(i));
               }
            }
            count++;
        }
        listNode.get(0).g=0;
        Node res = aStar(listNode.get(0),listNode.get(count-1));
        printPath(listNode.get(count-1));
    }
}
