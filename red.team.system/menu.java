import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class menu {
    String signed = "RED";
    // one scanner for the whole program (DO NOT REMOVE)
    static Scanner scan = new Scanner(System.in);

    static IP_RED ip_tools;
    tools tools;
    Terminal kitty;
    SSH ssh_tool;
    /**
     * Presents a numbered list of options and returns a valid index.
     * Keeps prompting until a valid integer in range is entered.
     */
    public int choices(String Title, String[] options) {
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

    /**
     * Main menu loop. Returns when user chooses "exit".
     */
    public int main() {
        if (ip_tools == null) ip_tools = new IP_RED();
        boolean running = true;

        while (running) {
            String ip_Display = ip_tools.getIp(); // uses getter/setter :>
            int choice = choices("MAIN <IP: " + ip_Display + ">", new String[]{"IP", "TOOLS", "SSH", "terminal", "exit"});

            try {
                if (choice == 0) {
                    ip_tools.main();
                } else if (choice == 1) {
                    if (tools == null) tools = new tools();
                    tools.main();
                } else if (choice == 2) {
                    if (ssh_tool == null) ssh_tool = new SSH();
                    ssh_tool.main();
                } else if (choice == 3) {
                    if (kitty == null) kitty = new Terminal();
                    kitty.main();
                } else if (choice == 4) {
                    running = false; //end loop
                    return(4); //exit return code
                }
            } catch (FileNotFoundException fnfe) {
                print("File error: " + fnfe.getMessage());
                fnfe.printStackTrace(); //idk bruh i just took this part from stack overflow. yell at me later
            } catch (InterruptedException ie) {
                print("Interrupted: " + ie.getMessage());
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                print("Unexpected error: " + e.getMessage());
                e.printStackTrace();
            }
            
        }
        return 67; //default return code xd
    }

    public void clear() {
        System.out.println("\033[H\033[2J");
    }

    public void print(String input) {
        System.out.println(input);
    }

    public void print(String input, int a) {
        System.out.print(input);
    }

    public static String prompt(String message) {
        System.out.print(message + ": ");
        return scan.nextLine();
    }

}
