package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;

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
        // TODO
    }

    // Tests making an accusation with the wrong person
    @Test
    public void testAccusationWrongPerson() {
        // TODO
    }

    // Tests making an accusation with the wrong weapon
    @Test
    public void testAccusationWrongWeapon() {
        // TODO
    }

    // Tests making an accusation with the wrong answer
    @Test
    public void testAccusationWrongRoom() {
        // TODO
    }


    // Disprove Suggestion tests

    // If player has only one matching card it should be returned
    @Test
    public void testDisproveSuggestionOneMatchingCard() {
        // TODO
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