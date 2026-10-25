package tests;

import experiment.TestBoard;
import experiment.TestBoardCell;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ryan Steinglass
 * @author Luke Bailey
 * 
 * BoardTestsExp
 */

public class BoardTestsExp {
	TestBoard board;

	@BeforeEach
	public void setup() { // Reset testBoard before each test
		board = new TestBoard();
	}

	// Adjacency List Creation Tests

	// top left corner [0][0]
	@Test
	public void testAdjacencyListTopLeft() {
		Set<TestBoardCell> adjList = board.getCell(0, 0).getAdjList();
		assertEquals(2, adjList.size());
		assertTrue(adjList.contains(board.getCell(1, 0)));
		assertTrue(adjList.contains(board.getCell(0, 1)));
	}

	// bottom right corner [3][3]
	@Test
	public void testAdjacencyListBottomRight() {
		Set<TestBoardCell> adjList = board.getCell(3, 3).getAdjList();
		assertEquals(2, adjList.size());
		assertTrue(adjList.contains(board.getCell(2, 3)));
		assertTrue(adjList.contains(board.getCell(3, 2)));
	}

	// right edge [1][3]
	@Test
	public void testAdjacencyListRightEdge() {
		Set<TestBoardCell> adjList = board.getCell(1, 3).getAdjList();
		assertEquals(3, adjList.size());
		assertTrue(adjList.contains(board.getCell(0, 3)));
		assertTrue(adjList.contains(board.getCell(2, 3)));
		assertTrue(adjList.contains(board.getCell(1, 2)));
	}

	// left edge [3][0]
	@Test
	public void testAdjacencyListLeftEdge() {
		Set<TestBoardCell> adjList = board.getCell(3, 0).getAdjList();
		assertEquals(2, adjList.size());
		assertTrue(adjList.contains(board.getCell(2, 0)));
		assertTrue(adjList.contains(board.getCell(3, 1)));
	}
	
	
	// Target Creation Tests
	
	/*
	 * Test targets for multiple rolls and start locations
	 */
	
	// Test for rolling a 3 with no occupied squares
	@Test
	public void testTargetsEmptyThree() { 
		TestBoardCell cell = board.getCell(0, 0);
		
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(6, targets.size()); // Checks that calcTargets got the correct number of targets
		
		// Checks that calcTargets got the correct targets
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	// Test for rolling a 6 with no obstacles
	@Test
	public void testTargetsEmpptySix() { 
		TestBoardCell cell = board.getCell(0, 0);
		
		board.calcTargets(cell, 6);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(7, targets.size()); // Checks that calcTargets found right number of cells
		
		// Checks that calcTargets got correct targets
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(2, 2)));
	}
	
	// Test with different starting location rolling a 2
	@Test
	public void testTargetsEmptyTwo() { 
		TestBoardCell cell = board.getCell(1, 1);
		
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(6, targets.size()); // calcTargets finds correct number of cells
		
		// calcTargets got correct targets
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 2)));
	}
	
	/*
	 * Test targets when some cells are occupied or rooms
	 */
	
	// Test for rooms stopping movement
	@Test
	public void testTargetsRoom() { 
		// Set up rooms so player can only move 1
		TestBoardCell cell = board.getCell(0, 0);
		board.getCell(1, 0).setIsRoom(true);
		board.getCell(0, 1).setIsRoom(true);
		
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(2, targets.size()); // Checks that there are only 2 targets, should be (1,0) and (0,1)
		
		// Checks that the correct cells are found
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		
	}
	
	// Test for rolling a 3 with some cells occupied
	@Test
	public void testTargetsMixed() { 
		// set up occupied cells
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setIsRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(3, targets.size()); // Checks that calcTargets got the correct number of cells
		
		// Checks that calcTargets got correct cells
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
}
