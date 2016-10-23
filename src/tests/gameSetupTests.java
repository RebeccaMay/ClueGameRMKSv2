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
		assertEquals(0, testPlayer.getPlayerRow());//row
		assertEquals(4, testPlayer.getPlayerCol());//column
		assertEquals("Buzz Lightyear", testPlayer.getPlayerName());//name
		assertEquals(Color.MAGENTA, testPlayer.getPlayerColor());//color
		
		//Testing attributes for 3rd computer player
		testPlayer = board.getPlayersInPlay().get(3);
		assertEquals(23, testPlayer.getPlayerRow());//row
		assertEquals(9, testPlayer.getPlayerCol());//col
		assertEquals("Wall-E", testPlayer.getPlayerName());//name
		assertEquals(Color.GRAY, testPlayer.getPlayerColor());//color
		
		//Testing attributes for last computer player
		testPlayer = board.getPlayersInPlay().get(5);
		assertEquals(9, testPlayer.getPlayerRow());//row
		assertEquals(20, testPlayer.getPlayerCol());//col
		assertEquals("Matt Damon", testPlayer.getPlayerName());//name
		assertEquals(Color.RED, testPlayer.getPlayerColor());//color
		
	}

	@Test
	public void loadCardTest(){
		//Tests to assure that the deck contains the correct total number of cards
		
		ArrayList<Card> testDeck = board.getFullDeck();
		
		assertEquals(21, testDeck.size()); //Makes sure that there is the correct number of cards
		assertEquals(6, board.getPeopleCounter()); 			// Makes sure that there are the correct number of people cards
		assertEquals(6, board.getWeaponCounter());			//Makes sure that there are the correct number of weapon cards
		assertEquals(9, board.getRoomCounter());				//Makes sure that there are the correct number of room cards

		assertEquals("Marvin the Martian", testDeck.get(2).getCardName()); //checks that a person is loaded
		assertEquals("Laser Gun", testDeck.get(8).getCardName()); //checks that a weapon is loaded
		assertEquals("Jupiter", testDeck.get(16).getCardName()); //checks that a planet is loaded
	
	}
	
	@Test
	public void dealingTest(){
		//Tests to ensure that deck of cards has been distributed properly to all 6 players
		assertEquals(0, board.getNewDeck().size()); //all cards should be dealt - have been moved from new deck into individual players arrays
		
		//Because there are 6 players, all players should have 3 cards
		assertEquals(3, board.getPlayersInPlay().get(0).getDeck().size());	//human player has 3 cards
		assertEquals(3, board.getPlayersInPlay().get(1).getDeck().size()); //all computer players have 3 cards
		assertEquals(3, board.getPlayersInPlay().get(2).getDeck().size());
		assertEquals(3, board.getPlayersInPlay().get(3).getDeck().size());
		assertEquals(3, board.getPlayersInPlay().get(4).getDeck().size());
		assertEquals(3, board.getPlayersInPlay().get(5).getDeck().size());
		
		//variety of tests to make sure that cards in one player's deck are not in another player's deck
		assertFalse(board.getPlayersInPlay().get(0).getDeck().contains(board.getPlayersInPlay().get(1).getDeck().get(0))); //player 2 card 1 is not in player 1 deck
		assertFalse(board.getPlayersInPlay().get(1).getDeck().contains(board.getPlayersInPlay().get(2).getDeck().get(1))); //player 3 card 2 is not in player 2 deck
		assertFalse(board.getPlayersInPlay().get(2).getDeck().contains(board.getPlayersInPlay().get(3).getDeck().get(2))); //player 4 card 3 is not in player 3 deck
		assertFalse(board.getPlayersInPlay().get(3).getDeck().contains(board.getPlayersInPlay().get(4).getDeck().get(0))); //player 5 card 1 is not in player 4 deck
		assertFalse(board.getPlayersInPlay().get(4).getDeck().contains(board.getPlayersInPlay().get(5).getDeck().get(1))); //player 6 card 2 is not in player 5 deck
		assertFalse(board.getPlayersInPlay().get(5).getDeck().contains(board.getPlayersInPlay().get(0).getDeck().get(2))); //player 1 card 3 is not in player 6 deck
		assertFalse(board.getPlayersInPlay().get(0).getDeck().contains(board.getPlayersInPlay().get(1).getDeck().get(2))); //player 2 card 3 is not in player 1 deck
		assertFalse(board.getPlayersInPlay().get(1).getDeck().contains(board.getPlayersInPlay().get(2).getDeck().get(0))); //player 3 card 1 is not in player 2 deck
	}

}
