package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import clueGame.Board;
import clueGame.Player;
import clueGame.Card;
import clueGame.Solution;
import clueGame.CardType;

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

	// test loading players
	@Test
	public void testLoadPlayers() {
		Map<String, Player> players = board.getPlayers();

		// assert that there are only 6 players
		assertEquals(players.size(), 6);

		// check if each player's name, color, location, and type (human/computer) is loaded correct
		assertTrue(players.containsKey("h1"));
		assertTrue(players.get("h1").isHuman());
		assertTrue(players.get("h1").getColor().equals("Red"));
		assertEquals(players.get("h1").getRow(), 14);
		assertEquals(players.get("h1").getCol(), 21);

		assertTrue(players.containsKey("h2"));
		assertTrue(players.get("h2").isHuman());
		assertTrue(players.get("h2").getColor().equals("Blue"));
		assertEquals(players.get("h2").getRow(), 9);
		assertEquals(players.get("h2").getCol(), 12);

		assertTrue(players.containsKey("c1"));
		assertFalse(players.get("c1").isHuman());
		assertTrue(players.get("c1").getColor().equals("Green"));
		assertEquals(players.get("c1").getRow(), 15);
		assertEquals(players.get("c1").getCol(), 2);

		assertTrue(players.containsKey("c2"));
		assertFalse(players.get("c2").isHuman());
		assertTrue(players.get("c2").getColor().equals("Yellow"));
		assertEquals(players.get("c2").getRow(), 22);
		assertEquals(players.get("c2").getCol(), 8);
	}

	// test that the deck of all cards was created (composed of 9 rooms, 6 weapons, and 6 people)
	@Test
	public void testDeckCreation() {
		Map<String, Card> deck = board.getDeck();

		assertEquals(deck.size(), 21);

		assertTrue(deck.get("Fountain").equals(new Card("Fountain", CardType.ROOM)));
		assertTrue(deck.get("h2").equals(new Card("h2", CardType.PERSON)));
		assertTrue(deck.get("Big stick").equals(new Card("Big stick", CardType.WEAPON)));
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
