package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class GuessServer implements Runnable {

	private ServerSocket serverSocket;
	private HandlerClientManager hClienteManager = null;
	private boolean done;
	
	public GuessServer(int port) throws IOException {
		super();
		serverSocket = new ServerSocket(port);
		done = false;
		serverSocket.setSoTimeout(120*1000);
	}
	
	public void setHandlerClientManager(HandlerClientManager hcm) {
		this.hClienteManager = hcm;
	}
	
	public void stop() {
		this.done = true;
	}
	
	@Override
	public void run() {
		System.out.println("Server is on\n");
		done = false;
		while(!done) {
			System.out.printf("Server is listen on port %d\n", serverSocket.getLocalPort());
			try {
				Socket socket = serverSocket.accept();
				System.out.println("Conex�o estabelecida\n");
				this.hClienteManager.handle(socket);
			} catch (SocketTimeoutException ste) {
				System.out.printf("Timemout - None connections\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
