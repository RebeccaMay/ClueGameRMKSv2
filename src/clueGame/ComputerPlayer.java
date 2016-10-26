package clueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	private BoardCell lastRoomVisited = new BoardCell();
	private ArrayList<BoardCell> roomsVisited = new ArrayList<BoardCell>();
	private ArrayList<Card> cardsSeen = new ArrayList<Card>();
	private Solution accusation = new Solution();
	private String currentRoom = "";
	private static Board board;
	
	
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
		//create solution object in make accusation function
				//if everything is good, returns true
				//otherwise returns false
		
	}
	
	public Solution createSuggestion(){
		board = Board.getInstance();
		ArrayList<Card> peopleNotSeen = new ArrayList<Card>();
		ArrayList<Card> weaponNotSeen = new ArrayList<Card>();
		Solution s = new Solution(); 
		Random r = new Random();
		
		//room must be room player is currently in
		BoardCell currentCell = board.getCellAt(playerRow, playerCol);
		char cellInitial = currentCell.getInitial();
		for (int i = 0; i < board.getFullDeck().size(); i++){
			if (board.getFullDeck().get(i).getRoomInitial() == String.valueOf(cellInitial)){
				s.room = board.getFullDeck().get(i).getCardName();
				currentRoom = board.getFullDeck().get(i).getCardName();
			}
		}
		
		//find which people and weapons have not been seen
		for (int i = 0; i < board.getFullDeck().size(); i++){
			if (!cardsSeen.contains(board.getFullDeck().get(i))){
				//System.out.println(i);
				if(board.getFullDeck().get(i).getCardType() == CardType.PERSON){
					peopleNotSeen.add(board.getFullDeck().get(i));
				}
				if(board.getFullDeck().get(i).getCardType() == CardType.WEAPON){
					weaponNotSeen.add(board.getFullDeck().get(i));
				}
			}			
		}
		//if only one weapon unseen, must choose the unseen weapon
		if(weaponNotSeen.size() == 1){
			s.weapon = weaponNotSeen.get(0).getCardName();
		}
		//otherwise, choose weapon randomly
		else{
			int n = r.nextInt(weaponNotSeen.size());
			s.weapon = weaponNotSeen.get(n).getCardName();
		}
		
		//if only one person unseen, must choose the unseen person
		if(peopleNotSeen.size() == 1){
			s.person = peopleNotSeen.get(0).getCardName();
		}
		//otherwise, choose person randomly
		else{
			int n = r.nextInt(peopleNotSeen.size());
			s.person = peopleNotSeen.get(n).getCardName();
		}
		
		return s;
	}
	
	//getter and setter
	public void addRoomToRoomsVisited(BoardCell room){
		roomsVisited.add(room);
	}
	
	public Solution getAccusation(){
		return accusation;
	}
	public ArrayList<Card> getCardsSeen(){
		return cardsSeen;
	}

	public String getCurrentRoom() {
		return currentRoom;
	}
	public void addToCardsSeen(Card c){
		cardsSeen.add(c);
	}
	public void removeFromCardsSeen(Card c){	//Function purely for use in testing
		cardsSeen.remove(c);
	}
}
