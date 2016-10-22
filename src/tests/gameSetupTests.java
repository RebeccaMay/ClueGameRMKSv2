package tests;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Player;
import javafx.scene.paint.Color;

public class gameSetupTests {
	private static Board board;
	@Before
	public void setUp(){
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
	
	

}
