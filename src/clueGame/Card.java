package clueGame;

public class Card {
	String cardName;
	CardType type;
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
}
