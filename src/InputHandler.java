package Scientific_Calculator;

import  java.util.Scanner;

public class InputHandler {
    private Scanner sc = new Scanner(System.in);

    public double getValidDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.next());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    public int getValidInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        } 
    }

    public String getString() {
        return sc.next();
    }

    public char getChar() {
        return sc.next().charAt(0);
    }
}