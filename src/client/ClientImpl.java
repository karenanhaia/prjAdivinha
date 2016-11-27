package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import client.userInterface.UserInterface;
import server.Message;
import server.Message.MessageType;

public class ClientImpl implements Client {

	private boolean done = false;
	private Socket socket = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;

	UserInterface ui = null;

	public ClientImpl() {
		super();
	}

	public ClientImpl(UserInterface ui) {
		this();
		this.setUserInterface(ui);
	}

	public void setUserInterface(UserInterface ui) {
		this.ui = ui;
	}

	@Override
	public void startNewWord() {
		try {
			sent(new Message(Message.MessageType.NEW_WORD, ""));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleMessage(Message msg) {
		switch (msg.getType()) {
		case WELCOME:
			ui.showWelcomeMessage(msg.getContent().toString());
			startNewWord();
			break;
		case NEW_WORD:
			ui.playGuessWord(msg.getContent().toString());
			break;
		case SUCCESS:
			ui.playGuessWord(msg.getContent().toString());
			break;
		case CONGRATULATION:
			ui.showCongratulationMessage(msg.getContent().toString());
			break;
		case GAME_OVER:
			ui.showGameOverMessage(msg.getContent().toString());
			break;
		case SORRY:
			ui.playGuessWord(msg.getContent().toString());
			break;
		case END:
			ui.showEndMessage(msg.getContent().toString());
		}
	}

	private void receiveMessage() {
		try {
			Message msg = (Message) ois.readObject();
			this.handleMessage(msg);
		} catch (SocketTimeoutException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	private void closeConnection() {
		try {
			sent(new Message(Message.MessageType.END, "Game finished!"));
			ois.close();
			oos.close();
			socket.close();
			System.out.println("Client is off\n");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public void run() {
		done = false;
		while (!done)
			receiveMessage();
		closeConnection();
	}

	@Override
	public void connect(String host, int port) throws IOException {
		socket = new Socket(host, port);
		socket.setSoTimeout(200000);
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		(new Thread(this)).start();
		System.out.println("Client is on\n");
	}

	@Override
	public void disconnect() throws IOException {
		done = true;
	}
	
	@Override
	public void tryLetter(char c) throws IOException {
		sent(new Message(MessageType.TRY_LETTER, c));
	}
	
	@Override
	public void tryWord(String word) throws IOException {
		sent(new Message(MessageType.TRY_WORD, word));
	}

	public void sent(Message msg) throws IOException {
		try {
			oos.writeObject(msg);
		} catch (IOException e) {
			done = true;
			e.printStackTrace();
		}
	}

}
