package clueGame;

/**
 * @author Ryan Steinglass
 * 
 * Card
 */
public class Card {
	private String cardName;
	private CardType cardType;

	public Card(String cardName, CardType cardType) {
		this.cardName = cardName;
		this.cardType = cardType;
	}

	public boolean equals(Card target) {
		return (
			this.cardName.equals(target.getCardName()) &&
			this.cardType == target.getCardType()
		);
	}

	public String getCardName() { return cardName; }
	public CardType getCardType() { return cardType; }
}
