package Scientific_Calculator;

public class MainController {

    private CalculateLogic logic = new CalculateLogic();
    private InputHandler input = new InputHandler();
    private HistoryManager history = new HistoryManager("history.txt");
    private static final char HALT = 'H';
    private static final int EXIT = 12; 
    private static final char LOAD = 'L';
    private static final char EXP = 'E';

    public void start() {

        boolean useCached = false;
        double cached = 0;

        while (true) {
            showMenu();
            int choice = input.getValidInt();

            if (choice == EXIT) {
                System.out.println("Exiting...");
                break;
            }
            double n1;
            char op = getOperator(choice);
            if (op == HALT) continue;
            if (op == EXP) {
                System.out.println("---- Expresion Mode ----");
                String exp = input.getString();
                double result = logic.evaluateExpression(exp);
                logic.setLastResult(result);
                history.addRecord( exp + " = " + result);
                System.out.println("Result: " + result);
                continue;
            }
            if (op == LOAD) {
                cached = logic.getLastResult();
                useCached = true;
                continue;
            }
            if (useCached) {
                n1 = cached;
                System.out.println("\nUsing Last Result as a first number: " + n1);
                useCached = false;
            } else {
                System.out.println("\nEnter First Number: ");
                n1 = input.getValidDouble();
            }

            System.out.print("Enter Second Number: ");
            double n2 = input.getValidDouble();
            if ((op == '/' || op == '%') && n2 == 0) {System.out.println("Cannot use zero.");  continue; }

            double result = logic.calculate(n1, n2, op);
            logic.setLastResult(result);

            String record = String.format("%.2f %c %.2f = %.2f", n1, op, n2, result);

            history.addRecord(record);

            System.out.println("Result: " + record);
            System.out.println("Continue with result? (y/n): ");
            char conti = input.getChar();
            if (conti == 'y' || conti == '1' || conti == 'Y') {useCached = true;} else {useCached = false;}
        }
    }

    private void showMenu() {
        System.out.println("\n----- MENU -----");
        System.out.println("1) Add");
        System.out.println("2) Subtract");
        System.out.println("3) Multiply");
        System.out.println("4) Divide");
        System.out.println("5) View History");
        System.out.println("6) Clear History");
        System.out.println("7) Undo Last");
        System.out.println("8) Use last result");
        System.out.println("9) Modulus(%)");
        System.out.println("10) Power(^)");
        System.out.println("11) Expresion Mode");
        System.out.println("12) Exit");
        System.out.print("Your Choice: ");
    }

    private char getOperator(int choice) {

        switch (choice) {
            case 1: return '+';
            case 2: return '-';
            case 3: return '*';
            case 4: return '/';
            case 5: {history.showHistory();  return HALT;}
            case 6: {history.clearHistory();   return HALT;}
            case 7: {history.undoLast();  return HALT;}
            case 8: {if (!logic.hasResult()) {
                        System.out.println("No previous result available.");
                        return HALT;
                        }
                        System.out.println("Last Result Loaded");
                        return LOAD;
                        }
            case 9: return '%';
            case 10: return '^';
            case 11: return EXP;
            default: {System.err.println("Enter valid number."); return HALT;}
        }
    }
}
