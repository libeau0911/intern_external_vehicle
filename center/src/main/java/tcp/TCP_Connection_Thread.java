package tcp;

import control_center.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class TCP_Connection_Thread implements Runnable {
    ServerSocket serverSocket = null;
    HashMap clients;

    control_center control_center=new control_center();
    Object x, y, type, info;
   public TCP_Connection_Thread() {
        try {
            serverSocket = new ServerSocket(6000);
            clients = new HashMap();
            Collections.synchronizedMap(clients);
            System.out.println("Connection Ready.");
            control_center control_center = new control_center();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendToAll(String msg) {
        Iterator it = clients.keySet().iterator();
        while (it.hasNext()) {
            try {
                DataOutputStream out = (DataOutputStream) clients
                        .get(it.next());
                out.writeUTF(msg);
                out.flush();
            } catch (IOException e) {
            }
        } // while
    } // sendToAll

    public void run() {
        Socket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println("[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]" + "is connected.");
                ServerReceiver thread = new ServerReceiver(socket);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ServerReceiver extends Thread {
        Socket socket;
        DataInputStream in;
        DataOutputStream out;

        ServerReceiver(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
            }
        }

        public void run() {
            String name = "";
            try {
                name = in.readUTF();
                sendToAll("#" + name + "is connected.");
                clients.put(name, out);
                System.out.println("현재 서버접속자 수는 "
                        + clients.size() + "입니다.");
                while (in != null) {
                    String msg = in.readUTF();
                    System.out.println(msg); //
                    JSONParser jsonParser = new JSONParser();
                    JSONObject obj = (JSONObject) jsonParser.parse(msg);
                    System.out.println(obj.get("x"));
                    System.out.println(obj.get("y"));
                    System.out.println(obj.get("type"));
                    System.out.println(obj.get("info"));
                    x=obj.get("x");
                    y=obj.get("y");
                    type=obj.get("type");
                    info=obj.get("info");
                    control_center.setRow(x, y, type, info);
                    control_center.setFrame();
                }
            } catch (IOException e) {
                // ignore
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                sendToAll("#" + name + "is disconnected.");
                clients.remove(name);
                System.out.println("[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]"
                        + "에서 접속을 종료하였습니다.");
                System.out.println("현재 서버접속자 수는 "
                        + clients.size() + "입니다.");
            } // try
        } // run

    } // ReceiverThread
}
