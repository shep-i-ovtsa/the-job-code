public class SSH {
    menu menu = new menu();
    public void main(){
        while(true){
            int choice = menu.choices("SSH/TERMINAL EMULATOR", new String[]{ "CAT","MV","RM","LS","back"});
            if(choice == 0){
                cat("dir");
            } else if (choice == 1){
                mv("dir","dir");
            } else if (choice == 2){

            } else if (choice == 3){

            } else if(choice == 4){
                break;
            }
        }
    }
    public void cat(String dir){

    }
    public void mv(String dir_start, String dir_end){

    }
    public void ls(String dir){

    }
    public void rm(String dir){

    }
    //ls
    //cat
    //mv
    //ssh
}
