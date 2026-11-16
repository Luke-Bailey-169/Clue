package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;

public class GameSetupTests {
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

	// test loading human players
	@Test
	public void testLoadHumanPlayers() {
		// TODO
	}

	// test loading computer players
	@Test
	public void testLoadComputerPlayers() {
		// TODO
	}

	// test that the deck of all cards was created (composed of rooms, weapons, and people)
	@Test
	public void testDeckCreation() {
		// TODO
	}

	// test that the solution was dealt
	@Test
	public void testDealSolution() {
		// TODO
	}

	// test that the other cards are dealt to the players
	@Test
	public void testDealCardsToPlayers() {
		// TODO
	}
}
