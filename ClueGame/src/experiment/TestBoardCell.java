package experiment;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Luke Bailey
 * 
 * TestBoardCell
 */
public class TestBoardCell {
	int row;
	int column;
	Set<TestBoardCell> adjList;
	boolean isRoom;
	boolean isOccupied;

	// Constructor that gives adjList a place in memory and sets row and column for this cell 
	public TestBoardCell(int rowNum, int columnNum) {
		adjList = new HashSet<>();
		row = rowNum;
		column = columnNum;
	}
	
	// Adds a different cell to this cell's adjacency list
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	// Returns this cell's adjacency list
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	// Sets the boolean that tracks if this cell is part of a room
	public void setIsRoom(boolean room) {
		isRoom = room;
	}
	
	// Gets the isRoom boolean
	public boolean isRoom() {
		return isRoom;
	}
	
	// Sets the boolean that tracks if this cell is occupied by a player
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}
	
	// Gets if this cell is occupied
	public boolean getOccupied() {
		return isOccupied;
	}

}
