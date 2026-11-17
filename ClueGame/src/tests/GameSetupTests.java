package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.HashSet;

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

	// test that the solution was dealt correctly and the rest of the deal was correct
	@Test
	public void testDeal() {
		// Solution Testing
		
		// Makes sure that the three cards are 1 weapon, 1 person, and 1 room
		// This also makes sure that we have three cards in the solution at all
		
		Solution solution = board.getSolution();
		
		assertEquals(solution.getPerson().getCardType(), CardType.PERSON);
		assertEquals(solution.getRoom().getCardType(), CardType.ROOM);
		assertEquals(solution.getWeapon().getCardType(), CardType.WEAPON);
		
		// Checks that every player gets an equal or roughly equal (+- 1) number of cards
		
		Map<String, Player> players = board.getPlayers();
		
		int handSize;
		int maxSize = 0;
		
		for (Player player : players.values()) { // This would probably fail if there were hand sizes of like 3, 4, 5, 6, but because of the way we deal cards players[0] will have the biggest hand size
			handSize = player.getHand().size();
			if (handSize > maxSize) {
				maxSize = handSize;
			}
			
			assertTrue((maxSize - handSize) <= 1);
		}
		
		// Checks that all cards are in a unique hand
		
		HashSet<Card> inHand = new HashSet<>();
		
		for (Player player : players.values()) {
			for (Card card : player.getHand()) {
				assertFalse(inHand.contains(card));
				inHand.add(card);
			}
		}
		
		// Checks that the solution cards are not in a hand
		
		assertFalse(inHand.contains(solution.getPerson()));
		assertFalse(inHand.contains(solution.getRoom()));
		assertFalse(inHand.contains(solution.getWeapon()));
	}


}
