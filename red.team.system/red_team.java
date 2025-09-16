import java.io.FileNotFoundException;

public class red_team {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        menu menu = new menu();
        while(true){
            if(menu.main() == 4){
                break;
            }
        }
    }
}

