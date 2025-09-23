import java.util.InputMismatchException;
import java.util.Scanner;

public class TUI {
    private static final Scanner scan = new Scanner(System.in);

    // Menu choice helper
    public static int choices(String title, String[] options) {
        while (true) {
            clear();
            print("<" + title + ">");
            for (int i = 0; i < options.length; i++) {
                print("(" + i + "): " + options[i]);
            }

            System.out.print("choice: ");
            try {
                int choice = scan.nextInt();
                scan.nextLine();
                if (choice >= 0 && choice < options.length || choice == 55 || choice == 67) {
                    return choice;
                } else {
                    print("Invalid choice: " + choice + " (out of range). Press enter to retry.");
                    scan.nextLine();
                }
            } catch (InputMismatchException ime) {
                scan.nextLine(); // eat bad input
                print("bad input, try again");
                scan.nextLine();
            }
        }
    }

    // Clears the terminal
    public static void clear() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public static void print(String input) {
        System.out.println(input);
    }

    public static void printInline(String input) {
        System.out.print(input);
    }

    public static String prompt(String message) {
        System.out.print(message + ": ");
        return scan.nextLine();
    }
}
