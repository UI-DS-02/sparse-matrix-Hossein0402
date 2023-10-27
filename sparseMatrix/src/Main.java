import java.io.*;
import java.util.ArrayList;
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

        SparseMatrix sparseMatrix = new SparseMatrix(matrix, numRows, numCols);
        sparseMatrix.matrixShowing();
        System.out.println();
        System.out.println();
        sparseMatrix.sparseShowing();
        System.out.println();
        System.out.println();
        sparseMatrix.insertValue(2, 4, 2);
        sparseMatrix.matrixShowing();
        System.out.println();
        System.out.println();
        sparseMatrix.removingItem(1,2);
        System.out.println();
        System.out.println();
        sparseMatrix.sparseShowing();
        System.out.println();
        System.out.println();
        sparseMatrix.matrixShowing();
        System.out.println();
        System.out.println();
        System.out.println(sparseMatrix.search(101));
        System.out.println(sparseMatrix.search(754));
    }
}

/////**************
class SparseMatrix {
    private ArrayList<DoublyLinkedList<Integer>> list = new ArrayList<>();
    int rows;
    int columns;

    public SparseMatrix(int[][] matrix, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        for (int i = 0; i < rows; i++) {
            DoublyLinkedList<Integer> linkList = new DoublyLinkedList<>();
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] != 0)
                    linkList.addLast(matrix[i][j], j);
            }
            list.add(linkList);
        }
    }

    public void sparseShowing() {
        int row = 0;
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.list) {
            doublyLinkedLists.showingData(row);
            row++;
        }
    }

    public void matrixShowing() {
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.list) {
            doublyLinkedLists.matrixShowing(columns);
        }
    }

    public void insertValue(int row, int column, int value) {
        int checkRow = 0;
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.list) {
            if (row == checkRow) {
                doublyLinkedLists.addI(column, value);
            }
            checkRow++;
        }
    }

    public void removingItem(int row, int column) {
        int checkRow = 0;
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.list) {
            if (row == checkRow) {
                    doublyLinkedLists.remove(column);
            }
            checkRow++;
        }
    }
    public boolean search(int value){
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.list) {
            if (doublyLinkedLists.search(value))
                return true;
        }
        return false;
    }

}


/////**************

class DoublyLinkedList<E> {
    private static class Node<E> {
        private E data;
        private int column;
        private Node<E> prev;
        private Node<E> next;

        public Node(E e, int column, Node<E> p, Node<E> n) {
            this.column = column;
            this.prev = p;
            this.next = n;
            this.data = e;
        }

        public int getColumn() {
            return column;
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
        head = new Node<>(null, -1, null, null);
        trailer = new Node<>(null, -2, head, null);
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

    public void remove(int column) {
        Node<E> node = this.head;
        while (true){
            if (node.next.column==column){
                Node<E>temp = node.next.next;
                node.setNext(node.next.next);
                temp.setPrev(node);
                size--;
                return;
            }
            else node = node.next;
        }
    }

    public void addBetween(E data, int column, Node<E> previous, Node<E> next) {
        Node<E> newNode = new Node<>(data, column, previous, next);
        previous.setNext(newNode);
        next.setPrev(newNode);
        size++;
    }

   /* public E removeFirst() {
        if (isEmpty())
            return null;
        else
            return remove(head.next);
    }*/

   /* public E removeLast() {
        if (isEmpty())
            return null;
        else return remove(trailer.prev);
    }*/

    public void addFirst(E data, int column) {
        addBetween(data, column, head, head.next);
    }

    public void addLast(E data, int column) {
        addBetween(data, column, trailer.prev, trailer);
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
        Node<E> node = this.head;
        while (true) {
            if (i < node.next.column || node.next.column == -2) {
                addBetween(e, i, node, node.next);
                return;
            }
            node = node.next;
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
            if (key == trailer)
                break;
        }
    }

    public void showingData(int row) {
        Node<E> current = this.getHead().next;
        while (current.data != null) {
            System.out.println(row + " " + current.column + " " + current.data);
            current = current.next;
        }
    }

    public void matrixShowing(int columns) {
        Node<E> current = this.getHead().next;
        for (int j = 0; j < columns; j++) {
            if (current.column != -2 && j == current.column) {
                System.out.print(current.data + " ");
                current = current.next;
            } else System.out.print(0 + " ");
        }
        System.out.println();
    }
    public boolean search(E value){
        Node<E> current = this.getHead().next;
        while (current.data != null) {
            if (current.data.equals(value)){
                return true;
            }
            current = current.next;
        }
        return false;
    }
}