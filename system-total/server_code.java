import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Scanner;

public class server_code {
    private static String pass_encrypt;
    public static String pass = "va1v3"; // default password
    private static final String ALGO = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static Scanner scan;
    // Change the password
    public static void change_pass(String newPassword) {
        pass = newPassword;
    }
    public void main() throws Exception{
        request("valve-secrets++/super-secret-info.dat");
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
        if(filePath == "SECRET") filePath = "valve-secrets++/super-secret-info.dat";
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

}
