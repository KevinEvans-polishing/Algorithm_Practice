package LinkedList;

import LinkedList.s_203.Solution_203;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        var p = this;
        String s = "[";
        while (p != null) {
            s += p.val + ", ";
            p = p.next;
        }
        s += "]";
        return s;
    }
}
