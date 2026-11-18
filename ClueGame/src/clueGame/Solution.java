package clueGame;

/**
 * @author Ryan Steinglass
 * 
 * Solution
 */
public class Solution {
	private Card room;
	private Card person;
	private Card weapon;

	public Solution(Card room, Card person, Card weapon) {
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}

	public boolean equals(Solution other) {
		return (
			this.room.equals(other.getRoom()) &&
			this.person.equals(other.getPerson()) &&
			this.weapon.equals(other.getWeapon())
		);
	}

	public Card getRoom() { return room; }
	public Card getPerson() { return person; }
	public Card getWeapon() { return weapon; }
}
