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

	void calcTargets(TestBoardCell startCell, int pathLength) {
		// does nothing
	}

	TestBoardCell getCell(int row, int col) {
		return new TestBoardCell(row, col);
	}

	Set<TestBoardCell> getTargets() {
		return new HashSet<TestBoardCell>();
	}
}
