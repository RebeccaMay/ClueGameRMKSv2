package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;

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
		//System.out.println(strColor);
		try{
			//System.out.println("here 1");
			//Field field = Class.forName("java.awt.Color").getField(strColor.trim()); //something is 
			//System.out.println("here 2");
			//co = (Color)field.get(null);
			Field field = Color.class.getField(strColor.trim());
			co = (Color)field.get(null);
		}catch (Exception e){
			System.out.println(e);
			co = null;
		}
		//System.out.println(co);
		//System.out.println(Color.MAGENTA);
		//co = Color.GRAY;
		return co;
	}
	
	public void addCardtoDeck(Card c){
		deck.add(c);
	}
	
	public ArrayList<Card> getDeck(){
		return deck;
	}
}
