package server;

import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HandlerClientManagerImpl implements HandlerClientManager {

	private Executor executor;
	private HandlerClientFactory handlerClientFactory;

	public HandlerClientManagerImpl() {
		executor = Executors.newCachedThreadPool();
	}

	public HandlerClientManagerImpl(HandlerClientFactory hcf) {
		this();
		setHandlerClientFactory(hcf);
	}

	public void setHandlerClientFactory(HandlerClientFactory hcf) {
		this.handlerClientFactory = hcf;
	}

	@Override
	public void handle(Socket socket) {
		try {
			HandlerClient hc = handlerClientFactory.create(socket);
			executor.execute(hc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
