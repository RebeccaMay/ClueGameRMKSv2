package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	private String roomInitial = ""; 	//used only for cards that are rooms
	public String getCardName(){
		return cardName;
	}
	public void setCardName(String s){
		cardName = s;
	}
	public void setCardType(CardType typeIn){
		type = typeIn;
	}
	public CardType getCardType(){
		return type;
	}
	public boolean equals(){
		return false; //currently not in use
		//accessing board for card equivalency tests instead
		//may use at a future date
	}
	public void setRoomInitial(String s){
		roomInitial = s;
	}
	public String getRoomInitial(){
		return roomInitial;
	}
}
