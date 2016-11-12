package server.wordDatabase;

import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractWordDatabase implements WordDatabase {

	protected ArrayList<String> wBase;
	
	
	public AbstractWordDatabase() {
		this.wBase = new ArrayList<>();
	}

	@Override
	public void add(String word) {
		wBase.add(word);
	}
	
	@Override
	public Word pick() {
		return new Word(wBase.get(new Random().nextInt(wBase.size())));
	}
	
}
