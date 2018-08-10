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

	public static void main(String args[]) {
		try {
			scanner = new Scanner(System.in);
			String serverIp = "155.230.120.112";
			Socket socket = new Socket(serverIp, 5000);
			System.out.println("Connected Server");
			ClientSender clientSender = new ClientSender(socket, "001");
			Thread sender = new Thread(clientSender);
			Thread receiver = new Thread(new ClientReceiver(socket));
			sender.start();
			receiver.start();
			subcar_demo subcar_demo = new subcar_demo(clientSender.out);
			subcar_demo.setFrame();
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
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

