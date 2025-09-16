import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.*;

public class tools {

    static final char[] list = {
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p',
            'q','r','s','t','u','v','w','x','y','z',
            '1','2','3','4','5','6','7','8','9','0'
    };

    menu menu = new menu();
    Scanner scan; // not used globally anymore
    String pass = "valve";
    public String target_pass(String target) throws FileNotFoundException {
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

    /**
    #passwords have an @ 
    #ip/port have a semicolon
    #names come first and should have a open space to seperate eachother
     */
    public void sniffer(String in) {
        if (in == null) {
            menu.prompt("No target");
            return;
        }

        File input = new File("globals/IPs.dat");
        if (!input.exists()) {
            menu.prompt("globals not found ");
            return;
        }

        // ensure output dir exists
        File outputDir = new File("red.team.system");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        try (Scanner s = new Scanner(input);
             BufferedWriter writer = new BufferedWriter(new FileWriter("red.team.system/open.dat"))) {

            boolean found = false;

            while (s.hasNextLine()) {
                String read = s.nextLine();

                if (!found) {
                    if (read.equals(in)) {
                        found = true;
                    }
                } else {
                    if (read.contains(";")) {
                        writer.write(read);
                        writer.newLine();
                        menu.prompt("saved: " + read);
                    } else {
                        // if line dosent contain a semicolon-> stop
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String brute(String target) throws InterruptedException {
        if (target == null) return "NOT_FOUND";
        int n = target.length();
        int threads = Math.max(1, Runtime.getRuntime().availableProcessors());
        int base = list.length;

        //total
        long total;
        double pow = Math.pow(base, n);
        if (pow > Long.MAX_VALUE) {
            //search space too large to prevent bit overflow
            return "NOT_FOUND";
        }
        total = (long) pow;

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        AtomicBoolean found = new AtomicBoolean(false);
        BlockingQueue<String> resultQueue = new LinkedBlockingQueue<>(1);

        long chunk = Math.max(1L, total / threads);

        for (int t = 0; t < threads; t++) {
            final long start = t * chunk;
            final long end = (t == threads - 1) ? total : start + chunk;

            executor.submit(() -> {
                char[] word = new char[n]; // preallocated per thread

                for (long i = start; i < end && !found.get(); i++) {
                    long num = i;
                    // Fill the char[] from right to left
                    for (int pos = n - 1; pos >= 0; pos--) {
                        int index = (int) (num % base);
                        word[pos] = list[index];
                        num /= base;
                    }

                    String candidate = new String(word);
                    if (candidate.equals(target)) {
                        if (found.compareAndSet(false, true)) {
                            resultQueue.offer(candidate);
                        }
                        break;
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        String res = resultQueue.poll();
        return res == null ? "NOT_FOUND" : res;
    }

    public void main() throws InterruptedException, FileNotFoundException {
        int choice = menu.choices("TOOLS", new String[]{"sniff", "Brute_force", "back"});
        if (choice == 0) {
            sniffer(menu.prompt("ENTER TARGET NAME"));
        } else if (choice == 1) {
            String targetName = menu.prompt("ENTER TARGET NAME");
            String lookup = target_pass(targetName);
            if ("NOT_FOUND".equals(lookup)) {
                menu.prompt("target_pass returned NOT_FOUND");
            } else {
                String bruteRes = brute(lookup);
                menu.prompt(bruteRes);
            }
        } else if (choice == 55) {
            menu.prompt(target_pass(menu.prompt("ENTER TARGET NAME")));
        }
    }
}
