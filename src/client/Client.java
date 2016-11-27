package client;

import java.io.IOException;

public interface Client extends Runnable {
	
	public void connect(String host, int port) throws IOException;
	public void disconnect() throws IOException;
	public void tryLetter(char msg) throws IOException;
	public void tryWord(String word) throws IOException;
	public void startNewWord();

}
