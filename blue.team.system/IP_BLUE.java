public class IP_BLUE {
    private menu menu = new menu();

    private String ip = "192.255.255.1";
    
    private String ports = "/24,/88,/220";

    public void change_ip(String in) {
        if (in == null) {
            menu.prompt("No IP entered.");
            return;
        }
        in = in.trim();
        if (ValidIPv4(in)) {
            ip = in;
            menu.prompt("IP changed to: " + ip);
        } else {
            menu.prompt("Invalid format, example: 192.168.1.1");
        }
    }

    public void change_ports(String in) {
        if (in == null) {
            menu.prompt("No ports given");
            return;
        }
        in = in.trim();
        if (ValidPortsString(in)) {
            ports = in;
            menu.prompt("Ports changed to: " + ports);
        } else {
            menu.prompt("Invalid port format, example: /24,/80 or /22,/220");
        }
    }

    //Pv4 check
    private boolean ValidIPv4(String candidate) {
        if (candidate == null) return false;
        String[] parts = candidate.split("\\.");
        if (parts.length != 4) return false;
        try {
            for (String p : parts) {
                int n = Integer.parseInt(p);
                if (n < 0 || n > 255) return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean ValidPortsString(String s) {
        if (s == null || s.isEmpty()) return false;
        String[] toks = s.split(",");
        for (String t : toks) {
            t = t.trim();
            if (!t.startsWith("/")) return false;
            String num = t.substring(1);
            if (num.isEmpty()) return false;
            try {
                Integer.parseInt(num);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public void main() {
        boolean running = true;
        while (running) {
            int choice = menu.choices("IP", new String[]{"change_ip", "change_ports", "view", "exit"});
            if (choice == 0) {
                change_ip(menu.prompt("enter new ip"));
            } else if (choice == 1) {
                change_ports(menu.prompt("enter new ports EX: /port,/port2"));
            } else if (choice == 2) {
                menu.prompt("ip: " + ip + " | ports: " + ports);
            } else { 
                running = false;
            }
        }
    }
    public String getIp() {
    return ip;
 }

}
