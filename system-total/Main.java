import java.io.FileNotFoundException;

public class Main {
    static Menu red = new Menu();
    static Menu blue = new Menu();
    static router router = new router();
    static server_code server = new server_code();
    //static BlueTeam blue = new BlueTeam()

    public static void main(String[] args) throws FileNotFoundException, InterruptedException, Exception {
        server_code.encrypt("system-total/super-secret-info.dat", "va1v3");
        router.init(false);
        
        while (true) {
            int choice = TUI.choices("MAIN-BRANCH", new String[]{"RED-TEAM", "BLUE-TEAM<broken>", "ROUTE", "exit","extra_init","serv"});

            if (choice == 0) {
                red.main("RED");
            } else if (choice == 1) {
                blue.main("BLUE");
            } else if (choice == 2) {
                
                router.init(true);
                router.view(TUI.prompt("Password to route table"));
                TUI.prompt("");
            } else if (choice == 3) {
                return;
            } else if (choice == 4){
                server_code.encrypt(TUI.prompt("ENTER DIRECTORY:"), router.get_pass(TUI.prompt("ENTER USER")));
            } else if (choice == 5){
                server.main();
            }
        }
    }
}
