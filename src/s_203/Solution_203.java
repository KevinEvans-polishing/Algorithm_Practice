package s_203;

import java.util.List;

public class Solution_203 {

    public static void main(String[] args) {
        ListNode o7 = new ListNode(7, null);
        var o6 = new ListNode(7, o7);
        var o5 = new ListNode(7, o6);
        var o4 = new ListNode(7, o5);
//        var o3 = new ListNode(6, o4);
//        var o2 = new ListNode(2, o3);
//        var o1 = new ListNode(1, o2);

        o4 = removeElements1(o4, 7);
        System.out.println(o4);
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
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

    public static ListNode removeElements1(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        ListNode s = new ListNode(0 ,head);
        ListNode p1 = s;
        ListNode p2;
        while ((p2 = p1.next) != null) {
            if (p2.val == val) {
                p1.next = p2.next;
            } else {
                p1 = p1.next;
            }
        }
        return s.next;
    }

    // 递归，数值相等则返回后续节点，不想等就返回自己+后续节点
    public static ListNode removeElements2(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        if (head.val == val) {
            return removeElements2(head.next, val);
        } else {
            head.next = removeElements2(head.next, val);
            return head;
        }
    }
}
