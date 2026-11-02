package clueGame;

import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;


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
	
	public int getRow() { return row; }
	public int getCol() { return col; }
	public char getInitial() { return initial; }
	public DoorDirection getDoorDirection() { return doorDirection; }
	public boolean isRoomLabel() { return roomLabel; }
	public boolean isRoomCenter() { return roomCenter; }
	public char getSecretPassage() { return secretPassage; }
	public Set<BoardCell> getAdjList() { return adjList; }

}
