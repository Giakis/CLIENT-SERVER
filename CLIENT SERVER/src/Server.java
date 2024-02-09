


/**
 * This is a demo server-side socket connection program for a multi-
 threaded client-server
 * architecture based on Runnable interface. The Server receives some
 messages from the Client
 * and instantly sends them back to the Client (a.k.a. Echo-Server).
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;

class Server {
    public static ArrayList<Account> acc = new ArrayList<>();
    //public  static Integer id= 1;


    public static void main(String[] args)
    {
        boolean flag=true;

        ServerSocket server = null;
        try {
            server = new ServerSocket(Integer.parseInt(args[0]));
            while (true) {
                Socket client = server.accept();
                DataInputStream in = new DataInputStream(client.getInputStream());
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                int argsLength = in.readInt();
                String[] arguments = new String[argsLength];
                for (int i = 0; i < argsLength; i++) {
                    arguments[i] = in.readUTF();
                }

                int key = Integer.parseInt(arguments[2]);

                if (key == 1) {
                    flag = true;
                    for (int i = 0; i < arguments[3].length(); i++) {
                        if ((arguments[3].charAt(i) < 'a' || arguments[3].charAt(i) > 'z') &&
                                (arguments[3].charAt(i) < 'A' || arguments[3].charAt(i) > 'Z') &&
                                (arguments[3].charAt(i) < '0' || arguments[3].charAt(i) > '9') &&
                                (arguments[3].charAt(i) != '_')) {
                            flag = false;
                            out.writeUTF("Invalid Username");
                            break;
                        } else {
                            continue;
                        }
                    }
                    if (flag) {
                        for (int i = 0; i < acc.size(); i++) {
                            if (acc.get(i).getUserName().equals(arguments[3])) {
                                flag = false;
                                out.writeUTF("Sorry, the user already exists");
                                break;
                            }
                        }
                    }
                    if (flag) {
                        Account account = new Account(arguments[3]);
                        acc.add(account);
                        out.writeUTF(String.valueOf(account.getAuthToken()));
                    }
                } else {
                    String sender="";
                    int IndexOfSender=-1;
                    flag = true;
                    for (int i = 0; i < acc.size(); i++) {
                        if (acc.get(i).authToken == Integer.parseInt(arguments[3])) {
                            sender=acc.get(i).userName;
                            IndexOfSender=i;
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        out.writeInt(0);
                    } else if (key == 2) {
                        int counter = 0;
                        out.writeInt(acc.size() - 1);
                        for (int i = 0; i < acc.size(); i++) {
                            if (Integer.parseInt(arguments[3]) != (acc.get(i).getAuthToken())) {
                                counter++;
                                out.writeUTF(counter + ". " + acc.get(i).getUserName());
                            }
                        }
                    }else if (key==3){
                        out.writeInt(1);//AMA TO SVISEIS KATASTAFIKES
                        flag=true;
                        int index=-1;
                        for (int i=0;i<acc.size();i++){
                            if (acc.get(i).getUserName().equals(arguments[4])){
                                index=i;
                                flag=false;
                                break;
                            }
                        }
                        if (flag){
                            out.writeUTF("User does not exist");
                        }else {
                            Message sms=new Message(false,sender,arguments[4],arguments[5]);
                            acc.get(index).messageBox.add(sms);
                            out.writeUTF("OK");

                        }
                    }
                    else if(key==4) {
                        //out.writeInt(1);//AMA TO SVISEIS KATASTAFIKES
                        ArrayList<Message> minima=new ArrayList<>();
                        minima=acc.get(IndexOfSender).messageBox;
                        out.writeInt(minima.size());
                        System.out.println("MINIMA:"+minima.size());
                        if (minima.size()>0) {
                            for (int i = 0; i < minima.size(); i++) {
                                if (!minima.get(i).getIsRead()) {
                                    out.writeUTF(minima.get(i).idMessage + ". from: " + minima.get(i).sender + "*");

                                } else {
                                    out.writeUTF(minima.get(i).idMessage + ". from: " + minima.get(i).sender);
                                }
                            }
                        }
                        else{
                            out.writeUTF("The List Is Empty");
                        }

                    }else if (key==5){
                        out.writeInt(1);//AMA TO SVISEIS KATASTAFIKES
                        ArrayList<Message> minima=new ArrayList<>();
                        minima=acc.get(IndexOfSender).messageBox;
                        int element=Integer.parseInt(arguments[4]);
                        boolean counter=true;
                        for (int i=0;i<minima.size();i++){
                            if (element==minima.get(i).idMessage){
                                out.writeUTF("("+minima.get(i).sender+")"+minima.get(i).body);
                                    minima.get(i).isRead=true;
                                    counter=false;
                                    break;
                                }
                        }
                        if (true){
                            out.writeUTF("Message does not exist");
                        }

                    }else if (key==6){
                        out.writeInt(1);//AMA TO SVISEIS KATASTAFIKES
                        ArrayList<Message> minima=new ArrayList<>();
                        minima=acc.get(IndexOfSender).messageBox;
                        int element=Integer.parseInt(arguments[4]);
                        boolean counter=true;
                        for (int i=0;i<minima.size();i++){
                            if (element==minima.get(i).idMessage){
                                minima.remove(element);
                                out.writeUTF("OK");
                                counter=false;
                                break;
                            }
                        }
                        if (counter){
                            out.writeUTF("Message does not exist");
                        }
                    }
                }
                ClientHandler clientSock = new ClientHandler(client);
                new Thread(clientSock).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        // Constructor
        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }
        // the thread echoes client's messages
        public void run()
        {
            PrintWriter out = null;
            BufferedReader in = null;
            try {
                out = new PrintWriter(
                        clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.printf(
                            " Sent from the client: %s\n",
                            line);
                    out.println(line);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


