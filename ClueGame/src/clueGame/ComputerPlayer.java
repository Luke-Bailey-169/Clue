package clueGame;

/**
 * @author Ryan Steinglass
 * 
 * ComputerPlayer
 */
public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}

	@Override
	public boolean isHuman() { return false; }
}
