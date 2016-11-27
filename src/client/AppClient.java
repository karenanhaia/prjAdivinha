package client;

import java.io.IOException;
import java.net.UnknownHostException;

import client.userInterface.textUI.TextUserInterface;

public class AppClient {

	public static void main(String args[]) throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException {
		
		ClientImpl cli = new ClientImpl();
		TextUserInterface ui = new TextUserInterface();
		ui.setClient(cli);
		cli.setUserInterface(ui);
		
		//simulando o inicio da aplicação
		cli.connect("localhost", 4500);
		
	}
}
