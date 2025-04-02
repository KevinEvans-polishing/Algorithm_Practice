package s_206;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution_206 {
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


    public static class ListNode {
      int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    //  方法1:反向构造新的链表
    public ListNode reverseList1(ListNode o1) {
        ListNode n1 = null;
        ListNode p = o1;

        while (p != null) {
            n1 = new ListNode(p.val, n1);
            p = p.next;
        }
        return n1;
    }

    // 方法2:移除原来链表的第一个节点
    // 然后加到新链表的第一个节点
    public static ListNode reverseList2(ListNode o1) {
        List oldList = new List(o1);
        List newList = new List(null);

        while (true) {
            ListNode node = oldList.removeFirst();
            if (node == null) {
                break;
            }
            newList.addFirst(node);
        }
        return newList.head;
    }

    // 方法3:递归
    public static ListNode reverseList3(ListNode o1) {
        if (o1 == null || o1.next == null) {
            return o1;
        }
        // 注意这里last是节点的引用
        // 改变了o1及其next的关系也会影响到last
        // 所以最后返回last没有问题
        var last = reverseList3(o1.next);
        o1.next.next = o1;
        o1.next = null;
        return last;
    }

    // 方法4: 将旧链表的头节点的下一个从链表中移除
    // 然后将它置于链表的首位
    public static ListNode reverseList4(ListNode o1) {
        ListNode n1 = o1;

        while (o1 != null && o1.next != null) {
            var lastN1 = n1;
            n1 = o1.next;
            // 断开
            o1.next = o1.next.next;
            //添加到首位
            n1.next = lastN1;
        }
        return n1;
    }

    // 方法5:面向过程版本的方法2
    public static ListNode reverseList5(ListNode o1) {
//        if (o1 != null) {
//            ListNode result = new ListNode(o1.val, null);
//            while (o1.next != null) {
//                //倒数第二个
//                ListNode node = o1;
//                //最后一个
//                ListNode lastNode = o1.next;
//                result = new ListNode(lastNode.val, result);
//                o1 = o1.next;
//            }
//            return result;
//        }
//        return null;
        if (o1 == null || o1.next == null) {
            return o1;
        }
        ListNode n1 = null;
        while (o1 != null) {
            ListNode o2 = o1.next;
            o1.next = n1;
            n1 = o1;
            o1 = o2;
        }
        return n1;
    }

    static class List {
        ListNode head;

        public List(ListNode head) {
            this.head = head;
        }


        public void addFirst(ListNode first) {
            first.next = head;
            head = first;
        }

        public ListNode removeFirst() {
            ListNode first = head;
            if (first != null) {
                head = first.next;
            }
            return first;
        }
    }
}