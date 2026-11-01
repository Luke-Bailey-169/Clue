package experiment;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ryan Steinglass
 * @author Luke Bailey
 * 
 * TestBoard
 */
public class TestBoard {
	final static int COLS = 4;
	final static int ROWS = 4;

	TestBoardCell[][] board;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;

	// Constuctor
	public TestBoard() {
		board = new TestBoardCell[ROWS][COLS];
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();

		// make all the cells
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j ++) {
				board[i][j] = new TestBoardCell(i, j);
			}
		}
		
		// set up adjacency lists
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				TestBoardCell cell = getCell(row, col);
				if ((row - 1) >= 0) {
					cell.addAdjacency(getCell(row - 1, col));
				}
				if ((row + 1) < ROWS) {
					cell.addAdjacency(getCell(row + 1, col));
				}
				if ((col - 1) >= 0) {
					cell.addAdjacency(getCell(row, col - 1));
				}
				if ((col + 1) < COLS) {
					cell.addAdjacency(getCell(row, col + 1));
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
	public void calcTargets(TestBoardCell startCell, int pathLength) {
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
	private void calcTargetsHelper(TestBoardCell currentCell, int pathLength) {
		// Base case
		if (pathLength == 0) {
			targets.add(currentCell);
			return;
		}

		visited.add(currentCell);  // add the current cell to visited

		// recursively call the method on all adjacent cells, as long as the cell can be entered
		for (TestBoardCell adjCell : currentCell.getAdjList()) {
			if (!adjCell.getOccupied() && !visited.contains(adjCell)) {

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

	public TestBoardCell getCell(int row, int col) {
		return board[row][col];
	}

	/**
	 * gets the list of target cells calculated by calcTargets()
	 * 
	 * @return the list of target cells
	 */
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
}
