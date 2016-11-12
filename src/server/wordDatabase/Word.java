package server.wordDatabase;

public class Word {

	private final String word;
	private char dash[];
	
	public Word(String word) {
		this.word = word;
	}

	public char[] getDash() {
		return dash;
	}
	
	public String getStrDash() {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < dash.length; i++)
			builder.append(dash[i]);
		return builder.toString();
	}

	public boolean setDash(Character c) {
		c = Character.toLowerCase(c);
		boolean found = false;

		for(int i = 0; i < dash.length; i++) {
			if(c.equals(dash[i])){
				dash[i] = c;
				found = true;
			}
		}
		
		return found;
	}
	
	public boolean isComplete() {
		return word.indexOf('-') < 0;
	}
	
	public boolean verify(String word) {
		return this.word.equalsIgnoreCase(word);
	}

	public String getWord() {
		return word;
	}
	
}
