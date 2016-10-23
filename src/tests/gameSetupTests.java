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
		
		ArrayList<Card> testDeck = board.getFullDeck();
		
		assertEquals(21, testDeck.size());
		assertEquals(6, board.getPeopleCounter()); 			// Makes sure that there are the correct number of people cards
		assertEquals(6, board.getWeaponCounter());			//Makes sure that there are the correct number of weapons cards
		assertEquals(9, board.getRoomCounter());				//Makes sure that there are the correct number of room cards

		assertEquals("Marvin the Martian", testDeck.get(2).getCardName());
		assertEquals("Laser Gun", testDeck.get(8).getCardName());
		assertEquals("Jupiter", testDeck.get(16).getCardName());
	
	}
	
	@Test
	public void dealingTest(){
		//Tests to ensure that deck of cards has been distributed properly to all 6 players
		assertEquals(0, board.getNewDeck().size()); //all cards should be dealt - have been moved from new deck into individual players arrays
		
		assertEquals(3, board.getPlayersInPlay().get(0).getDeck().size());	//Because there are 6 players, all players should have 3 cards
		assertEquals(3, board.getPlayersInPlay().get(1).getDeck().size());
		assertEquals(3, board.getPlayersInPlay().get(2).getDeck().size());
		assertEquals(3, board.getPlayersInPlay().get(3).getDeck().size());
		assertEquals(3, board.getPlayersInPlay().get(4).getDeck().size());
		assertEquals(3, board.getPlayersInPlay().get(5).getDeck().size());
		
		assertFalse(board.getPlayersInPlay().get(0).getDeck().contains(board.getPlayersInPlay().get(1).getDeck().get(0)));
		assertFalse(board.getPlayersInPlay().get(1).getDeck().contains(board.getPlayersInPlay().get(2).getDeck().get(1)));
		assertFalse(board.getPlayersInPlay().get(2).getDeck().contains(board.getPlayersInPlay().get(3).getDeck().get(2)));
		assertFalse(board.getPlayersInPlay().get(3).getDeck().contains(board.getPlayersInPlay().get(4).getDeck().get(0)));
		assertFalse(board.getPlayersInPlay().get(4).getDeck().contains(board.getPlayersInPlay().get(5).getDeck().get(1)));
		assertFalse(board.getPlayersInPlay().get(5).getDeck().contains(board.getPlayersInPlay().get(0).getDeck().get(2)));
		assertFalse(board.getPlayersInPlay().get(0).getDeck().contains(board.getPlayersInPlay().get(1).getDeck().get(2)));
		assertFalse(board.getPlayersInPlay().get(1).getDeck().contains(board.getPlayersInPlay().get(2).getDeck().get(0)));
	}

}
