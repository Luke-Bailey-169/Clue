package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Luke Bailey
 * @author Ryan Steinglass
 * 
 * ComputerPlayer
 */
public class ComputerPlayer extends Player {
	private static Board board;
	private static Map<String, Card> deck;

	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
		board = Board.getInstance();
		deck = board.getDeck();
	}
	
	public Solution createSuggestion(Room room) {
		// Checks if player is in the correct room
		BoardCell cell = room.getCenterCell();
		if (cell.getRow() != this.row || cell.getCol() != this.col) {
			return null;
		}
		
		// Make a suggestion of unseen cards
		ArrayList<Card> unseenWeapons = new ArrayList<>();
		ArrayList<Card> unseenPeople = new ArrayList<>();
		
		// Make arraylists to store all weapons/people to pick a random one if all have been seen already
		ArrayList<Card> weapons = new ArrayList<>();
		ArrayList<Card> people = new ArrayList<>();
		
		// Find unseen cards
		for (Card card : deck.values()) {
			if (!seenCards.contains(card)) {
				if (card.getCardType() == CardType.WEAPON) {
					unseenWeapons.add(card);
					weapons.add(card);
				} else if (card.getCardType() == CardType.PERSON) {
					unseenPeople.add(card);
					people.add(card);
				}
			}
		}
		
		Card weapon;
		Card person;
		
		// Make sure there are unseen cards		
		if (unseenWeapons.size() > 0) {
			int r = (int) (Math.random() * unseenWeapons.size());
			weapon = unseenWeapons.get(r);
		} else {
			int r = (int) (Math.random() * unseenWeapons.size());
			weapon = weapons.get(r);
		}
		
		if (unseenPeople.size() > 0) {
			int r = (int) (Math.random() * unseenPeople.size());
			person = unseenPeople.get(r);
		} else {
			int r = (int) (Math.random() * unseenPeople.size());
			person = people.get(r);
		}
		
		// Get the room Card instead of the room Room
		Card roomCard = deck.get(room.getName());
		
		Solution solution = new Solution(roomCard, person, weapon);
		return solution;
	}
	
	public BoardCell selectTarget(int roll) {
		// Loop through, check for unseen rooms, if none pick random
		Set<BoardCell> targets = new HashSet<>();
		
		BoardCell target = null;
		
		// Calculate potential targets
		BoardCell currentCell = board.getCell(row, col);
		board.calcTargets(currentCell, roll);
		targets = board.getTargets();
		
		// Check if any targets are rooms
		for (BoardCell cell : targets) {
			if (cell.isRoomCenter()) {
				if (!seenCards.contains(deck.get(board.getRoom(cell).getName()))) {
					target = cell;
					return target;
				}
			}
		}
		
		BoardCell[] targetArray = new BoardCell[targets.size()];
		int i = 0;
		for (BoardCell bc : targets) {
			targetArray[i] = bc;
			
			i++;
		}
		
		// No unseen room found, pick random target
		int r = (int) (Math.random() * targetArray.length);
		target = targetArray[r];
		
		return target;
	}

	@Override
	public boolean isHuman() { return false; }
}
