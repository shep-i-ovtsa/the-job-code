import java.util.Scanner;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
public class server_code{ //reminder to add port and IP functionality
    private boolean unlocked = false;
    private SecretKey secret_key;
    private String[] list = {"secret"};
    private String[] list2 = {};
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

    private Scanner scan;
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
    /*private String encrypter(){
        File input = new File("globals/IPs.dat");
        if (!input.exists()) {
            return("globals not found ");
        }

        //ensure output dir
        File outputDir = new File("valve-n-co");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        try (Scanner s = new Scanner(input);
             BufferedWriter writer = new BufferedWriter(new FileWriter("valve-n-co/super-secret-info.dat"))) {

            boolean found = false;

            while (s.hasNextLine()) {
                String read = s.nextLine();

                if (!found) {
                    if (read.contains("#")) {
                        found = true;
                    }
                } else {
                    if (read.contains("#")) {
                        writer.write(read);
                        writer.newLine();
                        menu.prompt("saved: " + read);
                    } else {
                        // if line dosent contain a semicolon-> stop
                        break;
                    }
                }
            }
            return("success");

        } catch (IOException e) {
            return("error");

        }
    }*/ //wanted to add cryptology to valve-n-co servers, but it was just easier to have them in a seperate directory

}