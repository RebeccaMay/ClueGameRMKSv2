package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;
import clueGame.Board;
import clueGame.Card;
import clueGame.Player;
import java.awt.Color;

public class gameSetupTests {
	private static Board board;
	@BeforeClass
	public static void setUp(){
		// Board is singleton, get the only instance and initialize it		
				board = Board.getInstance();
				board.setConfigFiles("data/ClueLayout.csv", "data/ClueLegend.txt");		
				board.setCardFile("data/cardConfigFile");
				board.setPlayerFile("data/playerConfigFile");
				board.initialize();
	}
	
	
	@Test
	public void loadPeopleTest(){
		
		//Testing attributes for the human player
		Player testPlayer = board.getPlayersInPlay().get(0);
		assertEquals(0, testPlayer.getPlayerRow());
		assertEquals(4, testPlayer.getPlayerCol());
		assertEquals("Buzz Lightyear", testPlayer.getPlayerName());
		System.out.println(testPlayer.getPlayerColor());
		System.out.println(Color.MAGENTA.toString());
		assertEquals(Color.MAGENTA, testPlayer.getPlayerColor());
		
		//Testing attributes for 3rd computer player
		testPlayer = board.getPlayersInPlay().get(3);
		assertEquals(23, testPlayer.getPlayerRow());
		assertEquals(9, testPlayer.getPlayerCol());
		assertEquals("Wall-E", testPlayer.getPlayerName());
		assertEquals(Color.GRAY, testPlayer.getPlayerColor());
		
		//Testing attributes for last computer player
		testPlayer = board.getPlayersInPlay().get(5);
		assertEquals(9, testPlayer.getPlayerRow());
		assertEquals(20, testPlayer.getPlayerCol());
		assertEquals("Matt Damon", testPlayer.getPlayerName());
		assertEquals(Color.RED, testPlayer.getPlayerColor());
		
	}

	@Test
	public void loadCardTest(){
		//Tests to assure that the deck contains the correct total number of cards
		
		ArrayList<ArrayList<Card>> testDeck = board.getFullDeck();
		for(int i = 0; i < testDeck.size(); i++){
			for(int j = 0; j < testDeck.get(i).size(); j++){
				System.out.println(testDeck.get(i).get(j).getCardName());
			}
		}
		assertEquals(3, testDeck.size());
		ArrayList<Card> testPeople = testDeck.get(0); //Makes array of just people cards
		assertEquals(6, testPeople.size()); 			// Makes sure that there are the correct number of people cards
		ArrayList<Card> testWeapons = testDeck.get(1); //Makes array of just weapon cards
		assertEquals(6, testWeapons.size());			//Makes sure that there are the correct number of weapons cards
		ArrayList<Card> testRooms = testDeck.get(2);	//Makes array of just room cards
		assertEquals(9, testRooms.size());				//Makes sure that there are the correct number of room cards
		//System.out.println(testWeapons.get(4).getCardName());
		//for(Card person : testPeople){
		//	System.out.println(person.getCardName());
		//}//
		assertEquals("Marvin the Martian", testPeople.get(2).getCardName());
		assertEquals("Laser Gun", testWeapons.get(2).getCardName());
		assertEquals("Jupiter", testRooms.get(4).getCardName());
	
	}
	
	@Test
	public void dealingTest(){
		//Tests to ensure that deck of cards has been distributed properly to all 6 players
		assertEquals(0, board.getNewDeck().size()); //all cards should be dealt - have been moved from new deck into individual players arrays
		
	}

}
