package clueGame;

import java.util.ArrayList;
import java.util.Set;

public class ComputerPlayer extends Player {
	private BoardCell lastRoomVisited = new BoardCell();
	private ArrayList<BoardCell> roomsVisited = new ArrayList<BoardCell>();
	public BoardCell pickLocation(Set<BoardCell> targets){ //-------------------------------------------RETURNING TEMP
		BoardCell temp = new BoardCell();
		return temp;
	}
	
	public void makeAccusation(){
		
	}
	
	//public Solution createSuggestion(){}
	
	//getter and setter
	public void addRoomToRoomsVisited(BoardCell room){
		
	}
}
