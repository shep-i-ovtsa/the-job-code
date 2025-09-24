public class IP_RED {
    private TUI menu = new TUI();

    public String ip = "192.255.255.1";
    
    public String ports = "";

    public void change_ip(String in, boolean min) {
        if (in == null) {
            if(!min)menu.prompt("No IP entered.");
            return;
        }
        in = in.trim();
        if (ValidIPv4(in)) {
            ip = in;
            if(!min)TUI.prompt("IP changed to: " + ip);
        } else {
            if(!min)TUI.prompt("Invalid format, example: 192.168.1.1");
        }
    }

    public void change_ports(String in, boolean min) {
        if (in == null) {
            if(!min)TUI.prompt("No ports given");
            return;
        }
        in = in.trim();
        if (ValidPortsString(in)) {
            ports = in;
            if(!min)TUI.prompt("Ports changed to: " + ports);
        } else {
            if(!min)TUI.prompt("Invalid port format, example: /24,/80 or /22,/220");
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
            int choice = TUI.choices("IP", new String[]{"change_ip", "change_ports", "view", "exit"});
            if (choice == 0) {
                change_ip(TUI.prompt("enter new ip"),false);
            } else if (choice == 1) {
                change_ports(TUI.prompt("enter new ports EX: /port,/port2"),false);
            } else if (choice == 2) {
                TUI.prompt("ip: " + ip + " | ports: " + ports);
            } else { 
                running = false;
            }
        }
    }
    public String getIp() {
        return ip;
 }

}
