package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Solution;
import clueGame.HumanPlayer;

public class GameSolutionTest {
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


    // Accusation tests

    // Tests making a correct accusation
    @Test
    public void testCorrectAccusation() {
        // get the solution
        Solution correct = board.getSolution();

        // make a copy of it
        Solution accusation = new Solution(correct.getRoom(), correct.getPerson(), correct.getWeapon());

        // make an accusation using the copy
        assertTrue(board.checkAccusation(accusation));
    }

    // Tests making an accusation with the wrong answer
    @Test
    public void testAccusationWrongRoom() {
        // get the solution
        Solution correct = board.getSolution();

        // make an incorrect copy of it
        Solution accusation = new Solution(new Card("", CardType.ROOM), correct.getPerson(), correct.getWeapon());

        // make an accusation using the copy
        assertFalse(board.checkAccusation(accusation));
    }

    // Tests making an accusation with the wrong person
    @Test
    public void testAccusationWrongPerson() {
        // get the solution
        Solution correct = board.getSolution();

        // make an incorrect copy of it
        Solution accusation = new Solution(correct.getRoom(), new Card("", CardType.PERSON), correct.getWeapon());

        // make an accusation using the copy
        assertFalse(board.checkAccusation(accusation));
    }

    // Tests making an accusation with the wrong weapon
    @Test
    public void testAccusationWrongWeapon() {
        // get the solution
        Solution correct = board.getSolution();

        // make an incorrect copy of it
        Solution accusation = new Solution(correct.getRoom(), correct.getPerson(), new Card("", CardType.WEAPON));

        // make an accusation using the copy
        assertFalse(board.checkAccusation(accusation));
    }


    // Disprove Suggestion tests

    // If player has only one matching card it should be returned
    @Test
    public void testDisproveSuggestionOneMatchingCard() {
        // Create a player with a known hand
        HumanPlayer player = new HumanPlayer("", "", 0, 0);

        Card matching = new Card("room", CardType.ROOM);
        player.updateHand(matching);
        player.updateHand(new Card("person", CardType.PERSON));
        player.updateHand(new Card("weapon", CardType.WEAPON));

        
        // cards for the suggestion
        Card r = new Card("room", CardType.ROOM);
        Card p = new Card("", CardType.PERSON);
        Card w = new Card("", CardType.WEAPON);

        // get the disproving card, and make sure it is the correct card
        Card c = player.disproveSuggestion(new Solution(r, p, w));
        assertTrue(matching.equals(c));
    }

    // If players has >1 matching card, returned card should be chosen randomly
    @Test
    public void testDisproveSuggestionMultipleMatchingCards() {
        // TODO
    }

    // If player has no matching cards, null should be returned
    @Test
    public void testDisproveSuggestionNoMatchingCards() {
        // TODO
    }


    // Handle Suggestion tests

    // Suggestion no one can disprove should return null
    @Test
    public void testSuggestionCannotDisprove() {
        // TODO
    }

    // Suggestion only suggesting player can disprove should return null
    @Test
    public void testSuggestionSuggestingDisprove() {
        // TODO
    }

    // Suggestion only human can disprove should return answer (i.e., card that disproves suggestion)
    @Test
    public void testSuggestionHumanDisprove() {
        // TODO
    }

    // Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
    @Test
    public void testSuggestionTwoPlayersDisprove() {
        // TODO
    }
}