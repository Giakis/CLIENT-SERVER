
/**
 * This is a demo client-side socket connection program for a multi-
 threaded client-server
 * architecture based on Runnable interface. The Client sends some
 messages to the Server
 * and the Server instantly sends them back to the Client (a.k.a.
 Echo-Server).
 */
import java.io.*;
import java.net.*;
import java.util.*;
public class Client {
    public static void main(String[] args)
    {
        try (Socket socket = new Socket(args[0] ,Integer.parseInt(args[1]))) {

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            output.writeInt(args.length);
            for (int i = 0; i < args.length; i++) {
                output.writeUTF(args[i]);
            }
            int key = Integer.parseInt(args[2]);
            if (key == 1) {
                System.out.println(in.readUTF());
            } else {
                int temp = in.readInt();
                if (temp == 0) {
                    System.out.println("Invalid Auth Token");
                } else if (key == 2) {
                    for (int i = 0; i < temp; i++) {
                        System.out.println(in.readUTF());
                    }
                } else if (key == 3) {
                    System.out.println(in.readUTF());
                } else if (key == 4) {
                    for (int i = 0; i < temp; i++) {
                        System.out.println(in.readUTF());
                    }
                } else if (key == 5) {
                    System.out.println(in.readUTF());
                } else if (key == 6) {
                    System.out.println(in.readUTF());
                }
            }
        }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
