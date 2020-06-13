package DemoServ;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Serv extends Thread {
    private static final int port = 6666;
    private String MSG = "The client sent message: ";
    private String CONN = "The client closed the connection";
    private Socket socket;
    private int num;
    public Serv(){}

    public void setSocket(int num, Socket socket){
        this.num = num;
        this.socket = socket;
        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }
    public void run(){
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            DataInputStream dataIn = new DataInputStream(in);
            DataOutputStream dataOut = new DataOutputStream(out);
            String line = null;
            while(true){
                line = dataIn.readUTF();
                System.out.println(String.format(MSG, num) + line);
                System.out.println("Sending message back...");
                dataOut.writeUTF("Serv received: " + line);
                dataOut.flush();
                System.out.println();
                if (line.equalsIgnoreCase("quit")){
                    socket.close();
                    System.out.println(String.format(CONN, num));
                    break;
                }


            }

        }catch (IOException e){
            System.err.println("" + e);
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ServerSocket srvSocket = null;
            try {
                int i = 0; //connections counter
                InetAddress ip= InetAddress.getByName("192.168.0.100");
               // ip = InetAddress.getByName("125.0.0.1");
                srvSocket = new ServerSocket(port,2, ip);
                System.out.println("Serv started...\n");
                while (true) {
                    Socket socket = srvSocket.accept();
                    System.err.println("Client accepted");
                    new Serv().setSocket(i++, socket);
                }
            }catch (IOException e){
                System.err.println("Troubles while accepting client: "+e);
                e.printStackTrace();
            }finally {
                try{
                    if (srvSocket != null)
                        srvSocket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
                System.exit(0);


    }

}
