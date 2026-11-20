package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class ComputerAITest {

	private static Board board;
	
	private static Map<String, Card> deck;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load config files
		board.initialize();
		
		// Get the deck
		deck = board.getDeck();
	}

	// Computer Suggestion Tests
	
	// Test for single unseen weapon
	@Test
	public void testSuggestUnseenWeapon() {
		// Make new computer player for testing
		ComputerPlayer compPlayer = new ComputerPlayer("test", "green", 8, 2);
		
		// Add all but one weapon to seen
		compPlayer.updateSeen(deck.get("Big stick"));
		compPlayer.updateSeen(deck.get("Shovel"));
		compPlayer.updateSeen(deck.get("Very evil mushroom"));
		compPlayer.updateSeen(deck.get("Potion of owie"));
		compPlayer.updateSeen(deck.get("Huuuuge pitcher plant"));
		
		// Make a suggestion, make sure that it includes the only weapon left
		Solution suggestion = compPlayer.createSuggestion(board.getRoom('M'));
		assertTrue(suggestion.getWeapon().getCardName().equals("Super stinky fertilizer"));
	}
	
	// Test for single unseen person
	@Test
	public void testSuggestUnseenPerson() {
		// Make new computer player for testing
		ComputerPlayer compPlayer = new ComputerPlayer("test", "green", 8, 2);

		// Add all but one person to seen
		compPlayer.updateSeen(deck.get("Not so Crazy Dave"));
		compPlayer.updateSeen(deck.get("Meowscarada"));
		compPlayer.updateSeen(deck.get("Crazy Dave"));
		compPlayer.updateSeen(deck.get("Meganium"));
		compPlayer.updateSeen(deck.get("Traveler"));

		// Make a suggestion, make sure that it includes the only person left
		Solution suggestion = compPlayer.createSuggestion(board.getRoom('M'));
		assertTrue(suggestion.getPerson().getCardName().equals("Potionseller"));
	}
	
	// Test for multiple unseen weapons
	@Test
	public void testSuggestUnseenWeapons() {
		// Make new computer player for testing
		ComputerPlayer compPlayer = new ComputerPlayer("test", "green", 8, 2);

		// Add weapons to seen
		compPlayer.updateSeen(deck.get("Big stick"));
		compPlayer.updateSeen(deck.get("Shovel"));
		compPlayer.updateSeen(deck.get("Very evil mushroom"));

		// Make a number of suggestions, make sure none include the seen weapons
		for (int i = 0; i < 100; i++) {
			Solution suggestion = compPlayer.createSuggestion(board.getRoom('M'));
			assertFalse(compPlayer.getSeen().contains(suggestion.getWeapon()));
		}
	}
	
	// Test for multiple unseen people
	@Test
	public void testSuggestUnseenPeople() {
		// Make new computer player for testing
		ComputerPlayer compPlayer = new ComputerPlayer("test", "green", 8, 2);

		// Add people to seen
		compPlayer.updateSeen(deck.get("Crazy Dave"));
		compPlayer.updateSeen(deck.get("Meganium"));
		compPlayer.updateSeen(deck.get("Traveler"));

		// Make a number of suggestions, make sure none include the seen people
		for (int i = 0; i < 100; i++) {
			Solution suggestion = compPlayer.createSuggestion(board.getRoom('M'));
			assertFalse(compPlayer.getSeen().contains(suggestion.getPerson()));
		}
	}
	
	
	// Test the player is in the room
	@Test
	public void testSuggestRoom() {
		// Make new player in the treehouse
		ComputerPlayer compPlayer = new ComputerPlayer("test", "green", 14, 16);
		
		// Make an accusation with the treehouse, should pass
		Solution suggestion = compPlayer.createSuggestion(board.getRoom('T'));
		assertFalse(suggestion == null);
		
		// Make an accusation in a different room, should fail
		suggestion = compPlayer.createSuggestion(board.getRoom('M'));
		assertTrue(suggestion == null);
	}
	
	// Movement Tests
	
	// Test that player always enters a room if it is unseen and within range
	@Test
	public void testForcedEntry1() {
		// Make new player close to the Flower Garden
		ComputerPlayer compPlayer = new ComputerPlayer("test", "green", 9, 9);
		
		// Loop to make sure player always targets in the room
		for (int i = 0; i < 100; i++) { 
			// Call selectTarget with a roll of 3, just enough to enter Flower Garden
			BoardCell targetCell = compPlayer.selectTarget(4);

			// Ensure targetCell is the Flower Garden center
			assertTrue(targetCell.getRow() == 4);
			assertTrue(targetCell.getCol() == 10);			
		}
	}
	
	// Test that player always enters the unseen room if it can enter 2 and one is seen
	@Test
	public void testForcedEntry2() {
		// Make new player close to treehouse and forest
		ComputerPlayer compPlayer = new ComputerPlayer("test", "green", 9, 16);
		
		// Add forest to seen
		compPlayer.updateSeen(deck.get("Forest"));
		
		// Loop to make sure player always enters treehouse
		for (int i = 0; i < 100; i++) {
			// Roll a 3, enough to enter both forest and treehouse
			BoardCell targetCell = compPlayer.selectTarget(4);
			
			// Ensure that targetCell is treehouse center
			assertTrue(targetCell.getRow() == 14);
			assertTrue(targetCell.getCol() == 16);
		}
	}
	
	// Test for random movement if no room can be entered
	@Test
	public void testRandomMovement() {
		// New player
		ComputerPlayer compPlayer = new ComputerPlayer("test", "green", 14, 21);
		
		// Set booleans to false, will be set to true when the cell is a target
		boolean up = false;
		boolean down = false;
		boolean left = false;
		boolean right = false;
		
		// Loop several times to check for randomness
		for (int i = 0; i < 100; i++) {
			BoardCell targetCell = compPlayer.selectTarget(1);
			
			if (targetCell.getRow() == 13) { up = true; }
			if (targetCell.getRow() == 15) { down = true; }
			if (targetCell.getCol() == 20) { left = true; }
			if (targetCell.getCol() == 22) { right = true; }
		}
		
		// Check that all cells were targeted
		assertTrue(up);
		assertTrue(down);
		assertTrue(left);
		assertTrue(right);
	}
	
	// Test for random movement if only enterable room has been seen
	@Test
	public void testRandomMovementRoom() {
		// New player
		ComputerPlayer compPlayer = new ComputerPlayer("test", "green", 16, 4);
		
		compPlayer.updateSeen(deck.get("Fountain"));
		
		// Set booleans to false, will be set to true when the cell is a target
		boolean up = false;
		boolean down = false;
		boolean left = false;
		boolean right = false;

		// Loop several times to check for randomness
		for (int i = 0; i < 100; i++) {
			BoardCell targetCell = compPlayer.selectTarget(1);

			if (targetCell.getRow() == 15) { up = true; }
			if (targetCell.getRow() == 17) { down = true; }
			if (targetCell.getCol() == 3) { left = true; }
			if (targetCell.getCol() == 6) { right = true; }
		}

		// Check that all cells were targeted
		assertTrue(up);
		assertTrue(down);
		assertTrue(left);
		assertTrue(right);
	}
}
