package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;

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

	private Set<BoardCell> targets;
	private Set<BoardCell> visited;

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
	 * Creates and populates the board using layoutConfigFile
	 */
	public void loadLayoutConfig() {
		// read the input file into an ArrayList of strings
		File layoutFile = new File(layoutConfigFile);

		ArrayList<String> lines = new ArrayList<String>();

		try (Scanner reader = new Scanner(layoutFile)) {
			while (reader.hasNextLine()) {
				lines.add(reader.nextLine());
			}
		} catch (FileNotFoundException e) {
			// TODO exception stuff
			System.out.println(e.getMessage());
			return;
		}

		// set numRows and numColumns
		numColumns = lines.get(0).split(",").length;
		numRows = lines.size();

		// TODO create and populate the grid
		grid = new BoardCell[numRows][numColumns];
	}

	/**
	 * calculates the list of valid target cells for a turn's movement given a starting cell and path length
	 * stores the result in targets
	 * 
	 * @param startCell the cell you start on that turn
	 * @param pathLength the number of cells you must move that turn
	 */
	public void calcTargets(BoardCell startCell, int pathLength) {
		targets.clear();
		visited.clear();

		calcTargetsHelper(startCell, pathLength);
	}

	/**
	 * Helper method for calcTargets() to perform the recursion
	 * 
	 * @param currentCell the cell you are currently on during your movement
	 * @param pathLength the number of remaining steps you have this turn
	 */
	private void calcTargetsHelper(BoardCell currentCell, int pathLength) {
		// Base case
		if (pathLength == 0) {
			targets.add(currentCell);
			return;
		}

		visited.add(currentCell);  // add the current cell to visited

		// recursively call the method on all adjacent cells, as long as the cell can be entered
		for (BoardCell adjCell : currentCell.getAdjList()) {
			if (!adjCell.isOccupied() && !visited.contains(adjCell)) {

				// if the cell is a room, use all remaining movement to enter it
				if (!adjCell.isRoom()) {
					calcTargetsHelper(adjCell, pathLength - 1);
				} else {
					calcTargetsHelper(adjCell, 0);
				}
			}
		}

		visited.remove(currentCell);  // remove the current cell from visited
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
