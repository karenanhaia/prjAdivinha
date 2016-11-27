package server.wordDatabase;

import java.util.Arrays;

public class Word {

	private final char DASH_PATTERN = '-';
	private final String word;
	private char dash[];

	public Word(String word) {
		this.word = word;
		this.dash = new char[word.length()];
		Arrays.fill(dash, DASH_PATTERN);
	}

	public char[] getDash() {
		return dash.clone();
	}

	public String getStrDash() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < dash.length; i++)
			builder.append(dash[i]);
		return builder.toString();
	}

	public boolean setDash(Character c) {
		c = Character.toLowerCase(c);
		boolean found = false;

		for (int i = 0; i < word.length(); i++) {
			if (Character.toLowerCase(word.charAt(i)) == c) {
				dash[i] = c;
				found = true;
			}
		}

		return found;
	}

	public boolean isComplete() {
		for (char c : dash)
			if (c == DASH_PATTERN)
				return false;
		return true;
	}

	public boolean verify(String word) {
		return this.word.equalsIgnoreCase(word);
	}

	public String getWord() {
		return word;
	}

}
