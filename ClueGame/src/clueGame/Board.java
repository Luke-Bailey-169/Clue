package clueGame;

import java.util.Map;

/**
 * @author Ryan Steinglass
 * 
 * Board
 */
public class Board {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;

	/*
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		super();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize() {
		loadSetupConfig();
		loadLayoutConfig();
	}


	/*
	--------------------------------------------------
					  Class Methods
	--------------------------------------------------
	*/

	/**
	 * Sets the config files
	 * 
	 * @param layoutConfigFile csv file
	 * @param setupConfigFile txt file
	 */
	public void setConfigFiles(String layoutConfigFile, String setupConfigFile) {
		this.layoutConfigFile = layoutConfigFile;
		this.setupConfigFile = setupConfigFile;
	}

	/**
	 * loads the setup config
	 */
	public void loadSetupConfig() {
		// TODO
	}

	/**
	 * loads the layout config
	 */
	public void loadLayoutConfig() {
		// TODO
	}


	/*
	--------------------------------------------------
					Getters/Setters
	--------------------------------------------------
	*/

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	// TODO
	public Room getRoom(BoardCell room) {
		return new Room("temp room", room, room);
	}

	// TODO
	public Room getRoom(char roomInitial) {
		return new Room("temp room", new BoardCell(0, 0, roomInitial), new BoardCell(0, 0, roomInitial));
	}

	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

}
