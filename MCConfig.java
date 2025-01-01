import java.io.Console;
import java.util.Scanner;

public class MCConfig{

    main m = new main();

    Boolean correct = false;

    int threadNumber = 100;
    int time = 3000;
    int port = 25565;
    int portMax = 25565;

    String pingNumberString;
    String timeString;
    String portString;

    String portMinString;
    String portMaxString;

    String millisec = "000";

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    @SuppressWarnings("resource")
    public void config(){

        while (correct != true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println(ANSI_YELLOW + "What the min and max port do you want to scan ? (default is 25565-25565)" + ANSI_RESET);
            portString = scanner.nextLine();

            if (!portString.equalsIgnoreCase("")) {
                while (portString.matches("[a-zA-Z]+")) {
                    System.out.println(ANSI_RED + "No characters allowed, exemple: 25565-25565" + ANSI_RESET);
                    portString = scanner.nextLine();
                }
            }else{
                portString = "25565-25565";
            }

            // del whitespace
            portString = portString.replaceAll("\\s", "");

            // split port
            String[] split = portString.split("-");
            portMinString = split[0];
            portMaxString = split[1];
            
            System.out.println(ANSI_YELLOW + "How many second before time out ? (default is 3)" + ANSI_RESET);
            timeString = scanner.nextLine();

            if (!timeString.equalsIgnoreCase("")) {
                while (timeString.matches("[a-zA-Z]+")) {
                    System.out.println(ANSI_RED + "No characters allowed" + ANSI_RESET);
                    timeString = scanner.nextLine();
                }
            }else{
                timeString = "3";
            }

            System.out.println(ANSI_YELLOW + "How many thread do you want to create ? (1 IP by thread, default is 100) " + ANSI_RESET);
            pingNumberString = scanner.nextLine();

            if (!pingNumberString.equalsIgnoreCase("")) {
                while (pingNumberString.matches("[a-zA-Z]+")) {
                    System.out.println(ANSI_RED + "No characters allowed" + ANSI_RESET);
                    pingNumberString = scanner.nextLine();
                }
            }else{
                pingNumberString = "100";
            }

            //clear
            System.out.print("\033c");

            System.out.println("Your configuration:");
            System.out.println("");
            System.out.println("Port: " + portMinString + " - " + portMaxString);
            System.out.println("Time out: " + timeString + "s");
            System.out.println("IP By thread: " + pingNumberString);
            System.out.println("");
            System.out.println(ANSI_YELLOW + "Everything correct ?" + ANSI_RESET);
            System.out.println("y or n:");

            String result;

            result = scanner.next();

            if (result.equalsIgnoreCase("y")) {
                // le headshot
                System.out.println("Headshot");
                break;
            }

            while (!result.equalsIgnoreCase("y")) {
                if (result.equalsIgnoreCase("n")) {
                    break;
                }

                System.out.println("write y or n");
                result = scanner.next();
            }
        }

        port = Integer.valueOf(portMinString);
        portMax = Integer.valueOf(portMaxString);

        threadNumber = Integer.valueOf(pingNumberString);

        timeString = timeString + millisec;
        time = Integer.valueOf(timeString);

        //clear
        System.out.print("\033c");
    }
}