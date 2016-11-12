package server;

import java.net.Socket;

public interface HandlerClientFactory {

	public HandlerClient create(Socket socket);
}
