package server.wordDatabase;

import java.io.IOException;

public interface WordDatabase {
	
	void add(String word);
	Word pick();
	void load() throws IOException;

}
