package LinkedList.s_203;

import LinkedList.ListNode;

public class Solution_203 {

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
