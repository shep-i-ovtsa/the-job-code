import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Terminal {
    private Path currentDir;
    private Path previousDir;

    TUI menu = new TUI();
    router route = new router();
    Scanner scan;

    //constructor
    public Terminal() {
        this.currentDir = Paths.get(".").toAbsolutePath().normalize();
        this.previousDir = this.currentDir;
    }
    private String chooseFromCurrentDir(String title) throws IOException {
        List<String> entries = ls(); // may throw IOException
        List<String> options = new ArrayList<>();

        options.add("..");// parent
        options.add("~");// reverse
        options.add("-");// previous

        options.addAll(entries); //add new entries
        for(String i: entries){
            if(i.contains("++")){
                options.remove(i);
            }
        }
        String[] optionArray = options.toArray(new String[0]);
        int idx = menu.choices(title, optionArray);

        if (idx == 55 || idx == 67) {
            String manual = TUI.prompt("Enter path (absolute or relative):"); //test codes 55 or 67 can switch to manual input
            return manual;
        }

        if (idx < 0 || idx >= optionArray.length) {
            return null;
        }

        return optionArray[idx];
    }
    public void main() throws Exception {
    route.init();
    while (true) {
        int choice = menu.choices("SSH/TERMINAL EMULATOR", 
            new String[]{ "CAT","MV","RM","LS","cd","request","back"});
        if (choice == 0) {
            cat();
        } else if (choice == 1) {
            mv(TUI.prompt("dir_start"),TUI.prompt("dir_end"));
        } else if (choice == 2) {
            rm();
        } else if (choice == 3) {
            TUI.print(pwd());
            printLs();
            TUI.prompt("");
        } else if (choice == 4) {
            cd();
        } else if (choice == 5) {
            TUI.prompt(route.request(TUI.prompt("user"), TUI.prompt("REQUEST code")));
        } else if (choice == 6) {
            break;
        }
        }
    }


    public void cat() throws IOException {
        String selected = chooseFromCurrentDir("Choose file for <cat>");
        if (selected == null) return;

        Path file = resolveAgainstCwd(selected);
        if (!Files.exists(file) || !Files.isRegularFile(file)) {
            TUI.print("cat: not a valid file");
            return;
        }

        TUI.clear();
        TUI.print("CAT READER");
        TUI.print("--------------------------------");

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        TUI.prompt("");
    }


    public void mv(String dir_start, String dir_end) throws IOException {
        Path src = resolveAgainstCwd(dir_start);
        Path dst = resolveAgainstCwd(dir_end);

        if (!Files.exists(src)) {
            throw new FileNotFoundException("mv: source not found: " + src);
        }

        // If dst is a directory, move into that directory (like mv behavior)
        if (Files.exists(dst) && Files.isDirectory(dst)) {
            dst = dst.resolve(src.getFileName());
        } else {
            // If parent of dst doesn't exist, throw
            Path parent = dst.getParent();
            if (parent != null && !Files.exists(parent)) {
                throw new IOException("mv: destination directory does not exist: " + parent);
            }
        }

        Files.move(src, dst, StandardCopyOption.REPLACE_EXISTING);
    }

    public void rm() throws IOException {
        String selected = chooseFromCurrentDir("Choose file/dir to rm");
        if (selected == null) return;

        rm(selected); 
    }

    public void rm(String dir) throws IOException {
        Path target = resolveAgainstCwd(dir);
        if (!Files.exists(target)) {
            throw new FileNotFoundException("rm: no such file or directory: " + target);
        }

        if (Files.isDirectory(target)) {
            try (Stream<Path> walk = Files.walk(target)) {
                List<Path> toDelete = walk.sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                for (Path p : toDelete) {
                    Files.deleteIfExists(p);
                }
            }
        } else {
            Files.delete(target);
        }
    }

    public void cd() throws IOException {
        String selected = chooseFromCurrentDir("Choose directory to enter");
        if (selected == null) return;
        cd(selected); // reuse existing cd(String)
    }
    public void cd(String target) throws IOException {
        if (target == null || target.isEmpty()) {
            return;
        }

        Path newPath;

        if (target.equals("-")) {
            Path temp = currentDir;
            currentDir = previousDir;
            previousDir = temp;
            return;
        }

        if (target.equals("~")) {
            Path parent = currentDir.getParent();
            if (parent == null) {
                return;
            } else {
                newPath = parent.normalize();
            }
        } else {
            Path p = Paths.get(target);
            if (p.isAbsolute()) {
                newPath = p.normalize();
            } else {
                newPath = currentDir.resolve(p).normalize();
            }
        }

        if (!newPath.isAbsolute()) {
            newPath = newPath.toAbsolutePath().normalize();
        }

        if (Files.exists(newPath) && Files.isDirectory(newPath)) {
            previousDir = currentDir;
            currentDir = newPath;
        } else {
            throw new IOException("cd: no such directory: " + target);
        }
    }



    public List<String> ls() throws IOException {
        List<String> names = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentDir)) {
            for (Path entry : stream) {
                names.add(entry.getFileName().toString());
            }
        }
        return names;
    }

    public void printLs() throws IOException {
        List<String> names = ls();
        if (names.isEmpty()) {
            TUI.print("(empty)");
        } else {
            names.forEach(TUI::print);
        }
    }

    public String pwd() {
        String s = currentDir.toString();
        TUI.print(s);
        return s;
    }


    private Path resolveAgainstCwd(String input) { //i just stole theese ones >.<
        Path p = Paths.get(input);
        if (p.isAbsolute()) {
            return p.normalize();
        } else {
            return currentDir.resolve(p).normalize();
        }
    }

}
