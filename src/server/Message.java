package server;

import java.io.Serializable;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static enum MessageType{WELCOME, END, NEW_WORD, TRY_LETTER, TRY_WORD, SORRY, SUCCESS, CONGRATULATION, GAME_OVER};

	private final MessageType type;
	private final Serializable content;
	
	public Message(MessageType type, Serializable content) {
		super();
		this.type = type;
		this.content = content;
	}
	
	public static long getSerialVersion() {
		return serialVersionUID;
	}
	
	public MessageType getType() {
		return type;
	}
	
	public Serializable getContent() {
		return content;
	}
}
