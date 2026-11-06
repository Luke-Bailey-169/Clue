package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and
	// then do all the tests.
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load config files
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms() {
		// we want to test a couple of different rooms.
		// First, the mush-room that only has a single door but a secret room
		Set<BoardCell> testList = board.getAdjList(8, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(11, 6)));
		assertTrue(testList.contains(board.getCell(23, 22)));

		// now test the forest (note not marked since multiple test here)
		testList = board.getAdjList(20, 11);
		assertEquals(8, testList.size());
		assertTrue(testList.contains(board.getCell(2, 20)));

		// one more room, the fountain
		testList = board.getAdjList(16, 6);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(16, 4)));
		assertTrue(testList.contains(board.getCell(16, 8)));
		assertTrue(testList.contains(board.getCell(23, 1)));
	}

	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor() {
		Set<BoardCell> testList = board.getAdjList(0, 20);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(0, 19)));
		assertTrue(testList.contains(board.getCell(1, 20)));
		assertTrue(testList.contains(board.getCell(6, 22)));

		testList = board.getAdjList(16, 4);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(15, 4)));
		assertTrue(testList.contains(board.getCell(17, 4)));
		assertTrue(testList.contains(board.getCell(16, 3)));
		assertTrue(testList.contains(board.getCell(16, 6)));
	}

	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways() {
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(24, 7);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(23, 7)));
		assertTrue(testList.contains(board.getCell(24, 8)));

		// Test near a door but not adjacent
		testList = board.getAdjList(10, 17);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(10, 16)));
		assertTrue(testList.contains(board.getCell(10, 18)));
		assertTrue(testList.contains(board.getCell(11, 17)));

		// Test adjacent to walkways
		testList = board.getAdjList(17, 17);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(18, 17)));
		assertTrue(testList.contains(board.getCell(16, 17)));
		assertTrue(testList.contains(board.getCell(17, 16)));
		assertTrue(testList.contains(board.getCell(17, 18)));

		// Test next to big tree
		testList = board.getAdjList(10, 12);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(10, 11)));
		assertTrue(testList.contains(board.getCell(10, 13)));
		assertTrue(testList.contains(board.getCell(9, 12)));

	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInTreehouse() {
		// test a roll of 1
		board.calcTargets(board.getCell(14, 16), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(11, 16)));
		assertTrue(targets.contains(board.getCell(16, 17)));

		// test a roll of 3
		board.calcTargets(board.getCell(14, 16), 3);
		targets = board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(10, 15)));
		assertTrue(targets.contains(board.getCell(11, 18)));
		assertTrue(targets.contains(board.getCell(18, 17)));
		assertTrue(targets.contains(board.getCell(16, 15)));

		// test a roll of 4
		board.calcTargets(board.getCell(14, 16), 4);
		targets = board.getTargets();
		assertEquals(19, targets.size());
		assertTrue(targets.contains(board.getCell(15, 19)));
		assertTrue(targets.contains(board.getCell(16, 20)));
		assertTrue(targets.contains(board.getCell(11, 15)));
		assertTrue(targets.contains(board.getCell(10, 14)));
	}

	@Test
	public void testTargetsInPotionBrewery() {
		// test a roll of 1
		board.calcTargets(board.getCell(23, 22), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(22, 19)));
		assertTrue(targets.contains(board.getCell(8, 2)));

		// test a roll of 3
		board.calcTargets(board.getCell(23, 22), 3);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(21, 18)));
		assertTrue(targets.contains(board.getCell(24, 19)));
		assertTrue(targets.contains(board.getCell(12, 17)));
		assertTrue(targets.contains(board.getCell(8, 2)));

		// test a roll of 4
		board.calcTargets(board.getCell(20, 19), 4);
		targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(21, 19)));
		assertTrue(targets.contains(board.getCell(22, 16)));
		assertTrue(targets.contains(board.getCell(23, 17)));
		assertTrue(targets.contains(board.getCell(8, 2)));
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(0, 20), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(0, 19)));
		assertTrue(targets.contains(board.getCell(1, 20)));
		assertTrue(targets.contains(board.getCell(6, 22)));

		// test a roll of 3
		board.calcTargets(board.getCell(0, 20), 3);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(6, 22)));
		assertTrue(targets.contains(board.getCell(0, 19)));
		assertTrue(targets.contains(board.getCell(1, 20)));
		assertTrue(targets.contains(board.getCell(2, 19)));
		assertTrue(targets.contains(board.getCell(3, 20)));

		// test a roll of 4
		board.calcTargets(board.getCell(0, 20), 4);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(6, 22)));
		assertTrue(targets.contains(board.getCell(1, 19)));
		assertTrue(targets.contains(board.getCell(2, 20)));
		assertTrue(targets.contains(board.getCell(6, 17)));
		assertTrue(targets.contains(board.getCell(3, 19)));
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(14, 9), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(13, 9)));
		assertTrue(targets.contains(board.getCell(15, 9)));

		// test a roll of 3
		board.calcTargets(board.getCell(14, 9), 3);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(16, 8)));
		assertTrue(targets.contains(board.getCell(17, 9)));
		assertTrue(targets.contains(board.getCell(16, 10)));
		assertTrue(targets.contains(board.getCell(12, 8)));

		// test a roll of 4
		board.calcTargets(board.getCell(14, 9), 4);
		targets = board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(17, 10)));
		assertTrue(targets.contains(board.getCell(16, 6)));
		assertTrue(targets.contains(board.getCell(10, 9)));
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(13, 7), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(13, 6)));
		assertTrue(targets.contains(board.getCell(12, 7)));

		// test a roll of 3
		board.calcTargets(board.getCell(13, 7), 3);
		targets = board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(15, 6)));
		assertTrue(targets.contains(board.getCell(14, 7)));
		assertTrue(targets.contains(board.getCell(11, 8)));

		// test a roll of 4
		board.calcTargets(board.getCell(13, 7), 4);
		targets = board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(15, 9)));
		assertTrue(targets.contains(board.getCell(11, 5)));
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(13, 21).setOccupied(true);
		board.calcTargets(board.getCell(13, 23), 4);
		board.getCell(13, 21).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(11, 21)));
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(15, 23)));
		assertFalse(targets.contains(board.getCell(13, 21)));
		assertFalse(targets.contains(board.getCell(13, 19)));

		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(11, 17).setOccupied(true);
		board.getCell(14, 16).setOccupied(true);
		board.calcTargets(board.getCell(11, 16), 1);
		board.getCell(11, 17).setOccupied(false);
		board.getCell(14, 16).setOccupied(false);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(11, 15)));
		assertTrue(targets.contains(board.getCell(10, 16)));
		assertTrue(targets.contains(board.getCell(14, 16)));

		// check leaving a room with a blocked doorway
		board.getCell(11, 16).setOccupied(true);
		board.calcTargets(board.getCell(14, 16), 3);
		board.getCell(11, 16).setOccupied(false);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(16, 15)));
		assertTrue(targets.contains(board.getCell(17, 16)));
		assertTrue(targets.contains(board.getCell(18, 17)));
		assertTrue(targets.contains(board.getCell(17, 18)));
		assertTrue(targets.contains(board.getCell(16, 19)));

	}
}
