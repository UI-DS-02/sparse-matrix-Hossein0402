import org.w3c.dom.Node;

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
        sparseMatrix.sparseShowing();
        System.out.println();
        System.out.println();
        sparseMatrix.removingItem(3, 1);
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
/*        sparseMatrix.update(9, 2, 11);
        sparseMatrix.matrixShowing();
        System.out.println();
        sparseMatrix.sparseShowing();
        sparseMatrix.savingDataMatrix("little.csv");
        sparseMatrix.savingDataInSparseMatrix("gig.csv");*/
    }
}

/////**************
class SparseMatrix {
    private ArrayList<DoublyLinkedList<Integer>> listRows = new ArrayList<>();
    private ArrayList<DoublyLinkedList<Integer>> listColumns = new ArrayList<>();
    int rows;
    int columns;

    public SparseMatrix(int[][] matrix, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        for (int i = 0; i < rows; i++) {
            DoublyLinkedList<Integer> linkList = new DoublyLinkedList<>();
            for (int j = 0; j < columns; j++) {
                if (i == 0) {
                    DoublyLinkedList<Integer> linkListColumn = new DoublyLinkedList<>();
                    listColumns.add(linkListColumn);
                }
                if (matrix[i][j] != 0) {
                    linkList.addLastRow(matrix[i][j], i, j);
                    listColumns.get(j).addLastColumn(linkList.getLastInRow());
                }
            }
            listRows.add(linkList);
        }
    }

