import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Scanner;

public class server_code {
    IP_RED IP = new IP_RED();
    Terminal kitty;
    public String name = "VALVE";
    private static String pass_encrypt;
    public static String pass = "va1v3"; // default password
    private static final String ALGO = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static Scanner scan;
    private String perms_Admin = "w3r0le";
    private String perms_Valve;
    private router route;
    private static String secrets_dir = "valve-secrets++/";
    private String server_ip;
    private String server_port;

    

    // Change the password
    public static void change_pass(String newPassword) {
        pass = newPassword;
    }
    public void main() throws Exception{
        //request("valve-secrets++/super-secret-info.dat");
        login();
    }
    //encrypts a file and saves 
    public static void encrypt(String filePath, String password) throws Exception {
        Path path = Paths.get(filePath);
        byte[] plain = Files.readAllBytes(path);
        byte[] cipher = applyCipher(plain, password, Cipher.ENCRYPT_MODE);

        Path out = Paths.get(filePath);
        Files.write(out, cipher, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        pass_encrypt = password;
    }

    //decrypts a string
    public static String decrypt(String encryptedText) throws Exception {
        byte[] cipher = java.util.Base64.getDecoder().decode(encryptedText);
        byte[] plain = applyCipher(cipher, pass_encrypt, Cipher.DECRYPT_MODE);
        return new String(plain);
    }

    public static String request(String filePath) throws Exception {
        if(filePath == "SECRET") filePath = secrets_dir+"super-secret-info.dat";
        String result="";
        String line;
        if(scan == null) scan = new Scanner(new File(filePath));
        while(scan.hasNextLine()){
            line =scan.nextLine();
            System.out.println(line);
        }
        return(result);
    }


    private static byte[] applyCipher(byte[] input, String password, int mode) throws Exception {
        SecretKeySpec key = new SecretKeySpec(getKey(password), ALGO);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(mode, key);
        return cipher.doFinal(input);
    }

    private static byte[] getKey(String password) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] hash = sha.digest(password.getBytes("UTF-8"));
        byte[] keyBytes = new byte[16];
        System.arraycopy(hash, 0, keyBytes, 0, 16);
        return keyBytes;
    }
    public void login() throws Exception{
        if(route == null){ route = new router(); route.init(false);}

        perms_Valve = pass;
        if (kitty == null) kitty = new Terminal();
        String code;
        TUI.print("LOGIN - VALVE_SERVER");
        code = TUI.prompt("PASSWORD");
        if (code.equals(perms_Admin)){
            kitty.cd("/home/shep/Documents/eng/mock_sec_sys/valve-secrets++");
            //open up option to read logs and such
            while (true) {

                route.init(true);
                IP.change_ip(route.get_ip(name),true);
                IP.change_ports(route.get_ports(name), true);                
                int choice = TUI.choices("VALVE-SERVER<admin", new String[]{"ls","cat","mv","rm","encrypt","exit","view-info"});
                if(choice == 0){
                    kitty.printLs();
                    TUI.prompt("");
                } else if(choice == 1){
                    kitty.cat();
                } else if(choice == 2){
                    kitty.mv(TUI.prompt("path to start"), TUI.prompt("path to end"));
                } else if(choice == 3){
                    kitty.rm(TUI.prompt("path to remove"));
                } else if(choice == 4){
                    encrypt(TUI.prompt("enter path to encrypt"), pass);
                    TUI.print("used "+pass+" to encrypt your file");
                } else if(choice == 5){
                    return;
                } else if(choice == 6){
                    view();
                }
            }
        } else if (code.equals(perms_Valve)){ 
            kitty.cd("/home/shep/Documents/eng/mock_sec_sys/valve-co++");
            while (true){
                int choice = TUI.choices("VALVE-SERVER<employee", new String[]{"ls","cat","view-info","exit"});
                if(choice == 0){
                    kitty.printLs();
                    TUI.prompt("");
                } else if(choice == 1){
                    kitty.cat();
                } else if(choice ==2){
                    view();
                } else if(choice ==3){
                    break;
                }
            }
        }
    }
    private void view(){
        TUI.clear();
        TUI.print("INFO VIEW");
        TUI.print("-----------------------------------");       
        TUI.print("PASSWORD: "+pass);
        TUI.print("last encryption code: "+pass_encrypt);
        TUI.print("TERMINAl: "+ kitty+ "| kitty ");
        TUI.print("ROUTE_SOURCE: "+secrets_dir);
        TUI.print("Admin USER: "+route.get_type("ADMIN"));
        TUI.print("current user: "+route.get_type("USER"));
        TUI.print("employ perms: "+perms_Valve);
        TUI.prompt("");
    }

}
