import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class scanMC implements Runnable{
    
    static Random r = new Random();
    static boolean nyah = true;

    static MCConfig conf = new MCConfig();

    // color
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private int t_port;
    private int t_portMax;
    private int t_time;

    
    public scanMC(int port, int port_max, int time) {
        this.t_port = port;
        this.t_portMax = port_max;
        this.t_time = time;
    }

    @Override
    public void run(){

        while(nyah == true){

            String IPStart = r.nextInt(256) + "";

            if (IPStart != "127") {

                String host = IPStart + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
                //String host = "192.168.1.87"; 

                if (ping(host, t_time) == true) {
                    try {
                        while (t_port <= t_portMax) {
                            if (scanning(host, t_port, t_time) == true) {
                                System.out.println(ANSI_GREEN + "Minecraft server found \n" + host + " port: " + t_port + ANSI_RESET);
                                save(host, t_port);
                            
                            }else {
                                System.out.println(ANSI_YELLOW + "No Minecraft server found for " + host + ":" + t_port + ANSI_RESET);  
                            }

                            // break infinite loop
                            if (t_port >= t_portMax) {
                                break;
                            }

                            t_port = t_port + 1;
                        }
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                nyah = true;
            }
        }
    }


    public boolean ping(String host, int time){
        boolean reachable = false;
        try{
            InetAddress address = InetAddress.getByName(host);
            reachable = address.isReachable(time);

            if (reachable) {
                System.out.println("Is host " + host +  " reachable? " + ANSI_GREEN + reachable + ANSI_RESET);
            }else{
                System.out.println("Is host " + host + " reachable? " + ANSI_RED + reachable + ANSI_RESET);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return reachable;
    }

    /**
     * @param host
     * @param port
     * @param time
     * @return
     */

    public static boolean scanning(String host, int port, int time) throws UnknownHostException,IOException{ 
        boolean reachable = true;
        Socket socket = new Socket();
        try
        {
            socket.connect(new InetSocketAddress(host, port), time);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            out.write(0xFE);
            StringBuilder str = new StringBuilder();
            int b;

            while ((b = in.read()) != -1) {
                if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
                    str.append((char) b);
                }
            }
           
            String[] data = str.toString().split("§");
            reachable = ((str != null && data != null && data.length == 3) ? true : false);
        }
        catch (Throwable t)
        {
            //t.printStackTrace();
            reachable = false;
           
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        return reachable;
    }



    public static void save(String host, int port) {

        File folder = new File("MCserver/"); 
        String nameFile = "MCserver.txt";
    
        if (!folder.exists()) {
            folder.mkdirs();
        }
    
        File save = new File(folder, nameFile);
        boolean ipExist = false;
    
        try {
            
            if (save.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(save));
                String line;
                String target = host + ":" + port;
    
                while ((line = reader.readLine()) != null) {
                    if (line.trim().equals(target)) {
                        ipExist = true;
                        break;
                    }
                }
                reader.close();
            }
    
            if (!ipExist) {
                PrintWriter writer = new PrintWriter(new FileWriter(save, true));
                writer.write("\n" + host + ":" + port);
                writer.close();
            } else {
                System.out.println("IP already exists in file: " + host + ":" + port);
            }
    
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}