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
		System.out.println("\n\n\n");
		System.out.println(startCell + " " + pathLength);
		
		
		targets.clear();
		visited.clear();

		visited.add(startCell);
		
		Set<TestBoardCell> tempVisited = new HashSet<TestBoardCell>();

		// for all steps excluding the last
		for (int i = 0; i < pathLength - 1; i++) {
			// for each visited cell
			for (TestBoardCell visitedCell : visited) {
				// add its adjacent cells to visited
				Set<TestBoardCell> adjList = visitedCell.getAdjList();
				for (TestBoardCell adjCell : adjList) {
					if (!adjCell.getOccupied() && !adjCell.isRoom()) {
						tempVisited.add(adjCell);
					}
				}
			}
			for (TestBoardCell cell : tempVisited) {
				visited.add(cell);
			}
			
			System.out.println(visited);
		}
		

		// final step of movement
		for (TestBoardCell visitedCell : visited) {
			Set<TestBoardCell> adjList = visitedCell.getAdjList();
			for (TestBoardCell adjCell : adjList) {
				if (!visited.contains(adjCell) && !adjCell.getOccupied()) {
					targets.add(adjCell);
				}
			}
		}
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
