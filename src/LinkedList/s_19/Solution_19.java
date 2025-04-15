package LinkedList.s_19;


import LinkedList.ListNode;

public class Solution_19 {

    public static ListNode removeNthFromEnd1(ListNode head, int n) {
        ListNode s = new ListNode(0, head);
        ListNode p1 = s;
        ListNode p2;
        int count = 1;
        int size = 0;
        while ((p2 = p1.next) != null) {
            size++;
            p1 = p2;
        }
        p1 = s;
        while ((p2 = p1.next) != null) {
            if (count == size - n + 1) {
                p1.next = p2.next;
                break;
            } else {
                count++;
                p1 = p2;
            }
        }
        return s.next;
    }

    // 递归
    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode s = new ListNode(0, head);
        removeRecursion(s, n);
        return s.next;
    }

    private static int removeRecursion(ListNode head, int n) {
        if (head == null) {
            return 0;
        }
        int nth = removeRecursion(head.next, n);
        if (nth == n) {
            head.next = head.next.next;
        }
        return nth + 1;
    }

    // 滞后双指针
    // 将p2先移动n+1个位置
    // 然后循环到p2指向null
    // 同时更新p1,p2分别指向各自的下一个
    // 那么最后p2与p1的间距为n+1
    // p1的下一位就是要删除的节点
    public static ListNode removeNthFromEnd3(ListNode head, int n) {
        ListNode s = new ListNode(0, head);
        ListNode p1 = s;
        ListNode p2 = s;

        for (int i = 0; i < n + 1; i++) {
            p2 = p2.next;
        }
        while (p2 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }

        p1.next = p1.next.next;
        return s.next;
    }

}
