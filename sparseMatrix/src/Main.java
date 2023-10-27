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
        sparseMatrix.removingItem(1, 2);
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
        System.out.println();
        sparseMatrix.update(9, 2, 11);
        sparseMatrix.matrixShowing();
        System.out.println();
        sparseMatrix.sparseShowing();
        sparseMatrix.savingDataMatrix("little.csv");
        sparseMatrix.savingDataInSparseMatrix("gig.csv");
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

    public boolean search(int value) {
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.list) {
            if (doublyLinkedLists.search(value))
                return true;
        }
        return false;
    }

    public void update(int row, int column, int newValue) {
        int checkRow = 0;
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.list) {
            if (row == checkRow) {
                doublyLinkedLists.updating(column, newValue);
                return;
            } else checkRow++;
        }
    }

    public void savingDataMatrix(String url) throws IOException {
        FileWriter fileWriter = new FileWriter(url);
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.list) {
            doublyLinkedLists.savingDataMatrix(columns, fileWriter);
        }
        fileWriter.close();
    }

    public void savingDataInSparseMatrix(String url) throws IOException {
        int row = 0;
        FileWriter fileWriter = new FileWriter(url);
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.list) {
            doublyLinkedLists.savingDataInSparseMatrix(fileWriter,row);
            row++;
        }
        fileWriter.close();
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

    public Node<E> getHead() {
        return head;
    }

    public void remove(int column) {
        Node<E> node = this.head;
        while (true) {
            if (node.next.column == column) {
                Node<E> temp = node.next.next;
                node.setNext(node.next.next);
                temp.setPrev(node);
                size--;
                return;
            } else node = node.next;
        }
    }

    public void addBetween(E data, int column, Node<E> previous, Node<E> next) {
        Node<E> newNode = new Node<>(data, column, previous, next);
        previous.setNext(newNode);
        next.setPrev(newNode);
        size++;
    }

    public void addLast(E data, int column) {
        addBetween(data, column, trailer.prev, trailer);
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

    public boolean search(E value) {
        Node<E> current = this.getHead().next;
        while (current.data != null) {
            if (current.data.equals(value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void updating(int column, E newValue) {
        Node<E> current = this.getHead().next;
        while (true) {
            if (current.column == column) {
                current.setData(newValue);
                return;
            } else current = current.next;
        }
    }

    public void savingDataMatrix(int columns, FileWriter fileWriter) throws IOException {
        Node<E> current = this.getHead().next;
        for (int j = 0; j < columns; j++) {
            if (current.column != -2 && j == current.column) {
                fileWriter.append(String.valueOf(current.data)).append(",");
                current = current.next;
            } else fileWriter.append(0 + ",");
        }
        fileWriter.append("\n");
    }

    public void savingDataInSparseMatrix(FileWriter fileWriter, int row) throws IOException {
        Node<E> current = this.getHead().next;
        while (current.data != null) {
            fileWriter.append(row + "," + current.column + "," + current.data + "\n");
            current = current.next;
        }
    }
}