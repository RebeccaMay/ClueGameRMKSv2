package clueGame;

import java.awt.Color;
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
}
