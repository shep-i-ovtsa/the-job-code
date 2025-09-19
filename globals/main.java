//import blue team classes, red team classes, valve n co classes
import java.util.InputMismatchException;
import java.util.Scanner;
public class main {
    static Scanner scan = new Scanner(System.in);
   
    public static void main(String[] args) {
        while(true){
            //initiates the tui for red team and blue team on choice
            int choice = choices("MAIN-BRANCH", new String[]{"RED-TEAM","BLUE-TEAM","exit"});
        
            if (choice == 0){
                //red-team.main();
            } else if (choice == 1){
                //blue-team.main();
            } else if (choice == 2){
                return;
            }
        }
        
    }
    public static int choices(String Title, String[] options) {
        while (true) {
            clear();
            print("<" + Title + ">");
            for (int i = 0; i < options.length; i++) {
                print("(" + i + "): " + options[i]);
            }

            System.out.print("choice: ");
            try {
                int choice = scan.nextInt();
                scan.nextLine(); 
                if (choice >= 0 && choice < options.length) {
                    return choice;
                } else {
                    print("Invalid choice: " + choice + " (out of range). Press enter to retry.");
                    scan.nextLine();
                }
            } catch (InputMismatchException ime) {
                scan.nextLine(); //eat the bad input
                print("bad input, try again");
                scan.nextLine();
            }
        }
    }
    public static void clear() {
        System.out.println("\033[H\033[2J");
    }

    public static void print(String input) {
        System.out.println(input);
    }

    public static void print(String input, int a) {
        System.out.print(input);
    }

    public static String prompt(String message) {
        System.out.print(message + ": ");
        return scan.nextLine();
    }

}



