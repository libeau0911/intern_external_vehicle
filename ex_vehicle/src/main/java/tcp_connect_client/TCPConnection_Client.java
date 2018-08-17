package tcp_connect_client;

import org.json.simple.JSONObject;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class TCPConnection_Client {

	public static Scanner scanner;
	public static String id="001";

	public static void main(String args[]) {
		try {
			scanner = new Scanner(System.in);
			String serverIp = "155.230.120.112";
            Socket socket1 = new Socket(serverIp, 5000);
			Socket socket2 = new Socket(serverIp, 6000);
			System.out.println("Connected Server");
			ClientSender clientSender1 = new ClientSender(socket1, id);
            ClientSender clientSender2 = new ClientSender(socket2, id);
			Thread sender1 = new Thread(clientSender1);
            Thread sender2 = new Thread(clientSender2);
			Thread receiver1 = new Thread(new ClientReceiver(socket1));
            Thread receiver2 = new Thread(new ClientReceiver(socket2));
			sender1.start();
            sender2.start();
			receiver1.start();
            receiver2.start();
			subcar subcar=new subcar(clientSender1.out, clientSender2.out);
			subcar.setFrame();
			System.out.println(4);
			System.out.println(subcar.location_label.getText());
			subcar.location_label.setText("hiiiiiiiii");
			System.out.println(subcar.location_label.getText());
			subcar.location_label.updateUI();

		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	} // main

	public static class ClientSender extends Thread {
		Socket socket;
		DataOutputStream out;
		String name;

		public ClientSender(Socket socket, String name) {
			this.socket = socket;
			try {
				this.out = new DataOutputStream(this.socket.getOutputStream());
				this.name = name;
			} catch (Exception e) {
			}
		}

		public void run() {
			try {
				if (out != null) {
					this.out.writeUTF(name);
					this.out.flush();
					System.out.println(name);
					JSONObject jsonObject = new JSONObject();

					while (this.out != null) {
						if (scanner.hasNextLine()) {
							String s = scanner.nextLine();
							System.out.println(s);
							String temp[] = s.split(" ");
							jsonObject.put(temp[0], temp[1]);
							out.writeUTF(jsonObject.toJSONString());
							out.flush();
						}
					}
				}

			} catch (IOException e) {
			}
		} // run()
	}

	public static class ClientReceiver extends Thread {
		Socket socket;
		DataInputStream in;

		public ClientReceiver(Socket socket) {
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
			}
		}

		public void run() {
			while (in != null) {
				try {
					System.out.println(in.readUTF());
				} catch (IOException e) {
				}
			}
		} // run
	}
}

