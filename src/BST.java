import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BST<K extends Comparable<K>, V> {

    static class BSTNode<K, V> {
        V value;
        K key;
        BSTNode<K,V> left;
        BSTNode<K, V> right;

        public BSTNode(K key) {
            this.key = key;
        }


        public BSTNode(V value, K key, BSTNode<K, V> left, BSTNode<K, V> right) {
            this.value = value;
            this.key = key;
            this.left = left;

            this.right = right;
        }


        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    BSTNode<K, V> root;

    public V get(K key) {
        return get(root, key).value;
    }

    private BSTNode<K, V> get(BSTNode<K, V> node, K key) {
        if (key.compareTo(node.key) < 0) {
            return get(node.left, key);
        } else if (node.key.compareTo(key) < 0) {
            return get(node.right, key);
        } else {
            return node;
        }
    }

    // 非递归
    public V get1(K key) {
        BSTNode<K, V> node = root;
        while (node != null) {
            if (key.compareTo(node.key) < 0) {
                node = node.left;
            } else if (node.key.compareTo(key) < 0) {
                node = node.right;
            } else {
                return node.value;
            }
        }
        return null;
    }

    public V min() {
        return min(root).value;
    }

    private BSTNode<K, V> min(BSTNode<K, V> node) {
        if (node == null) {
            return null;
        }
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    public V min1() {
        var node = root;
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node.value;
    }

    public V max() {
        return max(root).value;
    }

    private BSTNode<K, V> max(BSTNode<K, V> node) {
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public void put(K key, V value) {
        BSTNode<K, V> node = root;
        BSTNode<K, V> parent = null;
        while (node != null) {
            parent = node;
            if (key.compareTo(node.key) < 0) {
                node = node.left;
            } else if (node.key.compareTo(key) < 0) {
                node = node.right;
            } else {
                node.value = value;
                return;
            }
        }
        if (parent == null) {
            root = new BSTNode<>(key, value);
            return;
        }
        BSTNode<K, V> newNode = new BSTNode<>(key, value);
        if (parent.key.compareTo(key) < 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
    }

    public V successor(K key) {
        var node = root;
        BSTNode<K, V> ancestorFromRight = null;
        while (node != null) {
            if (key.compareTo(node.key) < 0) {
                ancestorFromRight = node;
                node = node.left;
            } else if (node.key.compareTo(key) < 0) {
                node = node.right;
            } else {
                break;
            }
        }
        if (node == null) {
            return null;
        }
        if (node.right != null) {
            return min(node.right).value;
        }
        return ancestorFromRight != null ?
                ancestorFromRight.value : null;
    }

    public V predecessor(K key) {
        var node = root;
        BSTNode<K, V> ancestorFromLeft = null;
        while (node != null) {
            if (key.compareTo(node.key) < 0) {
                node = node.left;
            } else if (node.key.compareTo(key) < 0) {
                ancestorFromLeft = node;
                node = node.right;
            } else {
                break;
            }
        }
        if (node == null) {
            return null;
        }
        if (node.left != null) {
            return max(node.left).value;
        }
        return ancestorFromLeft != null ?
                ancestorFromLeft.value : null;
    }

    public V delete(K key) {
        var node = root;
        BSTNode<K, V> parent = null;
        while (node != null) {
            if (key.compareTo(node.key) < 0) {
                parent = node;
                node = node.left;
            } else if (node.key.compareTo(key) < 0) {
                parent = node;
                node = node.right;
            } else {
                break;
            }
        }
        if (node == null) {
            return null;
        }
        if (node.right == null) {
            shift(parent, node, node.left);
        } else if (node.left == null) {
            shift(parent, node, node.right);
        } else {
            var s = node.right;
            BSTNode<K, V> sp = node;
            while (s.left != null) {
                sp = s;
                s = s.left;
            }

            if (sp != node) { // 不相邻
                // 先处理后事 -- 顶上去
                shift(sp, s, s.right);
                s.right = node.right;
            }
            // 再托付s给node的父节点
            shift(parent, node, s);
            s.left = node.left;
        }
        return node.value;
    }

    /**
     * / 托孤方法
     * @param parent 被删除节点的父节点
     * @param deleted 被删除节点
     * @param child 被顶上去的节点
     */
    private void shift(BSTNode<K, V> parent, BSTNode<K, V> deleted, BSTNode<K, V> child) {
        if (parent == null) {
            root = child;
        } else if (deleted == parent.left) {
            parent.left = child;
        } else {
            parent.right = child;
        }
    }

    public V deleteRecursive(K key) {
        ArrayList<V> result = new ArrayList<>(); // 保存被删除节点的值
        root = deleteRecursive(root, key, result);
        return result.isEmpty() ? null :result.getFirst();
    }

    // 返回值表示删剩下的孩子或者null
    private BSTNode<K, V> deleteRecursive(BSTNode<K, V> node, K key, ArrayList<V> result) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            node.left = deleteRecursive(node.left, key, result);
            return node;
        }
        if (node.key.compareTo(key) < 0) {
            node.right = deleteRecursive(node.right, key, result);
            return node;
        }
        result.add(node.value);
        if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        } else {
            BSTNode<K, V> s = node.right;
            while (s.left != null) {
                s = s.left;
            }
            s.right = deleteRecursive(node.right, s.key, new ArrayList<>());
            s.left = node.left;
            return s;
        }
    }

    // 小于key的所有值
    public List<V> less(K key) {
        ArrayList<V> result = new ArrayList<>();
        BSTNode<K, V> p = root;
        LinkedList<BSTNode<K, V>> stack = new LinkedList<>();
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                BSTNode<K, V> pop = stack.pop();
                if (pop.key.compareTo(key) < 0) {
                    result.add(pop.value);
                } else {
                    break;
                }
                p = pop.right;
            }
        }
        return result;
    }

    // 如果找的是比要找的key大的所有值，则可以进行反向前序遍历
    // 避免了多余的比较

    public static void main(String[] args) {
        BSTNode n1 = new BSTNode<>(1, 1);
        var n3 = new BSTNode<>(3, 3);
        var n2 = new BSTNode<>(2, 2, n1, n3);
        var n5 = new BSTNode<>(5, 5);
        var n6 = new BSTNode<>(6, 6, n5, null);
        var n7 = new BSTNode<>(7, 7, n6, null);
        var root1 = new BSTNode<>(4, 4, n2, n7);

        BST tree = new BST<>();
        tree.root = root1;

        tree.deleteRecursive(7);
        System.out.println(tree.root.right.value);
    }
}
