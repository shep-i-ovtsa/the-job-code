import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.*;

public class Tools {

    static final char[] list = {
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p',
            'q','r','s','t','u','v','w','x','y','z',
            '1','2','3','4','5','6','7','8','9','0'
    };

    TUI menu = new TUI();
    
    Scanner scan; 
    /*
    #passwords have an @ 
    #ip/port have a semicolon
    #names come first and should have a open space to seperate eachother
     */
    public void sniffer() throws Exception {
        TUI.clear();
        for(int i = 0; i<route.get_access_size();i++){
            if(route.get_access(i).contains("closed")) continue;
            System.out.println(route.get_access(i));
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

    router route = new router();

    public void main() throws Exception {
        route.init(false);
        int choice = TUI.choices("TOOLS", new String[]{"sniff", "Brute_force", "back"});
        if (choice == 0) {
            sniffer();
            TUI.prompt("");
        } else if (choice == 1) {
            String targetName = TUI.prompt("ENTER TARGET NAME");
            String lookup = route.get_pass(targetName);
            if ("NOT_FOUND".equals(lookup)) {
                TUI.prompt("target_pass returned NOT_FOUND");
            } else {
                String bruteRes = brute(lookup);
                TUI.prompt(bruteRes);
            }
        } else if (choice == 55) {
            String target =(TUI.prompt("ENTER TARGET NAME"));
            TUI.prompt(route.get_pass(target));
        }
    }
}
