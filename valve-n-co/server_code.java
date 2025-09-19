import java.util.Scanner;
import java.io.*;
public class server_code{
    private boolean unlocked = false;
    private String secret = "our super secret info #1: we're actually evil mr munchkin mans #2: credit card1 #3: credit card2 #4: credit card3";
    private String[] list = {"secret"};
    private String[] list2 = {secret};
    private void main() throws FileNotFoundException{
        System.out.println(get_info("secret","va1v3"));
    }
    public String get_info(String info, String pass) throws FileNotFoundException{
        if(unlocked){
                for(int i = 0; i < list.length; i++){
                    if(list[i] == info){
                        return(list2[i]);
                    }
                }
        }else {
            if (pass.equals(target_pass("VALVE"))){
                for(int i = 0; i < list.length; i++){
                    if(list[i] == info){
                        return(list2[i]);
                    }
                }
                unlocked = true;
            } else {
                return("Wrong pass");
            }            
        }

        return(null);
    }

    private Scanner scan; // not used globally anymore
    private String target_pass(String target) throws FileNotFoundException {
        if (target == null) return "NOT_FOUND";
        File f = new File("globals/IPs.dat");
        if (!f.exists()) {
            return "NOT_FOUND";
        }

        try (Scanner s = new Scanner(f)) {
            boolean sniff = false;
            while (s.hasNextLine()) {
                String current = s.nextLine();
                if (!sniff) {
                    if (current.equals(target)) {
                        sniff = true;
                    }
                } else {
                    if (current.contains("@")) {
                        return current.replace("@", "");
                    }
                }
            }
        }
        return "NOT_FOUND";
    }
}