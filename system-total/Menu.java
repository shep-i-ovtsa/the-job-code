import java.io.FileNotFoundException;

public class Menu {
    String signed = "RED";

    static IP_RED ip_tools;
    Tools tools;
    Terminal kitty;

    public int main() {
        if (ip_tools == null) ip_tools = new IP_RED();
        boolean running = true;

        while (running) {
            String ip_Display = ip_tools.getIp();
            int choice = TUI.choices("MAIN <IP: " + ip_Display + ">", 
                                     new String[]{"IP", "TOOLS", "terminal", "exit"});

            try {
                if (choice == 0) {
                    ip_tools.main();
                } else if (choice == 1) {
                    if (tools == null) tools = new Tools();
                    tools.main();
                } else if (choice == 2) {
                    if (kitty == null) kitty = new Terminal();
                    kitty.main();
                } else if (choice == 3) {
                    running = false;
                    return 4;
                }
            } catch (FileNotFoundException fnfe) {
                TUI.print("File error: " + fnfe.getMessage());
                fnfe.printStackTrace();
            } catch (InterruptedException ie) {
                TUI.print("Interrupted: " + ie.getMessage());
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                TUI.print("Unexpected error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return 67;
    }
}
