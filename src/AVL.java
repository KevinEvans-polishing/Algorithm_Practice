public class AVL {
    static class Node {
        int key;
        Object value;
        Node left, right;
        int height = 1; // leetcode约定

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public Node(int key, Object value, Node left, Node right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }


    private Node root = null;

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    // 在添加、删除、旋转后更新节点高度
    private void updateHeight(Node node) {
        int d1 = height(node.left);
        int d2 = height(node.right);
        node.height = Integer.max(d1, d2) + 1;
    }

    // 平衡因子：左子树高度-右子树高度
    // 返回0、1、或者-1，都表示节点平衡
    // 否则需要旋转
    // 大于1:表示左边比较高
    // 小于-1:表示右边比较高
    private int balanceFactor(Node node) {
        return height(node.left) - height(node.right);
    }

    // 参数为要旋转的节点
    // 返回的是新的根节点
    private Node rightRotated(Node red) {
        Node yellow = red.left;
        red.left = yellow.right;
        yellow.right =red;
        updateHeight(red);
        updateHeight(yellow);
        return yellow;
    }

    private Node leftRotated(Node red) {
        Node yellow = red.right;
        red.right = yellow.left;
        yellow.left = red;
        updateHeight(red);
        updateHeight(yellow);
        return yellow;
    }

    private Node leftRightRotated(Node node) {
        // 先失衡节点的左子树左旋，然后从失衡节点右旋
        node.left = leftRotated(node.left);
        return rightRotated(node);
    }

    private Node rightLeftRotated(Node node) {
        node.right = rightRotated(node.right);
        return leftRotated(node);
    }

    private Node balance(Node node) {
        if (node == null) {
            return null;
        }

        int bf = balanceFactor(node);
        int l_bf = balanceFactor(node.left);
        int r_bf = balanceFactor(node.right);
        if (bf > 1 && l_bf >= 0) {
            return rightRotated(node);
        } else if (bf > 1 && l_bf < 0) {
            return leftRightRotated(node);
        } else if (bf < -1 && r_bf > 0) {
            return rightLeftRotated(node);
        } else if (bf < -1 && r_bf <= 0) {
            return leftRotated(node);
        } else {
            return node;
        }
    }

    public void put(int key, Object value) {
        root = put(root, key ,value);
    }

    private Node put(Node node, int key, Object value) {
        if (node == null) {
            return new Node(key, value);
        }
        if (node.key < key) {
            node.right = put(node.right, key, value);
        } else if (key < node.key) {
            node.left = put(node.left, key ,value);
        } else {
            node.value = value;
            return node;
        }
        updateHeight(node);
        return balance(node);
    }

    public void remove(int key) {
        root = remove(root, key);
    }

    // 返回的是删除该节点后整个树剩下的节点
    private Node remove(Node node, int key) {
        if (node == null) {
            return null;
        }
        if (node.key < key) {
            node.right = remove(node.right, key);
        } else if (key < node.key) {
            node.left = remove(node.left, key);
        } else {
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                node = node.right;
            } else if (node.right == null) {
                node = node.left;
            } else {
                // 注意这里与BST的分情况讨论进行区别
                // 在BST中分类讨论是由于两种情况下的处理不一样
                // 但是在这里我们利用递归进行了处理统一化
                // 所以不用再分两种情况进行讨论
                Node s = node.right; // 后继节点
                while (s.left != null) {
                    s = s.left;
                }
                s.right = remove(node.right, s.key);
                s.left = node.left;
                node = s;
            }
        }
        updateHeight(node);
        return balance(node);
    }
}
