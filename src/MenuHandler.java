package Scientific_Calculator;

public class MenuHandler {

    public void showMainMenu() {
        System.out.println("\n---------------- MENU ----------------");
        System.out.println("1] Add      2] Sub      3] Mul      4] Div");
        System.out.println("5] Clear History     6] Undo");
        System.out.println("7] View History      8] MS");
        System.out.println("9] MR                10] MC");
        System.out.println("11] Rapid Mode       12] Exit");
        System.out.print("Your choice: ");
    }

    public void showContinueMenu() {
        System.out.print("\nContinue? (y/n): ");
    }

    public void showRapidModeHeader(double current) {
        System.out.println("\nRapid Mode Active");
        System.out.println("Current total: " + current);
    }

}
