import java.util.Stack;
import java.util.Scanner;
import java.io.*;
public class router {
    private String user;
    private boolean prev_init = false;
    Scanner scan;
    String current_register ="valve";
    String ports = "/20";
    server_code server = new server_code();

    private String ip_table = "globals/IPs.dat";
    private Stack<String> register = new Stack<>();
    private Stack<String> register_type = new Stack<>();
    private Stack<String> register_IP = new Stack<>();
    private Stack<String> register_Port = new Stack<>();
    private Stack<String> register_pass = new Stack<>();
    public void view() {

        int size = Math.max(Math.max(register.size(), register_IP.size()), 
                            Math.max(register_Port.size(), register_pass.size()));

        System.out.println("Name\t\tIP\tPort\tPass\tType");
        System.out.println("---------------------------------------------");

        for (int i = 0; i < size; i++) {
            String name = (i < register.size()) ? register.get(i) : "-";
            String ip   = (i < register_IP.size()) ? register_IP.get(i) : "-";
            String port = (i < register_Port.size()) ? register_Port.get(i) : "-";
            String pass = (i < register_pass.size()) ? register_pass.get(i) : "-";
            String type = (i < register_type.size()) ? register_type.get(i) : "-";
            System.out.printf("%-10s %-15s %-6s %-10s %-6s %n", name, ip, port, pass, type); //i fucking love stack overflow >.<
        }
    }

    public void init() throws FileNotFoundException {
    if (prev_init) return;

    if (scan == null) scan = new Scanner(new File(ip_table));
    String current;

    while (scan.hasNextLine()) {
        current = scan.nextLine().strip();

        // Strip inline comments
        if (current.contains("//")) {
            current = current.substring(0, current.indexOf("//")).strip();
        }

        if (current.isEmpty()){
            continue;
        } 

        if (!current.contains("@") && !current.contains(";") && !current.contains("##")) {
            register.push(current); // is a name
        } else if (current.contains(";")) {
        int slashIndex = current.indexOf("/");
        int semiIndex  = current.indexOf(";");
        if (slashIndex != -1 && semiIndex != -1 && semiIndex > slashIndex) {
            String ip   = current.substring(0, slashIndex);
            String port = current.substring(slashIndex + 1, semiIndex);

            register_IP.push(ip);
            register_Port.push(port);
        }


        } else if (current.contains("@")) {
            register_pass.push(current.replace("@", ""));
        } else if (current.contains("##")){
            register_type.push(current.replace("##", ""));
        }
    }
    prev_init = true;
    server.change_pass(register_pass.elementAt(register.indexOf("VALVE")));
}

    public void register(String ip, String pass, String where){
        if(pass.equals("temp") && ports.equals("temp")){
            //check password and if the ports the object is on, is the same as the ones scanned in the global ip table;
        }
    }
    public String request(String user, String code) throws Exception{
        //bruh im hela lazy rn sooo
        for(int i = 0; i < register.size();i++){
            if(register.elementAt(i).equals(user)&&register_pass.elementAt(i).equals(code)){
                return(server.request("valve-secrets++/super-secret-info.dat"));
            }
        }
        return("SOmething went wrong");
    }
    public String request_info(){
        return("a");
    }
    public String send(String user, String code){
        return("packet");
    }
    public String get_ip(String user){
        for(int i = 0; i<register.size();i++){
            if(register.elementAt(i).equals(user)){
                return(register_IP.elementAt(i));
            }
        }
        return(null);
    }
    public String get_pass(String user){
        for(int i = 0; i<register.size();i++){
            if(register.elementAt(i).equals(user)){
                return(register_pass.elementAt(i));
            }
        }
        return(null);
    }   
    
    //register
    //requestw
    //send
    //ip conf
    
}

