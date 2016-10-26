package clueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	private BoardCell lastRoomVisited = new BoardCell();
	private ArrayList<BoardCell> roomsVisited = new ArrayList<BoardCell>();
	private ArrayList<Card> cardsSeen = new ArrayList<Card>();
	private Solution accusation = new Solution();
	public BoardCell pickLocation(Set<BoardCell> targets){ 
		ArrayList<BoardCell> targetArray = new ArrayList<BoardCell>();
		for(BoardCell target : targets){
			targetArray.add(target);
		}
		boolean containsRoom = false;
		Random rand = new Random();
		
		for(BoardCell target : targets){
			if(target.isDoorway()){
				containsRoom = true;
				if(!roomsVisited.contains(target)){//if room in list that was not just visited, must select it
					
					return target;
				}
				break;
			}
		}
		//if no rooms in list, pick randomly
		if(!containsRoom){
			int index = rand.nextInt(targets.size());
			return targetArray.get(index);
		}
		
		//if room just visited is in list, each target (including room) selected randomly
		else{
			int index = rand.nextInt(targets.size());
			return targetArray.get(index);
		}
	}
	
	public void makeAccusation(){//-------------------------------------------------------------------------------------
		//get name of room for board cell at current location
		
	}
	
	public Solution createSuggestion(){
		Solution s = new Solution(); //------------------------------------------------------------------------------------
		return s;
	}
	
	//getter and setter
	public void addRoomToRoomsVisited(BoardCell room){
		roomsVisited.add(room);
	}
	
	public Solution getAccusation(){
		return accusation;
	}

	public String getCurrentRoom() {//-------------------------------------------------------------------------------------------
		return "hello";
	}
	public void addToCardsSeen(Card c){
		cardsSeen.add(c);
	}
	public void removeFromCardsSeen(Card c){	//Function purely for use in testing
		cardsSeen.remove(c);
	}
}
