package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import server.wordDatabase.Word;
import server.wordDatabase.WordDatabase;

public class HandlerClientImpl implements HandlerClient {

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Socket socket;
	private boolean done;

	private WordDatabase wordDatabase = null;
	private Word word;
	private int countTries;

	private static final Message msgWelcome = new Message(Message.MessageType.WELCOME, "Olá, seja bem vindo!");
	private static final Message msgEnd = new Message(Message.MessageType.END, "Game finished!");

	public HandlerClientImpl(Socket socket) throws IOException {
		this.socket = socket;
		socket.setSoTimeout(60 * 2 * 1000);
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
	}

	public void setWordDatabase(WordDatabase wordDatabase) {
		this.wordDatabase = wordDatabase;
	}

	public void sent(Message msg) {
		try {
			oos.writeObject(msg);
		} catch (IOException e) {
			done = true;
			e.printStackTrace();
		}

	}

	private void closeConnection() {
		try {
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void closeConnection(String cause) {
		sent(new Message(Message.MessageType.END, cause));
		closeConnection();
	}

	public void pickNewWord() {
		this.word = wordDatabase.pick();
		this.countTries = 2 * this.word.getWord().length();
		Message msg = new Message(Message.MessageType.NEW_WORD, word.getStrDash());
		sent(msg);
		System.out.println(word.getWord() + " has sent.");
	}

	private void tryLetter(String content) {
		--countTries;
		Message msg;

		if (word.setDash(content.charAt(0))) {
			if (word.isComplete()) {
				msg = new Message(Message.MessageType.CONGRATULATION,
						"Parabéns! Você completou a palavra: " + word.getWord());
				sent(msg);
				return;
			} else
				msg = new Message(Message.MessageType.SUCCESS, word.getStrDash());
		}
		if (countTries == 0)
			msg = new Message(Message.MessageType.GAME_OVER, "Game Over! Número de tentativas chegou ao limite.");
		else
			msg = new Message(Message.MessageType.SORRY, word.getStrDash());

		sent(msg);
	}

	private void tryWord(String content) {
		Message msg;
		if (word.verify(content))
			msg = new Message(Message.MessageType.CONGRATULATION,
					"Parabéns! Você acertou a palavra: " + word.getWord());
		else
			msg = new Message(Message.MessageType.GAME_OVER, "Game Over! A palavra está incorreta.");
		sent(msg);
	}

	private void end() {
		sent(msgEnd);
		closeConnection();
		done = true;
	}

	private void handleMessage(Message msg) {
		switch (msg.getType()) {
		case NEW_WORD:
			pickNewWord();
			break;
		case TRY_LETTER:
			tryLetter((String) msg.getContent());
			break;
		case TRY_WORD:
			tryWord((String) msg.getContent());
			break;
		case END:
			end();
			break;
		}
	}

	private void receiveMsg() {
		try {
			Message msg = (Message) ois.readObject();
			handleMessage(msg);
		} catch (SocketTimeoutException e) {
			closeConnection("Excedeu o tempo sem atividade.");
			done = true;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		done = false;
		sent(msgWelcome);
		while (!done) {
			receiveMsg();
		}
	}

}
