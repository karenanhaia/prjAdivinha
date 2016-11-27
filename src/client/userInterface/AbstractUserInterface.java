package client.userInterface;

import client.Client;

public abstract class AbstractUserInterface implements UserInterface {

	protected Client client;

	public void setClient(Client cli) {
		this.client = cli;
	}
	
}
