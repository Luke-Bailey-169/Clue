package clueGame;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ryan Steinglass
 * 
 * BoardCell
 */
public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;

	private boolean isRoom = false;
	private boolean isOccupied = false;

	/*
	--------------------------------------------------
					Constructors
	--------------------------------------------------
	*/

	/**
	 * Constructor for board cells that are walkways
	 */
	public BoardCell(int row, int col, char initial) {
		constructerHelper(row, col, initial);
	}

	/**
	 * Constructor for board cells in a room
	 */
	public BoardCell(int row, int col, char initial, boolean roomLabel, boolean roomCenter, char secretPassage) {
		constructerHelper(row, col, initial);
		
		this.roomLabel = roomLabel;
		this.roomCenter = roomCenter;
		this.secretPassage = secretPassage;
	}
	
	/**
	 * Constructor for board cells that are doors
	 */
	public BoardCell(int row, int col, char initial, DoorDirection doorDirection) {
		constructerHelper(row, col, initial);
		
		this.doorDirection = doorDirection;
	}

	/**
	 * Constructor that includes all inputs
	 */
	public BoardCell(int row, int col, char initial, DoorDirection doorDirection, boolean roomLabel, boolean roomCenter, char secretPassage) {
		constructerHelper(row, col, initial);
		
		this.doorDirection = doorDirection;
		this.roomLabel = roomLabel;
		this.roomCenter = roomCenter;
		this.secretPassage = secretPassage;
	}

	// Helper method to cut down on repeated code in the many BoardCell constructors
	private void constructerHelper(int row, int col, char initial) {
		this.row = row;
		this.col = col;
		this.initial = initial;
		
		this.doorDirection = DoorDirection.NONE;
		this.roomLabel = false;
		this.roomCenter = false;

		adjList = new HashSet<BoardCell>();
	}


	/*
	--------------------------------------------------
						Methods
	--------------------------------------------------
	*/

	/**
	 * Adds a cell to this cell's adjacency list
	 * 
	 * @param adj the cell to be added
	 */
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}


	/*
	--------------------------------------------------
					Getters/Setters
	--------------------------------------------------
	*/
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public char getInitial() {
		return initial;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getSecretPassage() {
		return secretPassage;
	}

	public Set<BoardCell> getAdjList() {
		return adjList;
	}

	public boolean isLabel() {
		return roomLabel;
	}

	public boolean isRoomCenter() {
		return roomCenter;
	}

	public boolean isDoorway() {
		return (doorDirection != DoorDirection.NONE);
	}

	// Sets the boolean that tracks if this cell is part of a room
	public void setIsRoom(boolean room) {
		isRoom = room;
	}

	// Gets the isRoom boolean
	public boolean isRoom() {
		return isRoom;
	}

	// Sets the boolean that tracks if this cell is occupied by a player
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}

	// Gets if this cell is occupied
	public boolean isOccupied() {
		return isOccupied;
	}


	/**
	 * toString method
	 */
	@Override
	public String toString() {
		return "TestBoardCell [row=" + row + ", column=" + col + "]";
	}
}
