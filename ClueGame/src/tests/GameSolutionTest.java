package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Solution;
import clueGame.HumanPlayer;
import clueGame.Player;

public class GameSolutionTest {
    // We make the Board static because we can load it one time and
	// then do all the tests.
	private static Board board;

    private static Solution answer;

    @BeforeAll
    public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load config files
		board.initialize();

        answer = board.getSolution();
	}


    // Accusation tests

    // Tests making a correct accusation
    @Test
    public void testCorrectAccusation() {

        // make a copy of it
        Solution accusation = new Solution(answer.getRoom(), answer.getPerson(), answer.getWeapon());

        // make an accusation using the copy
        assertTrue(board.checkAccusation(accusation));
    }

    // Tests making an accusation with the wrong answer
    @Test
    public void testAccusationWrongRoom() {
        // make an incorrect copy of answer
        Solution accusation = new Solution(new Card("", CardType.ROOM), answer.getPerson(), answer.getWeapon());

        // make an accusation using the copy
        assertFalse(board.checkAccusation(accusation));
    }

    // Tests making an accusation with the wrong person
    @Test
    public void testAccusationWrongPerson() {
        // make an incorrect copy of answer
        Solution accusation = new Solution(answer.getRoom(), new Card("", CardType.PERSON), answer.getWeapon());

        // make an accusation using the copy
        assertFalse(board.checkAccusation(accusation));
    }

    // Tests making an accusation with the wrong weapon
    @Test
    public void testAccusationWrongWeapon() {
        // make an incorrect copy of answer
        Solution accusation = new Solution(answer.getRoom(), answer.getPerson(), new Card("", CardType.WEAPON));

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

        Set<Card> matchedCards = new HashSet<Card>();

        // get the disproving cards
        for (int i = 0; i < 100; i++) {
            matchedCards.add(player.disproveSuggestion(new Solution(r, p, w)));
        }
        
        assertEquals(matchedCards.size(), 1);
        assertTrue(matchedCards.contains(matching));
    }

    // If players has >1 matching card, returned card should be chosen randomly
    @Test
    public void testDisproveSuggestionMultipleMatchingCards() {
        // Create a player with a known hand
        HumanPlayer player = new HumanPlayer("", "", 0, 0);

        player.updateHand(new Card("room", CardType.ROOM));
        Card matching1 = new Card("person", CardType.PERSON);
        player.updateHand(matching1);
        Card matching2 = new Card("weapon", CardType.WEAPON);
        player.updateHand(matching2);

        
        // cards for the suggestion
        Card r = new Card("", CardType.ROOM);
        Card p = new Card("person", CardType.PERSON);
        Card w = new Card("weapon", CardType.WEAPON);

        Set<Card> matchedCards = new HashSet<Card>();

        // get the disproving cards
        for (int i = 0; i < 100; i++) {
            matchedCards.add(player.disproveSuggestion(new Solution(r, p, w)));
        }
        
        assertEquals(matchedCards.size(), 2);
        assertTrue(matchedCards.contains(matching1));
        assertTrue(matchedCards.contains(matching2));
    }

    // If player has no matching cards, null should be returned
    @Test
    public void testDisproveSuggestionNoMatchingCards() {
        // Create a player with a known hand
        HumanPlayer player = new HumanPlayer("", "", 0, 0);

        player.updateHand(new Card("room", CardType.ROOM));
        player.updateHand(new Card("person", CardType.PERSON));
        player.updateHand(new Card("weapon", CardType.WEAPON));

        
        // cards for the suggestion
        Card r = new Card("", CardType.ROOM);
        Card p = new Card("", CardType.PERSON);
        Card w = new Card("", CardType.WEAPON);

        Set<Card> matchedCards = new HashSet<Card>();

        // get the disproving cards
        for (int i = 0; i < 100; i++) {
            matchedCards.add(player.disproveSuggestion(new Solution(r, p, w)));
        }
        
        assertEquals(matchedCards.size(), 1);
        assertTrue(matchedCards.contains(null));
    }


    // Handle Suggestion tests

    // Suggestion no one can disprove should return null
    @Test
    public void testSuggestionCannotDisprove() {
        Map<String, Player> players = board.getPlayers();

        assertEquals(board.handleSuggestion(players.get("h1"), answer), null);
    }

    // Suggestion only suggesting player can disprove should return null
    @Test
    public void testSuggestionOnlySuggestingDisprove() {
        Map<String, Player> players = board.getPlayers();
        Player player1 = players.get("h1");

        player1.updateHand(new Card("p1 card", CardType.ROOM));

        Solution suggestion = new Solution(
            new Card("p1 card", CardType.ROOM),
            answer.getPerson(),
            answer.getWeapon()
        );

        assertEquals(board.handleSuggestion(player1, suggestion), null);
    }

    // Suggestion only human can disprove should return answer (i.e., card that disproves suggestion)
    @Test
    public void testSuggestionHumanDisprove() {
        Map<String, Player> players = board.getPlayers();
        Player player1 = players.get("h1");

        player1.updateHand(new Card("p1 card", CardType.ROOM));

        Solution suggestion = new Solution(
            new Card("p1 card", CardType.ROOM),
            answer.getPerson(),
            answer.getWeapon()
        );

        assertTrue(board.handleSuggestion(players.get("h2"), suggestion).equals(new Card("p1 card", CardType.ROOM)));
    }

    // Suggestion that two players can disprove, answer player (based on starting with next player in list) returns answer
    @Test
    public void testSuggestionTwoPlayersDisprove() {
        Map<String, Player> players = board.getPlayers();
        Player player1 = players.get("h1");
        Player player2 = players.get("h2");

        player1.updateHand(new Card("p1 card", CardType.ROOM));
        player2.updateHand(new Card("p2 card", CardType.PERSON));

        Solution suggestion = new Solution(
            new Card("p1 card", CardType.ROOM),
            new Card("p2 card", CardType.PERSON),
            answer.getWeapon()
        );

        assertTrue(board.handleSuggestion(players.get("h3"), suggestion).equals(new Card("p1 card", CardType.ROOM)));
    }
}