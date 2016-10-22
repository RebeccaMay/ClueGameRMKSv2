package clueGame;

import java.util.ArrayList;

public class Player {
	ArrayList<Card> deck = new ArrayList<Card>();
	String name;
	int playerRow;
	int playerCol;
	
	public String getPlayerName(){
		return name;
	}
}
