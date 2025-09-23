import java.io.FileNotFoundException;

public class Main {
    //static Router router = new Router();
    static Menu red = new Menu();
    static Menu blue = new Menu();
    static router router = new router();
    static server_code server = new server_code();
    //static BlueTeam blue = new BlueTeam()

    public static void main(String[] args) throws FileNotFoundException, InterruptedException, Exception {
        server_code.encrypt("system-total/super-secret-info.dat", "va1v3");
        while (true) {
            int choice = TUI.choices("MAIN-BRANCH", new String[]{"RED-TEAM", "BLUE-TEAM<broken>", "ROUTE", "exit","extra_init"});

            if (choice == 0) {
                red.main();
            } else if (choice == 1) {
                blue.main();
            } else if (choice == 2) {
                router.init();
                router.view();
                
                TUI.prompt("");
            } else if (choice == 3) {
                return;
            } else if (choice == 4){
                server_code.encrypt("system-total/super-secret-info.dat", server_code.pass);
            }
        }
    }
}
