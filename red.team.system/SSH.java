import java.util.Scanner;
import java.io.*;
public class SSH {

    private String current_ip = menu.ip_tools.getIp();
    private String band_dir = "globals/Band.dat";
    private String router_dir = "globals/router.java";
    private String IP_list = "globals/IPs.dat";
    private String pair = "ROUTER";
    menu MENU = new menu();
    public void main(){
        int choice = MENU.choices("SSH", new String[]{"coms"});
    }
    public String coms(String ip, String name, String in){
        if (ip == null){
 
        } else if (name == null){

        }
        return("hi");
    }
}
