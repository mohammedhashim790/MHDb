package com.mhdb.mhdb.Bloc;

import java.util.Scanner;

public class DBScanner {
    private final Scanner scanner;

    /**
     * Constructs a DBScanner object and initializes the scanner.
     */
    public DBScanner() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a single line of input from the user.
     *
     * @return The input string provided by the user.
     */
    public String nextLine() {
        System.err.print("MHDb > "); // Display command prompt
        return scanner.nextLine();
    }

    /**
     * Reads an integer input from the user.
     * If the input is not an integer, it prompts the user to re-enter a valid integer.
     *
     * @return The integer input provided by the user.
     */
    public int nextInt() {
        System.out.print("MHDb > ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            System.out.print("MHDb > ");
            scanner.next(); // Consume invalid input
        }
        return scanner.nextInt();
    }

    /**
     * Reads a double input from the user.
     * If the input is not a valid double, it prompts the user to re-enter a valid number.
     *
     * @return The double input provided by the user.
     */
    public double nextDouble() {
        System.out.print("MHDb > ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a number.");
            System.out.print("MHDb > ");
            scanner.next(); // Consume invalid input
        }
        return scanner.nextDouble();
    }

    /**
     * Closes the scanner, releasing any resources associated with it.
     */
    public void close() {
        scanner.close();
    }
}