    public void sparseShowing() {
        int row = 0;
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.listRows) {
            doublyLinkedLists.showingData(row);
            row++;
        }
    }

    public void matrixShowing() {
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.listRows) {
            doublyLinkedLists.matrixShowing(columns);
        }
    }

    public void insertValue(int row, int column, int value) {
        int checkRow = 0;
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.listRows) {
            if (row == checkRow) {
                doublyLinkedLists.addI(row, column, value, this.listColumns);
                break;
            }
            checkRow++;
        }
    }

    public void removingItem(int row, int column) {
        if (row < column) {
            int checkRow = 0;
            for (DoublyLinkedList<Integer> doublyLinkedLists : this.listRows) {
                if (row == checkRow) {
                    doublyLinkedLists.removeI(column);
                }
                checkRow++;
            }
        } else {
            int checkColumn = 0;
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.listColumns) {
            if (column == checkColumn) {
                doublyLinkedLists.removeJ(row);
            }
            checkColumn++;
        }
    }
}

    public boolean search(int value) {
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.listRows) {
            if (doublyLinkedLists.search(value))
                return true;
        }
        return false;
    }

    public void update(int row, int column, int newValue) {
        int checkRow = 0;
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.listRows) {
            if (row == checkRow) {
                doublyLinkedLists.updating(column, newValue);
                return;
            } else checkRow++;
        }
    }

    public void savingDataMatrix(String url) throws IOException {
        FileWriter fileWriter = new FileWriter(url);
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.listRows) {
            doublyLinkedLists.savingDataMatrix(columns, fileWriter);
        }
        fileWriter.close();
    }

    public void savingDataInSparseMatrix(String url) throws IOException {
        int row = 0;
        FileWriter fileWriter = new FileWriter(url);
        for (DoublyLinkedList<Integer> doublyLinkedLists : this.listRows) {
            doublyLinkedLists.savingDataInSparseMatrix(fileWriter, row);
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
        private Node<E> prevRow;
        private Node<E> prevColumn;
        private Node<E> nextColumnNode;
        private Node<E> nextRowNode;
        private int row;

        public Node(E data, int row, int column, Node<E> prevRow, Node<E> prevColumn, Node<E> nextColumnNode, Node<E> nextRowNode) {
            this.data = data;
            this.row = row;
            this.column = column;
            this.prevRow = prevRow;
            this.nextRowNode = nextRowNode;
            this.nextColumnNode = nextColumnNode;
            this.prevColumn = prevColumn;
        }

        public void setData(E data) {
            this.data = data;
        }

        public void setNextRowNode(Node<E> next) {
            this.nextRowNode = next;
        }

        public void setNextColumnNode(Node<E> nextColumnNode) {
            this.nextColumnNode = nextColumnNode;
        }

        public void setPrevColumn(Node<E> prevColumn) {
            this.prevColumn = prevColumn;
        }

        public void setPrevRow(Node<E> prev) {
            this.prevRow = prev;
        }
    }

    private int size;
    private Node<E> head;
    private Node<E> trailer;


    public DoublyLinkedList() {
        head = new Node<>(null, -1, -1, null, null, null, null);
        trailer = new Node<>(null, -2, -2, head, head, null, null);
        size = 0;
        head.setNextRowNode(trailer);
        head.setNextColumnNode(trailer);
    }

    public Node<E> getLastInRow() {
        return trailer.prevRow;
    }

    public Node<E> getLastInColumn() {
        return head.prevColumn;
    }

    public Node<E> getHead() {
        return head;
    }

    public void removeI(int column) {
        Node<E> node = this.head;
        while (true) {
            if (node.nextRowNode.column == column) {
                Node<E> temp = node.nextRowNode.nextRowNode;
                Node<E> temp1 = node.nextRowNode;
                Node<E> temp2 = node.nextRowNode.nextColumnNode;
                node.setNextRowNode(node.nextRowNode.nextRowNode);
                temp.setPrevRow(node);
                temp1.prevColumn.setNextColumnNode(temp2);
                temp2.setPrevColumn(temp1.prevColumn);
                size--;
                return;
            } else node = node.nextRowNode;
        }
    }

    public void removeJ(int row) {
        Node<E> node = this.head;
        while (true) {
            if (node.nextColumnNode.row == row) {
                Node<E> temp2 = node.nextColumnNode;
                Node<E> temp = node.nextColumnNode.nextRowNode;
                node.setNextColumnNode(temp2.nextColumnNode);
                temp2.nextColumnNode.setPrevColumn(node);
                //Node<E> temp1 = node.nextColumnNode.nextRowNode;
                temp.setPrevRow(temp2.prevRow);
                temp2.prevRow.setNextRowNode(temp);
                size--;
                return;
            } else node = node.nextColumnNode;
        }
    }

    public Node<E> addBetweenRow(E data, int row, int column, Node<E> previous, Node<E> next) {
        Node<E> newNode = new Node<>(data, row, column, previous, null, null, next);
        previous.setNextRowNode(newNode);
        next.setPrevRow(newNode);
        size++;
        return newNode;
    }

    public void addBetweenColumn(Node<E> newNode, Node<E> previous, Node<E> next) {
        newNode.setNextColumnNode(next);
        newNode.setPrevColumn(previous);
        previous.setNextColumnNode(newNode);
        next.setPrevColumn(newNode);
    }

    public void addLastRow(E data, int row, int column) {
        addBetweenRow(data, row, column, trailer.prevRow, trailer);
    }

    public void addLastColumn(Node<E> newNode) {
        addBetweenColumn(newNode, trailer.prevColumn, trailer);
    }

    public void addI(int i, int j, E e, ArrayList<DoublyLinkedList<E>> listColumn) {
        Node<E> tempAdd;
        Node<E> node = this.head;
        while (true) {
            if (j < node.nextRowNode.column || node.nextRowNode.column == -2) {
                tempAdd = addBetweenRow(e, i, j, node, node.nextRowNode);
                break;
            }
            node = node.nextRowNode;
        }
        int checkColumn = 0;
        for (DoublyLinkedList<E> doublyLinkedLists : listColumn) {
            if (j == checkColumn) {
                Node<E> node1 = doublyLinkedLists.head;
                while (true) {
                    if (tempAdd.row < node1.nextColumnNode.row || node1.nextColumnNode.row == -2) {
                        addBetweenColumn(tempAdd, node1, node1.nextColumnNode);
                        return;
                    }
                    node1 = node1.nextColumnNode;
                }
            }
            checkColumn++;
        }
    }

    public void showingData(int row) {
        Node<E> current = this.getHead().nextRowNode;
        while (current.data != null) {
            System.out.println(row + " " + current.column + " " + current.data);
            current = current.nextRowNode;
        }
    }

    public void matrixShowing(int columns) {
        Node<E> current = this.getHead().nextRowNode;
        for (int j = 0; j < columns; j++) {
            if (current.column != -2 && j == current.column) {
                System.out.print(current.data + " ");
                current = current.nextRowNode;
            } else System.out.print(0 + " ");
        }
        System.out.println();
    }

    public boolean search(E value) {
        Node<E> current = this.getHead().nextRowNode;
        while (current.data != null) {
            if (current.data.equals(value)) {
                return true;
            }
            current = current.nextRowNode;
        }
        return false;
    }

    public void updating(int column, E newValue) {
        Node<E> current = this.getHead().nextRowNode;
        while (true) {
            if (current.column == column) {
                current.setData(newValue);
                return;
            } else current = current.nextRowNode;
        }
    }

    public void savingDataMatrix(int columns, FileWriter fileWriter) throws IOException {
        Node<E> current = this.getHead().nextRowNode;
        for (int j = 0; j < columns; j++) {
            if (current.column != -2 && j == current.column) {
                fileWriter.append(String.valueOf(current.data)).append(",");
                current = current.nextRowNode;
            } else fileWriter.append(0 + ",");
        }
        fileWriter.append("\n");
    }

    public void savingDataInSparseMatrix(FileWriter fileWriter, int row) throws IOException {
        Node<E> current = this.getHead().nextRowNode;
        while (current.data != null) {
            fileWriter.append(row + "," + current.column + "," + current.data + "\n");
            current = current.nextRowNode;
        }
    }
}