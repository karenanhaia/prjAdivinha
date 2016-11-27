package client.userInterface.textUI;

import java.io.IOException;
import java.util.Scanner;

import client.userInterface.AbstractUserInterface;

public class TextUserInterface extends AbstractUserInterface {

	@Override
	public void showWelcomeMessage(String msg) {
		System.out.printf("GUESS: %s\n", msg);
	}
	
	@Override
	public void showEndMessage(String msg) {
		System.out.printf("GUESS: %s\n", msg);
	}

	@Override
	public void playGuessWord(String strDash) {
		System.out.println("GUESS: Adivinhe qual é a palavra:");
		for (int i = 0; i < strDash.length(); i++) {
			System.out.printf("%c", strDash.charAt(i));
		}
		System.out.println();

		System.out.println("GUESS: Sabe a palavra (s/n)?");
		Scanner scan = new Scanner(System.in);
		String r = scan.next();
		if ('s' == r.charAt(0) || 'S' == r.charAt(0)) {
			System.out.println("GUESS: Qual é a palavra?");
			try {
				client.tryWord(scan.next());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("GUESS: Escolha uma letra:");
			try {
				client.tryLetter(scan.next().charAt(0));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void showCongratulationMessage(String word) {
		System.out.println("GUESS: Parabéns! Você acertou a palavra: " + word);
		playAgain();
	}
	
	@Override
	public void showGameOverMessage(String msg) {
		System.out.printf("GUESS: %s\n", msg);
		playAgain();
	}
	
	private void playAgain() {
		System.out.println("Deseja jogar novamente (s/n)?");
		Scanner scan = new Scanner(System.in);
		String r = scan.next();
		if ('s' == r.charAt(0) || 'S' == r.charAt(0)) {
			client.startNewWord();
		} else {
			try {
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
