package clueGame;

/**
 * @author Ryan Steinglass
 * 
 * HumanPlayer
 */
public class HumanPlayer extends Player {

	public HumanPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}

	@Override
	public boolean isHuman() { return true; }
}
