package experiment;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ryan Steinglass
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

		// TODO set adjacency lists for all the cells
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

		visited.add(startCell);

		// for all steps excluding the last
		for (int i = 0; i < pathLength - 1; i++) {
			// for each visited cell
			for (TestBoardCell visitedCell : visited) {
				// add its adjacent cells to visited
				Set<TestBoardCell> adjList = visitedCell.getAdjList();
				for (TestBoardCell adjCell : adjList) {
					visited.add(adjCell);
				}
			}
		}

		// final step of movement
		for (TestBoardCell visitedCell : visited) {
			if (!targets.contains(visitedCell)) {
				targets.add(visitedCell);
			}
		}
	}

	public TestBoardCell getCell(int row, int col) {
		return new TestBoardCell(row, col);
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
