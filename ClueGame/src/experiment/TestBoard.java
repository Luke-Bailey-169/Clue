package experiment;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ryan Steinglass
 * 
 * TestBoard
 */
public class TestBoard {
	TestBoardCell[][] board;

	public TestBoard() {
		// empty constuctor
	}

	public void calcTargets(TestBoardCell startCell, int pathLength) {
		// does nothing
	}

	public TestBoardCell getCell(int row, int col) {
		return new TestBoardCell(row, col);
	}

	public Set<TestBoardCell> getTargets() {
		return new HashSet<TestBoardCell>();
	}
}
