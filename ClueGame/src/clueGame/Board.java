package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
		this.targets = new HashSet<BoardCell>();
		this.visited = new HashSet<BoardCell>();

		try {
			loadSetupConfig();
			loadLayoutConfig();
		} catch (Exception e) {
			return;
		}

		setAdjacencies();
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
		this.layoutConfigFile = "data/" + layoutConfigFile;
		this.setupConfigFile = "data/" + setupConfigFile;
	}

	/**
	 * loads the setup config
	 */
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
		// initialize the roomMap
		roomMap = new HashMap<Character, Room>();

		// read the input file
		File setupFile = new File(setupConfigFile);

		try (Scanner reader = new Scanner(setupFile)) {
			while (reader.hasNextLine()) {
				String[] line = reader.nextLine().split(", ");

				if (line.length == 1) {
					continue;
				}

				if (!line[0].equals("//")) {
					// Check for correct formatting
					if (!line[0].equals("Room") && !line[0].equals("Space")) {
						throw new BadConfigFormatException("Setup file formatted incorrectly");
					}
					// make a room
					Room room = new Room(line[1]);
					roomMap.put(line[2].charAt(0), room);
				}
			}
		} catch (FileNotFoundException e) {
			throw e;
		}
	}

	/**
	 * Creates and populates the board using layoutConfigFile
	 */
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		// read the input file into an ArrayList of strings
		File layoutFile = new File(layoutConfigFile);

		ArrayList<String> lines = new ArrayList<String>();

		try (Scanner reader = new Scanner(layoutFile)) {
			while (reader.hasNextLine()) {
				lines.add(reader.nextLine());
			}
		} catch (FileNotFoundException e) {
			throw e;
		}

		// set numRows and numColumns
		numColumns = lines.get(0).split(",").length;
		numRows = lines.size();

		// create and populate the grid
		grid = new BoardCell[numRows][numColumns];

		for (int row = 0; row < numRows; row++) {
			String[] line = lines.get(row).split(",");

			// Check for board being rectangle
			if (line.length != numColumns) {
				throw new BadConfigFormatException("Board layout file does not contain the same number of columns in every row");
			}

			for (int col = 0; col < numColumns; col++) {
				String cellString = line[col];

				char initial = cellString.charAt(0);

				if (!roomMap.containsKey(initial)) {
					throw new BadConfigFormatException("Layout file includes room that does not exist");
				}

				// set default values for fields involving special properties
				DoorDirection doorDirection = DoorDirection.NONE;
				boolean roomCenter = false;
				boolean roomLabel = false;
				char secretPassage = '\0';	// default value so it can be passed to the BoardCell constructor

				// if the cell has a special property (ex. door, room center, secret passage, etc.)
				// then set the appropriate variables.
				if (cellString.length() == 2) {
					switch (cellString.charAt(1)) {
					case '^':	// up door
						doorDirection = DoorDirection.UP;
						break;
					case '>':	// right door
						doorDirection = DoorDirection.RIGHT;
						break;
					case 'v':	// down door
						doorDirection = DoorDirection.DOWN;
						break;
					case '<':	// left door
						doorDirection = DoorDirection.LEFT;
						break;
					case '*':	// room center
						roomCenter = true;
						break;
					case '#':	// room label
						roomLabel = true;
						break;
					default:	// secret passage
						secretPassage = cellString.charAt(1);
					}
				}

				grid[row][col] = new BoardCell(row, col, initial, doorDirection, roomLabel, roomCenter, secretPassage);
				if (initial != 'W' && initial != 'X') {
					grid[row][col].setIsRoom(true);
				}

				// Set room centers and labels
				if (roomCenter) {
					getRoom(initial).setCenterCell(grid[row][col]);
				}
				if (roomLabel) {
					getRoom(initial).setLabelCell(grid[row][col]);
				}
			}
		}
	}

	/**
	 * Sets the adjacency lists for all the cells
	 */
	private void setAdjacencies() {
		// set up adjacency lists
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				BoardCell cell = getCell(row, col);


				// proccess adjacent cells for walkways
				if (cell.getInitial() == 'W') {
					BoardCell adj = null;
					// up
					if ((row - 1) >= 0) {
						adj = getCell(row - 1, col);

						if (adj.getInitial() == cell.getInitial() && !adj.isRoom()) {
							cell.addAdj(adj);
						}

						if (adj.isDoorway()) {
							adj.addAdj(cell);
						}
					}

					// down
					if ((row + 1) < numRows) {
						adj = getCell(row + 1, col);

						if (adj.getInitial() == cell.getInitial() && !adj.isRoom()) {
							cell.addAdj(adj);
						}

						if (adj.isDoorway()) {
							adj.addAdj(cell);
						}
					}

					// left
					if ((col - 1) >= 0) {
						adj = getCell(row, col - 1);

						if (adj.getInitial() == cell.getInitial() && !adj.isRoom()) {
							cell.addAdj(adj);
						}

						if (adj.isDoorway()) {
							adj.addAdj(cell);
						}
					}

					// right
					if ((col + 1) < numColumns) {
						adj = getCell(row, col + 1);

						if (adj.getInitial() == cell.getInitial() && !adj.isRoom()) {
							cell.addAdj(adj);
						}

						if (adj.isDoorway()) {
							adj.addAdj(cell);
						}
					}

				}


				// proccess doorways
				if (cell.isDoorway()) {
					Room adjRoom = null;

					switch (cell.getDoorDirection()) {
					case DoorDirection.LEFT:
						adjRoom = getRoom(getCell(row, col - 1));
						break;
					case DoorDirection.RIGHT:
						adjRoom = getRoom(getCell(row, col + 1));
						break;
					case DoorDirection.UP:
						adjRoom = getRoom(getCell(row - 1, col));
						break;
					case DoorDirection.DOWN:
						adjRoom = getRoom(getCell(row + 1, col));
						break;
					case DoorDirection.NONE:
						break;
					}

					BoardCell target = adjRoom.getCenterCell();

					cell.addAdj(target);
					target.addAdj(cell);
				}

				// proccess secret passages
				if (cell.getSecretPassage() != '\0') {
					BoardCell adjSecret = getRoom(cell.getSecretPassage()).getCenterCell();
					BoardCell center = getRoom(cell).getCenterCell();
					center.addAdj(adjSecret);
					adjSecret.addAdj(center);
				}
			}
		}
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
				if (adjCell.isRoom()) {
					calcTargetsHelper(adjCell, 0);
				} else {
					calcTargetsHelper(adjCell, pathLength - 1);
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

	/**
	 * @return the number of rows in the board
	 */
	public int getNumRows() {
		return numRows;
	}

	/**
	 * @return the number of columns in the board
	 */
	public int getNumColumns() {
		return numColumns;
	}

	/**
	 * gets the list of target cells calculated by calcTargets()
	 * 
	 * @return the list of target cells
	 */
	public Set<BoardCell> getTargets() {
		return targets;
	}

	/**
	 * gets the room of a given cell
	 * 
	 * @return the room to which the given cell belongs
	 */
	public Room getRoom(BoardCell cell) {
		return getRoom(cell.getInitial());
	}

	/**
	 * gets the room with the given initial
	 * 
	 * @param roomInitial the initial of the room
	 * @return the room object corresponding to the given initial
	 */
	public Room getRoom(char roomInitial) {
		return roomMap.get(roomInitial);
	}

	/**
	 * gets the BoardCell at the given location
	 * 
	 * @return the BoardCell instance
	 */
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
	}
}
