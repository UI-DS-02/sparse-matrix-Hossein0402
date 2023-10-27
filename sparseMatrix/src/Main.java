import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("M(10,5).csv"));

        int numRows = 0;
        int numCols = 0;

        // Count the number of rows and columns in the matrix
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(",");
            numCols = data.length;
            numRows++;
        }

        // Create a 2D array with the appropriate size
        int[][] matrix = new int[numRows][numCols];

        // Reset the scanner to read from the beginning of the file
        scanner = new Scanner(new File("M(10,5).csv"));

        // Read the matrix values and store them in the 2D array
        int row = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(",");

            for (int col = 0; col < numCols; col++) {
                matrix[row][col] = Integer.parseInt(data[col]);
            }

            row++;
        }
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }

    }
}

class DoublyLinkedList<E> {
    private static class Node<E> {
        private E data;
        private Node<E> prev;
        private Node<E> next;

        public Node(E e, Node<E> p, Node<E> n) {
            this.prev = p;
            this.next = n;
            this.data = e;
        }

        public E getData() {
            return data;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setData(E data) {
            this.data = data;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }
    }

    private int size;
    private Node<E> head;
    private Node<E> trailer;

    public DoublyLinkedList() {
        head = new Node<>(null, null, null);
        trailer = new Node<>(null, head, null);
        size = 0;
        head.setNext(trailer);
    }

    public int getSize() {
        return size;
    }

    public Node<E> getHead() {
        return head;
    }

    public Node<E> getTrailer() {
        return trailer;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E getFirst() {
        if (isEmpty())
            return null;
        else return this.head.next.getData();
    }

    public E getLast() {
        if (isEmpty())
            return null;
        else
            return this.trailer.prev.getData();
    }

    public E remove(Node<E> node) {
        Node<E> previous = node.prev;
        Node<E> next = node.next;
        previous.setNext(next);
        next.setPrev(previous);
        size--;
        return node.getData();
    }

    public void addBetween(E data, Node<E> previous, Node<E> next) {
        Node<E> newNode = new Node<>(data, previous, next);
        previous.setNext(newNode);
        next.setPrev(newNode);
        size++;
    }

    public E removeFirst() {
        if (isEmpty())
            return null;
        else
            return remove(head.next);
    }

    public E removeLast() {
        if (isEmpty())
            return null;
        else return remove(trailer.prev);
    }

    public void addFirst(E data) {
        addBetween(data, head, head.next);
    }

    public void addLast(E data) {
        addBetween(data, trailer.prev, trailer);
    }

    public E getI(int i) {
        Node<E> element = this.head.next;
        for (int j = 0; j <= i; j++) {
            if (i - j == 0)
                break;
            else element = element.next;
        }
        return element.data;
    }

    public void addI(int i, E e) {
        i++;
        Node<E> element = this.head.next;
        for (int j = 0; j < i; j++) {
            if (i - j == 2) {
                addBetween(e, element, element.next);
                //size++;
                break;
            } else if (i == 1) {
                addFirst(e);
                break;
            } else element = element.next;
        }
    }

    public E removeI(int i) {
        Node<E> element = this.head.next;
        Node<E> nextNode;
        for (int j = 0; j < i; j++) {
            if (i - j == 1) {
                nextNode = element.next;
                element.prev.setNext(nextNode);
                nextNode.setPrev(element.prev);
                size--;
                return element.getData();
            } else element = element.next;
        }
        return null;
    }

    public void insertionSort() {
        Node<E> key = this.head.next.next;
        Node<E> keyNext = key.next;
        for (int i = 0; i < size; i++) {
            Node<E> key1 = key.prev;
            while (key1 != head && (int) key1.data > (int) key.data) {
                if (key1.next == key) {
                    key1.setNext(key.next);
                    key.next.setPrev(key1);
                }
                key1 = key1.prev;
            }
            if (key.prev != key1) {
                key.setNext(key1.next);
                key1.next.setPrev(key);
                key1.setNext(key);
                key.setPrev(key1);
            }
            key = keyNext;
            keyNext = keyNext.next;
            if (key==trailer)
                break;
        }
    }

    public void showingData() {
        Node<E> current = this.getHead().next;
        while (current.data != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }
}