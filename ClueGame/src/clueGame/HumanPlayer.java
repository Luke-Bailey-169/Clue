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
	
	public Solution createSuggestion(Room room) {
		// Temporary method implementation, no actual behavior
		return null;
	}

	@Override
	public boolean isHuman() { return true; }
}
