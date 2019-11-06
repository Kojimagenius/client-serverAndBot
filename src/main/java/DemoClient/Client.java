package DemoClient;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static final int servPort = 6666;
    private static final String localhost = "127.0.0.1";

    public static void main(String[] args) {
        Socket socket = null;
        try {
            System.out.println("This is client side. Connecting to serv. IP: " + localhost + "port: " + servPort);
            InetAddress ipAddress= InetAddress.getByName(localhost);
            socket = new Socket(ipAddress, servPort);
            System.out.println("Connected succsessfully");
            System.out.println("Local port = "+socket.getLocalPort()
                    + "HostAddress = " + socket.getInetAddress().getHostAddress());
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);
            InputStreamReader inStr = new InputStreamReader(System.in);
            BufferedReader keyboard = new BufferedReader(inStr);
            String line = null;
            System.out.println("Type in smth and press enter");
            System.out.println();
            while(true){
                line = keyboard.readLine();
                out.writeUTF(line);
                out.flush();
                line = in.readUTF();
                if (line.endsWith("quit")){
                    break;
                }else
                    System.out.println("The server replied with: " + line);
            }
        }catch (IOException e ){
            System.out.println("Troubles on client side: " + e);
            e.printStackTrace();
        }finally {
            try {
                if (socket != null)
                    socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
