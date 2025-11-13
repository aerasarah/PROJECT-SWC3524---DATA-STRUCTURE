
/**
 * Write a description of class Firdauze here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.ArrayList;

public class Firdauze {

    public static void main(String[] args) {

        // =======================
        // Min-Heap Test
        // =======================
        MinHeap minHeap = new MinHeap();
        minHeap.insert(10);
        minHeap.insert(3);
        minHeap.insert(15);
        System.out.println("Min-Heap Extract Min: " + minHeap.extractMin());

        // =======================
        // Max-Heap Test
        // =======================
        MaxHeap maxHeap = new MaxHeap();
        maxHeap.insert(10);
        maxHeap.insert(3);
        maxHeap.insert(15);
        System.out.println("Max-Heap Extract Max: " + maxHeap.extractMax());

        // =======================
        // Splay Tree Test
        // =======================
        SplayTree tree = new SplayTree();
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        System.out.println("Splay Tree Search (10 found): " + tree.search(10));
    }

    // ============================
    // MIN HEAP
    // ============================
    static class MinHeap {
        private ArrayList<Integer> heap = new ArrayList<>();

        private void swap(int i, int j) {
            int temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }

        public void insert(int value) {
            heap.add(value);
            int current = heap.size() - 1;

            while (current > 0) {
                int parent = (current - 1) / 2;
                if (heap.get(current) >= heap.get(parent)) break;
                swap(current, parent);
                current = parent;
            }
        }

        public int extractMin() {
            if (heap.isEmpty()) return -1;

            int min = heap.get(0);
            heap.set(0, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);

            int current = 0;
            while (true) {
                int left = 2 * current + 1;
                int right = 2 * current + 2;
                int smallest = current;

                if (left < heap.size() && heap.get(left) < heap.get(smallest))
                    smallest = left;

                if (right < heap.size() && heap.get(right) < heap.get(smallest))
                    smallest = right;

                if (smallest == current) break;

                swap(current, smallest);
                current = smallest;
            }

            return min;
        }
    }

    // ============================
    // MAX HEAP
    // ============================
    static class MaxHeap {
        private ArrayList<Integer> heap = new ArrayList<>();

        private void swap(int i, int j) {
            int temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }

        public void insert(int value) {
            heap.add(value);
            int current = heap.size() - 1;

            while (current > 0) {
                int parent = (current - 1) / 2;
                if (heap.get(current) <= heap.get(parent)) break;
                swap(current, parent);
                current = parent;
            }
        }

        public int extractMax() {
            if (heap.isEmpty()) return -1;

            int max = heap.get(0);
            heap.set(0, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);

            int current = 0;
            while (true) {
                int left = 2 * current + 1;
                int right = 2 * current + 2;
                int largest = current;

                if (left < heap.size() && heap.get(left) > heap.get(largest))
                    largest = left;

                if (right < heap.size() && heap.get(right) > heap.get(largest))
                    largest = right;

                if (largest == current) break;

                swap(current, largest);
                current = largest;
            }

            return max;
        }
    }

    // ============================
    // SPLAY TREE
    // ============================
    static class SplayTree {

        class Node {
            int key;
            Node left, right;
            Node(int k) { key = k; }
        }

        private Node root;

        private Node rotateRight(Node x) {
            Node y = x.left;
            x.left = y.right;
            y.right = x;
            return y;
        }

        private Node rotateLeft(Node x) {
            Node y = x.right;
            x.right = y.left;
            y.left = x;
            return y;
        }

        private Node splay(Node root, int key) {
            if (root == null || root.key == key) return root;

            if (key < root.key) {
                if (root.left == null) return root;

                if (key < root.left.key) {
                    root.left.left = splay(root.left.left, key);
                    root = rotateRight(root);
                }
                else if (key > root.left.key) {
                    root.left.right = splay(root.left.right, key);
                    if (root.left.right != null)
                        root.left = rotateLeft(root.left);
                }
                return (root.left == null) ? root : rotateRight(root);
            }
            else {
                if (root.right == null) return root;

                if (key < root.right.key) {
                    root.right.left = splay(root.right.left, key);
                    if (root.right.left != null)
                        root.right = rotateRight(root.right);
                }
                else if (key > root.right.key) {
                    root.right.right = splay(root.right.right, key);
                    root = rotateLeft(root);
                }
                return (root.right == null) ? root : rotateLeft(root);
            }
        }

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
            }
            else {
                newNode.left = root;
                newNode.right = root.right;
                root.right = null;
            }
            root = newNode;
        }

        public boolean search(int key) {
            root = splay(root, key);
            return (root != null && root.key == key);
        }
    }
}
