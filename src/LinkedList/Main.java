package LinkedList;

import static LinkedList.s_206.Solution_206.reverseList3;

public class Main {
    public static void main(String[] args) {
        ListNode o5 = new ListNode(5, null);
        var o4 = new ListNode(4, o5);
        var o3 = new ListNode(3, o4);
        var o2 = new ListNode(2, o3);
        var o1 = new ListNode(1, o2);

        System.out.println(o1);
        o1 = reverseList3(o1);
        System.out.println(o1);
    }
}
