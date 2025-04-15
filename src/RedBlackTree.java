public class RedBlackTree {
    enum Color {
        RED, BLACK;
    }

    private class Node {
        int key;
        Object value;
        Node left;
        Node right;
        Node parent;
        Color color = Color.RED;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        boolean isLeftChild() {
            return parent != null && parent.left == this;
        }

        // 父节点的兄弟节点
        Node uncle() {
            if (parent == null || parent.parent == null) {
                return null;
            }
            if (parent.isLeftChild()) {
                return parent.parent.right;
            } else {
                return parent.parent.left;
            }
        }

        // 兄弟节点
        Node sibling() {
            if (parent == null) {
                return null;
            }
            if (isLeftChild()) {
                return parent.right;
            } else {
                return parent.left;
            }
        }
    }

    private Node root;

    boolean isRed(Node node) {
        return node != null && node.color == Color.RED;
    }

    boolean isBlack(Node node) {
        return !isRed(node);
    }

    // 相比AVL旋转多出来的步骤：1.处理parent 2.处理旋转后新根的父子关系
    private void rightRotate(Node node) {
        Node parent = node.parent;
        Node yellow = node.left;
        Node green = yellow.right;
        if (green != null) {
            green.parent = node;
        }
        yellow.right = node;
        yellow.parent = node.parent;
        node.left = green;
        node.parent = yellow;
        if (parent == null) {
            root = yellow;
        }else if (parent.left == node) {
            parent.left = yellow;
        } else {
            parent.right = yellow;
        }
    }

    private void leftRotate(Node node) {
        Node parent = node.parent;
        Node yellow = node.right;
        Node green = yellow.left;
        if (green != null) {
            green.parent = node;
        }
        yellow.left = node;
        yellow.parent = node.parent;
        node.right = green;
        node.parent = yellow;
        if (parent == null) {
            root = yellow;
        }else if (parent.left == node) {
            parent.left = yellow;
        } else {
            parent.right = yellow;
        }
    }

    // 插入节点
    // 先正常按照BST规则插入
    // 然后处理红红不平衡的情况
    public void put(int key, Object value) {
        Node p = root;
        Node parent = null;
        while (p != null) {
            parent = p;
            if (key < p.key) {
                p = p.left;
            } else if (p.key < key) {
                p = p.right;
            } else {
                p.value = value;
                return;
            }
        }

        Node inserted = new Node(key, value);
        if (parent == null) {
            root = inserted;
        } else if (key < parent.key) {
            parent.left = inserted;
            inserted.parent = parent;
        } else {
            parent.right = inserted;
            inserted.parent = parent;
        }
        fixRedRed(inserted);
    }

    void fixRedRed(Node x) {
        // 情况1
        if (x == root) {
            x.color = Color.BLACK;
            return;
        }
        // 情况2:
        if (isBlack(x.parent)) {
            return;
        }
        // 情况3:
        Node parent = x.parent;
        Node uncle = x.uncle();
        Node grandparent = x.parent.parent;
        if (isBlack(uncle)) {
            parent.color = Color.BLACK;
            uncle.color = Color.BLACK;
            grandparent.color = Color.RED;
            fixRedRed(grandparent);
            return;
        }
        // 情况4
        // case 1 : LL
        if (parent.isLeftChild() && x.isLeftChild()) {
            // 父亲先变黑
            parent.color = Color.BLACK;
            // 祖父变红
            grandparent.color = Color.RED;
            // 然后右旋
            rightRotate(grandparent);
        } else if (parent.isLeftChild() && !x.isLeftChild()) { // LR
            leftRotate(parent);
            x.color = Color.BLACK;
            grandparent.color = Color.RED;
            rightRotate(grandparent);
        } else if (!parent.isLeftChild() && !x.isLeftChild()) {
            // 父亲先变黑
            parent.color = Color.BLACK;
            // 祖父变红
            grandparent.color = Color.RED;
            leftRotate(grandparent);
        } else {
            rightRotate(parent);
            x.color = Color.BLACK;
            grandparent.color = Color.RED;
            leftRotate(grandparent);
        }
    }

    public void remove(int key) {
        Node deleted = find(key);
        if (deleted == null) {
            return;
        }
        remove(deleted);
    }

    private void remove(Node deleted) {
        Node replaced = findReplaced(deleted);
        Node parent = deleted.parent;
        if (replaced == null) {
            if (deleted == root) {
                root = null;
            } else {
                if (isBlack(deleted)) {

                } else {
                    // 红色叶子无需任何处理
                }
                if (deleted.isLeftChild()) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                deleted.parent = null; // 方便垃圾回收
            }

            return;
        }
        // 所删节点有一个孩子
        if (replaced.left == null || replaced.right == null) {
            if (deleted == root) {
                // 李代桃僵
                root.key = replaced.key;
                root.value = replaced.value;
                root.left = null;
                root.right = null;
            } else {
                if (deleted.isLeftChild()) {
                    parent.left = replaced;
                } else {
                    parent.right = replaced;
                }
                replaced.parent = parent;
                deleted.left = deleted.right = deleted.parent = null; // 帮助垃圾回收
                if (isBlack(deleted) && isBlack(replaced)) {

                } else {
                    replaced.color = Color.BLACK;
                }
            }
            return;
        }
        // 有两个孩子
        // 李代桃僵--将所删节点后继节点的信息交换
        // 然后把交换后的后继节点删去
        int t = deleted.key;
        deleted.key = replaced.key;
        replaced.key = t;

        Object v = deleted.value;
        deleted.value = replaced.value;
        replaced.value = v;

        remove(replaced);
    }

    private void fixDoubleBlack(Node x) {
        if (x == root) {
            return;
        }
        Node parent = x.parent;
        Node sibling = x.sibling();
        if (isRed(sibling)) {
            // 旋转
            if (x.isLeftChild()) {
                leftRotate(parent);
            } else {
                rightRotate(parent);
            }
            parent.color = Color.RED;
            sibling.color = Color.BLACK;
            fixDoubleBlack(x);
            return;
        }

        if (sibling != null) {
            // 兄弟是黑色，两个侄子也是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                sibling.color = Color.RED;
                if (isRed(parent)) {
                    parent.color = Color.BLACK;
                    return;
                } else {
                    fixDoubleBlack(parent);
                }
            } else { // 兄弟是黑色，侄子有红色的
                // LL
                if (sibling.isLeftChild() && isRed(sibling.left)) {
                    rightRotate(parent);
                    sibling.left.color = Color.BLACK;
                    sibling.color = parent.color;
                } else if (sibling.isLeftChild() && isRed(sibling.right)) {
                    // LR
                    sibling.right.color = parent.color;
                    leftRotate(sibling);
                    rightRotate(parent);
                } else if (!sibling.isLeftChild() && isRed(sibling.left)) {
                    // RL
                    sibling.left.color = parent.color;
                    rightRotate(sibling);
                    leftRotate(parent);
                } else {
                    leftRotate(parent);
                    sibling.right.color = Color.BLACK;
                    sibling.color = parent.color;
                }
                parent.color = Color.BLACK;
            }
        } else {
            fixDoubleBlack(parent);
        }
    }

    // 找到特定节点
    Node find(int key) {
        Node p = root;
        while (p != null) {
            if (key < p.key) {
                p = p.left;
            } else if (p.key < key) {
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }

    // 找到删除需要删除的节点的替代节点
    Node findReplaced(Node deleted) {
        if (deleted.left == null & deleted.right == null) {
            return null;
        }
        if (deleted.left == null) {
            return deleted.right;
        }
        if (deleted.right == null) {
            return deleted.left;
        }
        Node s = deleted.right;
        while (s.left != null) {
            s = s.left;
        }
        return s;
    }
}
