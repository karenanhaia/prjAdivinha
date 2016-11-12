package server;

import server.wordDatabase.DefaultWordDatabase;
import server.wordDatabase.WordDatabase;

public class AppServer {
	
	Server server = null;
	WordDatabase wdb = null;
	HandlerClientManager hcManager = null;
	HandlerClientFactory hcFactory = null;

	private AppServer(int port) throws Exception {
		server = new Server(port);
		wdb = new DefaultWordDatabase("/data/frutas.txt");
		hcFactory = new HandlerClientFactoryImpl(wdb);
		hcManager = new HandlerClientManagerImpl(hcFactory);
		
		server.setHandlerClientManager(hcManager);
		wdb.load();
	}
	
	private void start() {
		(new Thread(server)).start();
	}
	
	public static void main(String[] args) throws NumberFormatException, Exception {
		if(args.length < 1) {
			System.out.print("Use: java AppServer <port>");
			System.exit(1);
		}
		
		final AppServer app = new AppServer(Integer.parseInt(args[0]));
		app.start();
	}
}
