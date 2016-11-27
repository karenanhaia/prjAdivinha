package client.userInterface;

public interface UserInterface {
	
	public void showWelcomeMessage(String message);
	public void showEndMessage(String message);
	public void playGuessWord(String word);
	public void showCongratulationMessage(String word);
	public void showGameOverMessage(String msg);
}
