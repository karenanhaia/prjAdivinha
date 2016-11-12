package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server implements Runnable {

	private ServerSocket serverSocket;
	private HandlerClientManager hClienteManager = null;
	private boolean done;
	
	public Server(int port) throws IOException {
		super();
		serverSocket = new ServerSocket(port);
		done = false;
		serverSocket.setSoTimeout(60*1000);
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
				this.hClienteManager.handle(socket);
			} catch (SocketTimeoutException ste) {
				System.out.printf("Timemout - None connections\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
