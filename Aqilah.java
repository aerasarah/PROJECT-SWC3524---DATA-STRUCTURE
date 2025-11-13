import java.util.*;

public class Aqilah {
    
    //  Run Methods 
    public void runInsertionSort() {
        int[] arr = {8, 3, 5, 1, 9, 2};
        insertionSort(arr);
        System.out.println("Sorted Array: " + Arrays.toString(arr));
    }

    public void runBinarySearch() {
        int[] arr = {1, 2, 3, 5, 8, 9};
        int index = binarySearch(arr, 5);
        System.out.println("Binary Search (5 found at index): " + index);
    }

    public void runMinHeap() {
        MinHeap heap = new MinHeap();
        heap.insert(10);
        heap.insert(3);
        heap.insert(15);
        int min = heap.extractMin();
        System.out.println("Min-Heap Extract Min: " + min);
    }

    public void runMaxHeap() {
        MaxHeap heap = new MaxHeap();
        heap.insert(10);
        heap.insert(3);
        heap.insert(15);
        int max = heap.extractMax();
        System.out.println("Max-Heap Extract Max: " + max);
    }

    public void runSplayTree() {
        SplayTree tree = new SplayTree();
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        boolean found = tree.search(10);
        System.out.println("Splay Tree Search (10 found): " + found);
    }

    //  Insertion Sort 
    public void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // Binary Search 
    public int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid; 
            }
            if (arr[mid] < target) {
                left = mid + 1;  
            } else {
                right = mid - 1; 
            }
        }
        return -1;
    }

    // Min-Heap 
    static class MinHeap {
        private ArrayList<Integer> heap;

        public MinHeap() {
            heap = new ArrayList<>();
        }

        
        public void insert(int value) {
            heap.add(value);
            heapifyUp(heap.size() - 1);
        }

        
        private void heapifyUp(int index) {
            while (index > 0 && heap.get(index) < heap.get((index - 1) / 2)) {
                Collections.swap(heap, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        
        public int extractMin() {
            if (heap.isEmpty()) {
                throw new NoSuchElementException("Heap is empty");
            }
            int min = heap.get(0);
            Collections.swap(heap, 0, heap.size() - 1);
            heap.remove(heap.size() - 1);
            heapifyDown(0);
            return min;
        }


        private void heapifyDown(int index) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < heap.size() && heap.get(left) < heap.get(smallest)) {
                smallest = left;
            }
            if (right < heap.size() && heap.get(right) < heap.get(smallest)) {
                smallest = right;
            }

            if (smallest != index) {
                Collections.swap(heap, index, smallest);
                heapifyDown(smallest);
            }
        }
    }

    // Max-Heap 
    static class MaxHeap {
        private ArrayList<Integer> heap;

        public MaxHeap() {
            heap = new ArrayList<>();
        }

        public void insert(int value) {
            heap.add(value);
            heapifyUp(heap.size() - 1);
        }

        private void heapifyUp(int index) {
            while (index > 0 && heap.get(index) > heap.get((index - 1) / 2)) {
                Collections.swap(heap, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        public int extractMax() {
            if (heap.isEmpty()) {
                throw new NoSuchElementException("Heap is empty");
            }
            int max = heap.get(0);
            Collections.swap(heap, 0, heap.size() - 1);
            heap.remove(heap.size() - 1);
            heapifyDown(0);
            return max;
        }

        private void heapifyDown(int index) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int largest = index;

            if (left < heap.size() && heap.get(left) > heap.get(largest)) {
                largest = left;
            }
            if (right < heap.size() && heap.get(right) > heap.get(largest)) {
                largest = right;
            }

            if (largest != index) {
                Collections.swap(heap, index, largest);
                heapifyDown(largest);
            }
        }
    }

    //  Splay Tree 
    static class SplayTree {
        private class Node {
            int key;
            Node left, right;

            Node(int key) {
                this.key = key;
                left = right = null;
            }
        }

        private Node root;

        public SplayTree() {
            root = null;
        }

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

        private Node splay(Node root, int key) {
            if (root == null || root.key == key) {
                return root;
            }

            if (key < root.key) {
                if (root.left == null) return root;
                if (key < root.left.key) {
                    root.left.left = splay(root.left.left, key);
                    root = rightRotate(root);
                } else if (key > root.left.key) {
                    root.left.right = splay(root.left.right, key);
                    if (root.left.right != null) {
                        root.left = leftRotate(root.left);
                    }
                }

                return (root.left == null) ? root : rightRotate(root);
            } else {
                if (root.right == null) return root;
                if (key < root.right.key) {
                    root.right.left = splay(root.right.left, key);
                    if (root.right.left != null) {
                        root.right = rightRotate(root.right);
                    }
                } else if (key > root.right.key) {
                    root.right.right = splay(root.right.right, key);
                    root = leftRotate(root);
                }

                return (root.right == null) ? root : leftRotate(root);
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
            } else {
                newNode.left = root;
                newNode.right = root.right;
                root.right = null;
            }

            root = newNode;
        }

        public boolean search(int key) {
            root = splay(root, key);
            return root != null && root.key == key;
        }
    }

    // Main Method 
    public static void main(String[] args) {
        Aqilah program = new Aqilah();
        program.runInsertionSort();
        program.runBinarySearch();
        program.runMinHeap();
        program.runMaxHeap();
        program.runSplayTree();
    }
}
