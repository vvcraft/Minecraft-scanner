
public class main {

    static MCConfig conf = new MCConfig();

    static String option;

    public static void main(String[] args){

        conf.config();

        System.out.println(conf.ANSI_GREEN + "Starting . . ." + conf.ANSI_RESET);
        
        for (int i = 0; i < conf.threadNumber; i++) {
            Runnable r = new scanMC(conf.port, conf.portMax, conf.time);
            new Thread(r).start();
        }
    }
}
