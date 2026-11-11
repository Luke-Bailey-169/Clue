package clueGame;

import java.util.ArrayList;

public abstract class Player {
	private String name;
	private String color;
	private int row;
	private int col;

	private ArrayList<Card> hand;

	public Player(String name, String color, int row, int col) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;

		this.hand = new ArrayList<Card>();
	}

	public void updateHand(Card card) {
		hand.add(card);
	}

	public String getName() { return name; }
	public String getColor() { return color; }
	public int getRow() { return row; }
	public int getCol() { return col; }

	public ArrayList<Card> getHand() { return hand; }
}
