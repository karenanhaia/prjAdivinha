package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import server.Message;

public class AppClient {

	public static void main(String args[]) throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException {
		Socket socket = new Socket("localhost", 4500);
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		
		System.out.println("Connection started.\n");
		//while(ois.available() == 0)
			//Thread.sleep(100);
		
		Message msg = (Message) ois.readObject();
		System.out.println(msg.getContent());
		oos.writeObject(new Message(Message.MessageType.NEW_WORD, ""));
		msg = (Message) ois.readObject();
		System.out.println(msg.getContent());
		
		System.in.read();
	}
}
