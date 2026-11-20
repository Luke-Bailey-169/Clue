package clueGame;

import java.util.ArrayList;

/**
 * @author Ryan Steinglass
 * @author Luke Bailey
 * 
 * Player
 */
public abstract class Player {
	protected String name;
	protected String color;
	protected int row;
	protected int col;

	private ArrayList<Card> hand;
	protected ArrayList<Card> seenCards;

	public Player(String name, String color, int row, int col) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;

		this.hand = new ArrayList<Card>();
		this.seenCards = new ArrayList<Card>();
	}

	/**
	 * Adds the card the player's hand
	 * 
	 * @param card
	 */
	public void updateHand(Card card) {
		hand.add(card);
		updateSeen(card);
	}
	
	public void updateSeen(Card seenCard) {
		seenCards.add(seenCard);
	}

	/**
	 * A player tries to dispute a suggestion with the cards in their hand.
	 * If the player cannot, null is returned.
	 * If a player can, the player returns the card.
	 * If is more than one card that can dispute the suggestion, one is randomly chosen.
	 * 
	 * @param suggestion
	 * @return A card that would disprove the suggestion
	 */
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> disprovingCards = new ArrayList<Card>();

		for (Card card : hand) {
			if (card.equals(suggestion.getRoom()) || card.equals(suggestion.getPerson()) || card.equals(suggestion.getWeapon())) {
				disprovingCards.add(card);
			}
		}

		int r = (int) (Math.random() * disprovingCards.size());
		return (disprovingCards.size() > 0) ? disprovingCards.get(r) : null;
	}
	
	public void suggest(Room room) {
		Board board = Board.getInstance();
		Solution solution = createSuggestion(room);
		
		board.handleSuggestion(this, solution);
	}
	
	public abstract Solution createSuggestion(Room room);

	public abstract boolean isHuman();

	public String getName() { return name; }
	public String getColor() { return color; }
	public int getRow() { return row; }
	public int getCol() { return col; }

	public ArrayList<Card> getHand() { return hand; }
	public ArrayList<Card> getSeen() { return seenCards; }
}
