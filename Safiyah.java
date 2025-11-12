import java.util.PriorityQueue;
import java.util.Collections;

public class Safiyah {

    // ===================== MIN HEAP =====================
    static class MinHeap {
        PriorityQueue<Integer> heap = new PriorityQueue<>();

        public void insert(int value) {
            heap.add(value);
        }

        public int extractMin() {
            if (heap.isEmpty()) {
                System.out.println("Heap is empty!");
                return -1;
            }
            return heap.poll(); // Removes and returns the smallest element
        }
    }

    // ===================== MAX HEAP =====================
    static class MaxHeap {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());

        public void insert(int value) {
            heap.add(value);
        }

        public int extractMax() {
            if (heap.isEmpty()) {
                System.out.println("Heap is empty!");
                return -1;
            }
            return heap.poll(); // Removes and returns the largest element
        }
    }

    // ===================== SPLAY TREE =====================
    static class SplayTree {
        class Node {
            int key;
            Node left, right;
            Node(int key) {
                this.key = key;
            }
        }

        private Node root;

        // ---------- Rotation operations ----------
        private Node rightRotate(Node x) {
            Node y = x.left;
            x.left = y.right;
            y.right = x;
            return y;
        }

        private Node leftRotate(Node x) {
            Node y = x.right;
            x.right = y.left;
            y.left = x;
            return y;
        }

        // ---------- Splaying operation ----------
        private Node splay(Node root, int key) {
            if (root == null || root.key == key)
                return root;

            // Key in left subtree
            if (key < root.key) {
                if (root.left == null) return root;

                if (key < root.left.key) {
                    root.left.left = splay(root.left.left, key);
                    root = rightRotate(root);
                } else if (key > root.left.key) {
                    root.left.right = splay(root.left.right, key);
                    if (root.left.right != null)
                        root.left = leftRotate(root.left);
                }

                return (root.left == null) ? root : rightRotate(root);
            }

            // Key in right subtree
            else {
                if (root.right == null) return root;

                if (key > root.right.key) {
                    root.right.right = splay(root.right.right, key);
                    root = leftRotate(root);
                } else if (key < root.right.key) {
                    root.right.left = splay(root.right.left, key);
                    if (root.right.left != null)
                        root.right = rightRotate(root.right);
                }

                return (root.right == null) ? root : leftRotate(root);
            }
        }

        // ---------- Insertion ----------
        public void insert(int key) {
            if (root == null) {
                root = new Node(key);
                return;
            }

            root = splay(root, key);

            if (root.key == key) return;

            Node newNode = new Node(key);
            if (key < root.key) {
                newNode.right = root;
                newNode.left = root.left;
                root.left = null;
            } else {
                newNode.left = root;
                newNode.right = root.right;
                root.right = null;
            }
            root = newNode;
        }

        // ---------- Search ----------
        public boolean search(int key) {
            root = splay(root, key);
            return root != null && root.key == key;
        }
    }

    // ===================== MAIN METHOD =====================
    public static void main(String[] args) {

        // ---------- Min-Heap ----------
        MinHeap minHeap = new MinHeap();
        minHeap.insert(10);
        minHeap.insert(3);
        minHeap.insert(15);
        System.out.println("Min-Heap Extract Min: " + minHeap.extractMin());

        // ---------- Max-Heap ----------
        MaxHeap maxHeap = new MaxHeap();
        maxHeap.insert(10);
        maxHeap.insert(3);
        maxHeap.insert(15);
        System.out.println("Max-Heap Extract Max: " + maxHeap.extractMax());

        // ---------- Splay Tree ----------
        SplayTree tree = new SplayTree();
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        System.out.println("Splay Tree Search (10 found): " + tree.search(10));
    }
}
