package server;

import java.io.IOException;
import java.net.Socket;

import server.wordDatabase.WordDatabase;

public class HandlerClientFactoryImpl implements HandlerClientFactory {

	private WordDatabase wordDatabase = null;
	
	public HandlerClientFactoryImpl(WordDatabase wordDatabase) {
		super();
		this.wordDatabase = wordDatabase;
	}

	@Override
	public HandlerClient create(Socket socket) {
		try {
			HandlerClientImpl hc = new HandlerClientImpl(socket);
			hc.setWordDatabase(this.wordDatabase);
			return hc;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
