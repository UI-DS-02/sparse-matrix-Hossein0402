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