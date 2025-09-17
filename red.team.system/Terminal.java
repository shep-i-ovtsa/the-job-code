public class Terminal {
    menu menu = new menu();
    private String current_dir;
    public void main(){
        while(true){
            int choice = menu.choices("SSH/TERMINAL EMULATOR", new String[]{ "CAT","MV","RM","LS","cd","back"});
            if(choice == 0){
                cat("dir");
            } else if (choice == 1){
                mv("dir","dir");
            } else if (choice == 2){
                rm("dir");
            } else if (choice == 3){
                ls(null);
            } else if(choice == 4){
                cd(null);
            } else if(choice == 5){
                break;
            }
        }
    }
    public void cat(String dir){

    }
    public void mv(String dir_start, String dir_end){

    }
    public void ls(String dir){
        if(dir == null){
            //ls current directory files
        }
    }
    public void rm(String dir){

    }
    public void cd(String dir){
        //change directory
    }
    //ls
    //cat
    //mv
    //ssh
}
