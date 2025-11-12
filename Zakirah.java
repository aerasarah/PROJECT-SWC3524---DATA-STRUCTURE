
/**
 * Write a description of class Zakirah here.
 *
 * @author ZAKIRAH
 * PROJECT SWC3524
 * INDIVIDUAL TASK
 */

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Zakirah
{
    //-------- Min-Heap --------
    public void runMinHeap() {
        MinHeap heap = new MinHeap();
        heap.insert(10);
        heap.insert(3);
        heap.insert(15);
        int min = heap.extractMin();
        System.out.println("Min-Heap Extract Min: " + min + "\n");
    }
    
    static class MinHeap {
        private PriorityQueue<Integer> heap = new PriorityQueue<>();

        public void insert(int value) {
            heap.add(value);
        }

        public int extractMin() {
            if (heap.isEmpty()) {
                throw new NoSuchElementException("Heap is empty");
            }
            return heap.poll();
        }
    }
    
    //-------- Max Heap --------
    public void runMaxHeap() {
        MaxHeap heap = new MaxHeap();
        heap.insert(10);
        heap.insert(3);
        heap.insert(15);
        int max = heap.extractMax();
        System.out.println("Max-Heap Extract Max: " + max + "\n");
    }
    
    static class MaxHeap {
        private PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        public void insert(int value) { heap.add(value); }
        public int extractMax() { return heap.poll(); }
    }

    // -------- Splay Tree --------
    public void runSplayTree() {
        SplayTree tree = new SplayTree();
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        boolean found = tree.search(10);
        System.out.println("Splay Tree Search (10 found): " + found);
    }
    
    static class SplayTree {
        private class Node {
            int key;
            Node left, right;

            Node(int key) {
                this.key = key;
                this.left = this.right = null;
            }
        }

        private Node root;

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
    
     // -------- Main Method --------
    public static void main(String[] args) {
            Zakirah ans = new Zakirah();
            ans.runMinHeap();
            ans.runMaxHeap();
            ans.runSplayTree();
        }
}