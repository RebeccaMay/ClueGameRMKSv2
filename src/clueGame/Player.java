package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Player {
	ArrayList<Card> deck = new ArrayList<Card>();
	String name;
	int playerRow;
	int playerCol;
	Color color;
	
	public String getPlayerName(){
		return name;
	}
	public int getPlayerRow(){
		return playerRow;
	}
	public int getPlayerCol(){
		return playerCol;
	}
	
	public Color getPlayerColor(){
		return color;
	}
	
	public void setPlayerName(String n){
		name = n;
	}
	public void setPlayerRow(int y){
		playerRow = y;
	}
	public void setPlayerCol(int x){
		playerCol = x;
	}
	
	public void setPlayerColor(String c){
		color = convertColor(c);
	}
	
	public Color convertColor(String strColor){
		Color co;
		try{
			Field field = Class.forName("java.awt.Color").getField(strColor.trim()); 
			co = (Color)field.get(null);
		}catch (Exception e){
			co = null;
		}
		return co;
	}
	
	public void addCardtoDeck(Card c){
		deck.add(c);
	}
	
	public ArrayList<Card> getDeck(){
		return deck;
	}
	
	public Card disproveSuggestion(Solution suggestion){ 
		ArrayList<Card> matchingCards = new ArrayList<Card>();
		Random r = new Random();
		for (Card c : deck){
			if(c.getCardName().equals(suggestion.person)){
				matchingCards.add(c);
			}
			else if(c.getCardName().equals(suggestion.weapon)){
				matchingCards.add(c);
			}
			else if (c.getCardName().equals(suggestion.room)){
				matchingCards.add(c);
			}
		}
		
		if(matchingCards.size() == 0){
			return null;
		}
		else if(matchingCards.size() == 1){
			return matchingCards.get(0);
		}
		else{
			int n = r.nextInt(matchingCards.size());
			return matchingCards.get(n);
		}
	}
	
}
