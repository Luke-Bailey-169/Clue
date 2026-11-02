package clueGame;

/**
 * @author Ryan Steinglass
 * 
 * Room
 */
public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;

	public Room(String name, BoardCell centerCell, BoardCell labelCell) {
		this.name = name;
		this.centerCell = centerCell;
		this.labelCell = labelCell;
	}

	public String getName() { return name; }
	public BoardCell getCenterCell() { return centerCell; }
	public BoardCell getLabelCell() { return labelCell; }
}
