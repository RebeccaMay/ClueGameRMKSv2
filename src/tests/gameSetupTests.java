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

public class gameSetupTests {
	private static Board board;
	@Before
	public void setUp(){
		// Board is singleton, get the only instance and initialize it		
				board = Board.getInstance();
				board.setConfigFiles("data/ClueLayout.csv", "data/ClueLegend.txt");		
				board.setCardFile("data/cardConfigFile");
				board.initialize();
	}
	
	@Test
	public void loadPeopleTest(){
		Set<Player> testPeople = board.getPeople();
		assertEquals(6, testPeople.size());
		Set<String> peopleNames = new HashSet<String>();
		for(Player testPerson : testPeople){
			peopleNames.add(testPerson.getPlayerName());
		}
		
		assertTrue(peopleNames.contains("Buzz Lightyear"));
		assertTrue(peopleNames.contains("ET"));
		assertTrue(peopleNames.contains("Marvin the Martian"));
		assertTrue(peopleNames.contains("Wall-E"));
		assertTrue(peopleNames.contains("Neil Armstrong"));
		assertTrue(peopleNames.contains("Matt Damon"));
	}
	
	

}
