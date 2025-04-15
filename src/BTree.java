import java.util.Arrays;

public class BTree {
    static class Node {
        int[] keys;
        Node[] children;
        int keyNumber;
        boolean leaf = true;
        int t; // 最少的孩子数--度degree

        public Node(int t) { // t最小为2
            this.t = t;
            children = new Node[2 * t];
            keys = new int[2 * t - 1];
        }

        @Override
        public String toString() {
            return Arrays.toString(Arrays.copyOfRange(keys, 0, keyNumber));
        }

        // 多路查找
        Node get(int key) {
            int i = 0;
            while (i < keyNumber) {
                if (keys[i] == key) {
                    return this;
                }
                if (keys[i] > key) {
                    break;
                }
                i++;
            }
            // 执行到此时keys[i] > key或i == keyNumber
            if (leaf) {
                return null;
            }
            // 非叶子情况 -- 需要删除在相应的孩子节点中查找
            // 孩子节点的索引时退出循环时i的值
            return children[i].get(key);
        }

        // 向keys指定索引处插入key
        void insertKey(int key, int index) {
            for (int i = keyNumber - 1; i >= index; i--) {
                // index之后的元素向后移动，腾出插入位置
                keys[i + 1] = keys[i];
            }
            keys[index] = key;
            keyNumber++;
        }

        // 向children指定索引处插入child
        void insertChild(Node child, int index) {
            for (int i = children.length - 1; i >= index; i--) {
                children[i + 1] = children[i];
            }

            children[index] = child;
        }

        // 移除指定index的key
        int removeKey(int index) {
            int t = keys[index];
            System.arraycopy(keys, index + 1, keys, index, --keyNumber - index);
            return t;
        }

        // 移除最左边的key
        int removeLeftMostKey() {
            return removeKey(0);
        }

        // 移除最右边的key
        int removeRightMostKey() {
            return removeKey(keyNumber - 1);
        }

        // 移除指定index处的child
        Node removeChild(int index) {
            Node t = children[index];
            System.arraycopy(children, index + 1, children, index, children.length - index - 1);
            return t;
        }

        Node removeLeftMostChild() {
            return removeChild(0);
        }

        Node removeRightMostChild() {
            return removeChild(keyNumber);
        }

        // index孩子左边的兄弟
        Node childLeftSibling(int index) {
            return index > 0 ? children[index - 1] : null;
        }

        Node childRightSibling(int index) {
            return index == keyNumber ? null : children[index + 1];
        }

        // 复制当前节点的所有key和child到target
        void moveToTarget(Node target) {
            int start = target.keyNumber;
            if (!leaf) {
                for (int i = 0; i <= keyNumber; i++) {
                    target.children[start + i] = children[i];
                }
            }
            for (int i = 0; i < keyNumber; i++) {
                target.keys[target.keyNumber++] = keys[i];
            }
        }
    }

    Node root;
    int t; // 树中节点的最小度数
    final int MIN_KEY_NUMBER;// 最小key的数目
    final int MAX_KEY_NUMBER;// 最大key的数目

    public BTree() {
        this(2);
    }

    public BTree(int t) {
        this.t = t;
        root = new Node(t);
        MAX_KEY_NUMBER = 2 * t - 1;
        MIN_KEY_NUMBER = t - 1;
    }

    // 是否存在
    public boolean contains(int key) {
        return root.get(key) != null;
    }

    // 新增
    // 规则：
    // 遇到叶子节点，则加入key
    // 如果叶子中key数目达到最大值，则进行分裂
    public void put(int key) {
        put(root, key, null, 0);
    }

    private void put(Node node, int key, Node parent, int index) {
        int i = 0;
        while (i < node.keyNumber) {
            if (node.keys[i] == key) {
                return;
            }
            if (node.keys[i] > key) {
                break; // 找到了插入位置，即为此时的i
            }
            i++;
        }

        if (node.leaf) {
            node.insertKey(key, i);
        } else {
            put(node.children[i], key, node, i);
        }
        if (node.keyNumber == MAX_KEY_NUMBER) {
            split(node, parent, index);
        }
    }

    // 分裂
    private void split(Node left, Node parent, int index) {
        if (parent == null) { // 说明分裂的是根节点
            Node newRoot = new Node(t);
            newRoot.leaf = false;
            newRoot.insertChild(left, 0);
            this.root = newRoot;
            parent = newRoot;
        }
        Node right = new Node(t);
        right.leaf = left.leaf;
        System.arraycopy(left.keys, t, right.keys, 0, t - 1);
        // 分裂节点不是叶子节点
        if (!left.leaf) {
            System.arraycopy(left.children, t, right.children, 0 ,t);
        }
        right.keyNumber = t - 1;
        left.keyNumber = t - 1;

        // 将中间的key插入到父节点
        int mid = left.keys[t - 1];
        parent.insertKey(mid, index);

        // 将right节点插入到父亲的孩子
        parent.insertChild(right, index + 1);
    }

    // 删除
    public void remove(int key) {
        remove(null, root, 0, key);
    }

    private void remove(Node parent, Node node, int index, int key) {
        int i = 0;
        while (i < node.keyNumber) {
            if (node.keys[i] >= key) {
                break;
            }
            i++;
        }
        // 找到了，则i代表待删除key的索引
        // 没找到，代表到第i个孩子中继续查找 case 2
        if (node.leaf) {
            if (!found(node, key, i)) { // case 1
                return;
            } else {

                node.removeKey(i);
            }
        } else {
            if (!found(node, key, i)) {
                // case 3
                remove(node, node.children[i], i, key);
            } else {
                // case 4
                // 找到后继key
                Node s = node.children[i + 1];
                while (!s.leaf) {
                    s = s.children[0];
                }
                int sKey = s.keys[0];
                // 替换待删除key
                node.keys[i] = sKey;
                // 删除后继key
                remove(node, node.children[i + 1], i + 1, sKey);
            }
        }

        if (node.keyNumber < MIN_KEY_NUMBER) {
            // case 5 case 6
            balance(parent, node, index);
        }
    }

    private void balance(Node parent, Node x, int i) {
        // case 6 ：根节点
        if (x == root) {
            if (root.keyNumber == 0 && root.children[0] != null) {
                root = root.children[0];
            }
            return;
        }
        Node left = parent.childLeftSibling(i);
        Node right = parent.childRightSibling(i);
        // case 5-1 ：左边富裕，右旋
        if (left != null && left.keyNumber > MIN_KEY_NUMBER) {
            x.insertKey(parent.keys[i - 1], 0);
            if (!left.leaf) {
                x.insertChild(left.removeRightMostChild(), 0);
            }
            parent.keys[i - 1] = left.removeRightMostKey();
            return;
        }
        // case 5-2 ： 右边富裕，左旋
        if (right != null && right.keyNumber > MIN_KEY_NUMBER) {
            x.insertKey(parent.keys[i], x.keyNumber);
            if (!right.leaf) {
                x.insertChild(right.removeLeftMostChild(), x.keyNumber + 1);
            }
            parent.keys[i] = right.removeLeftMostKey();
            return;
        }
        // case 5-3 ： 合并
        if (left != null) {
            // 向左兄弟合并
            parent.removeChild(i);
            left.insertKey(parent.removeKey(i - 1), left.keyNumber);
            x.moveToTarget(left);
        } else {
            // 向自己合并
            parent.removeChild(i + 1);
            x.insertKey(parent.removeKey(i), x.keyNumber);
            right.moveToTarget(x);
        }

    }

    private static boolean found(Node node, int key, int i) {
        return i < node.keyNumber && node.keys[i] == key;
    }
}
