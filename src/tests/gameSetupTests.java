package tests;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Player;
import javafx.scene.paint.Color;

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
		Player testPlayer = board.gethumanPlayer();
		assertEquals(0, testPlayer.getPlayerRow());
		assertEquals(4, testPlayer.getPlayerCol());
		assertEquals("Buzz Lightyear", testPlayer.getPlayerName());
		//System.out.println(testPlayer.getPlayerColor());
		//System.out.println(Color.MAGENTA.toString());
		//assertEquals(Color.MAGENTA, testPlayer.getPlayerColor().toString());
		
		//Testing attributes for 3rd computer player
		testPlayer = board.getcomputerPlayer3();
		assertEquals(23, testPlayer.getPlayerRow());
		assertEquals(9, testPlayer.getPlayerCol());
		assertEquals("Wall-E", testPlayer.getPlayerName());
		//assertEquals(Color.GRAY, testPlayer.getPlayerColor());
		
		//Testing attributes for last computer player
		testPlayer = board.getcomputerPlayer5();
		assertEquals(9, testPlayer.getPlayerRow());
		assertEquals(20, testPlayer.getPlayerCol());
		assertEquals("Matt Damon", testPlayer.getPlayerName());
		//assertEquals(Color.RED, testPlayer.getPlayerColor());
		
	}

	@Test
	public void loadCardTest(){
		//Tests to assure that the deck contains the correct total number of cards
		
		ArrayList<ArrayList<Card>> testDeck = board.getFullDeck();
		assertEquals(3, testDeck.size());
		ArrayList<Card> testPeople = testDeck.get(0); //Makes array of just people cards
		assertEquals(6, testPeople.size()); 			// Makes sure that there are the correct number of people cards
		ArrayList<Card> testWeapons = testDeck.get(1); //Makes array of just weapon cards
		assertEquals(6, testWeapons.size());			//Makes sure that there are the correct number of weapons cards
		ArrayList<Card> testRooms = testDeck.get(2);	//Makes array of just room cards
		assertEquals(9, testRooms.size());				//Makes sure that there are the correct number of room cards
		
		assertEquals("Marvin the Martian", testDeck.get(0).get(2).getCardName());
		assertEquals("Laser Gun", testDeck.get(1).get(2).getCardName());
		assertEquals("Jupiter", testDeck.get(2).get(4).getCardName());
	
	}
	
	@Test
	public void dealingTest(){
		//Tests to ensure that deck of cards has been distributed properly to all 6 players
		assertEquals(0, board.getFullDeck().size()); //all cards should be dealt - have been moved from full deck into individual players arrays
		
		Player testPlayer = board.gethumanPlayer(); //make sure that all 6 players have 3 cards
		assertEquals(3, testPlayer.getDeck().size()); //so all cards are distributed evenly
		testPlayer = board.getcomputerPlayer1();
		assertEquals(3, testPlayer.getDeck().size());
		testPlayer = board.getcomputerPlayer2();
		assertEquals(3, testPlayer.getDeck().size());
		testPlayer = board.getcomputerPlayer3();
		assertEquals(3, testPlayer.getDeck().size());
		testPlayer = board.getcomputerPlayer4();
		assertEquals(3, testPlayer.getDeck().size());
		testPlayer = board.getcomputerPlayer5();
		assertEquals(3, testPlayer.getDeck().size());
		
		assertFalse(board.getcomputerPlayer4().getDeck().contains(testPlayer.getDeck().get(0)));
		assertFalse(board.getcomputerPlayer3().getDeck().contains(testPlayer.getDeck().get(1)));
		assertFalse(board.getcomputerPlayer2().getDeck().contains(testPlayer.getDeck().get(2)));
		
		testPlayer = board.getcomputerPlayer4();
		assertFalse(board.getcomputerPlayer3().getDeck().contains(testPlayer.getDeck().get(0)));
		assertFalse(board.getcomputerPlayer2().getDeck().contains(testPlayer.getDeck().get(1)));
		assertFalse(board.getcomputerPlayer1().getDeck().contains(testPlayer.getDeck().get(2)));
		
		testPlayer = board.getcomputerPlayer3();
		assertFalse(board.getcomputerPlayer2().getDeck().contains(testPlayer.getDeck().get(0)));
		assertFalse(board.getcomputerPlayer1().getDeck().contains(testPlayer.getDeck().get(1)));
		assertFalse(board.getcomputerPlayer5().getDeck().contains(testPlayer.getDeck().get(2)));
		
		testPlayer = board.getcomputerPlayer2();
		assertFalse(board.getcomputerPlayer1().getDeck().contains(testPlayer.getDeck().get(0)));
		assertFalse(board.getcomputerPlayer5().getDeck().contains(testPlayer.getDeck().get(1)));
		assertFalse(board.getcomputerPlayer4().getDeck().contains(testPlayer.getDeck().get(2)));
		
		testPlayer = board.getcomputerPlayer1();
		assertFalse(board.getcomputerPlayer5().getDeck().contains(testPlayer.getDeck().get(0)));
		assertFalse(board.getcomputerPlayer4().getDeck().contains(testPlayer.getDeck().get(1)));
		assertFalse(board.getcomputerPlayer3().getDeck().contains(testPlayer.getDeck().get(2)));
	}

}
