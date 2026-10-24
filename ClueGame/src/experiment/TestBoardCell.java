package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	int row;
	int column;
	Set<TestBoardCell> adjList;
	boolean isRoom;
	boolean isOccupied;

	public TestBoardCell(int rowNum, int columnNum) {
		adjList = new HashSet<>();
		row = rowNum;
		column = columnNum;
	}
	
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	public void setRoom(boolean room) {
		isRoom = room;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}

}
