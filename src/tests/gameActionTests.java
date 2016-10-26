package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class gameActionTests {
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
	//selecting a target location
	@Test
	public void testTargetLocations(){
		ComputerPlayer testPlayer = new ComputerPlayer();
		//if no rooms in list, will select randomly from target list
		testPlayer.setPlayerRow(14);
		testPlayer.setPlayerCol(7);
		BoardCell testCell = board.getCellAt(testPlayer.getPlayerRow(), testPlayer.getPlayerCol());
		board.calcTargets(testCell.getRow(), testCell.getCol(), 2);
		Set<BoardCell> testTargets = board.getTargets();
		BoardCell targetCell = new BoardCell();
		int count0 = 0;
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int count4 = 0;
		int count5 = 0;
		int count6 = 0;
		int count7 = 0;
		for(int i = 0; i < 300; i++){
			targetCell =  testPlayer.pickLocation(testTargets);
			//14, 9
			if((targetCell.getRow() == 14) && (targetCell.getCol() == 9)) count0++;
			//15, 8
			if((targetCell.getRow() == 15) && (targetCell.getCol() == 8)) count1++;
			//16, 7
			if((targetCell.getRow() == 16) && (targetCell.getCol() == 7)) count2++;
			//15, 6
			if((targetCell.getRow() == 15) && (targetCell.getCol() == 6)) count3++;
			//14, 5
			if((targetCell.getRow() == 14) && (targetCell.getCol() == 5)) count4++;
			//13, 6
			if((targetCell.getRow() == 13) && (targetCell.getCol() == 6)) count5++;
			//12, 7
			if((targetCell.getRow() == 12) && (targetCell.getCol() == 7)) count6++;
			//13, 8
			if((targetCell.getRow() == 13) && (targetCell.getCol() == 8)) count7++;
		}
		assert(count0 > 1);
		assert(count1 > 1);
		assert(count2 > 1);
		assert(count3 > 1);
		assert(count4 > 1);
		assert(count5 > 1);
		assert(count6 > 1);
		assert(count7 > 1);
		
		//if room in list that was not just visited, must select it
		//test at cell (17,10) from one step away
		testPlayer.setPlayerRow(17);
		testPlayer.setPlayerCol(10);
		testCell = board.getCellAt(testPlayer.getPlayerRow(), testPlayer.getPlayerCol());
		board.calcTargets(testCell.getRow(), testCell.getCol(), 2);
		testTargets = board.getTargets();
		targetCell =  testPlayer.pickLocation(testTargets);
		assertEquals(17, targetCell.getRow());
		assertEquals(11, targetCell.getCol());
		//test at cell (13,1) from two steps away
		testPlayer.setPlayerRow(13);
		testPlayer.setPlayerCol(1);
		testCell = board.getCellAt(testPlayer.getPlayerRow(), testPlayer.getPlayerCol());
		board.calcTargets(testCell.getRow(), testCell.getCol(), 2);
		testTargets = board.getTargets();
		targetCell =  testPlayer.pickLocation(testTargets);
		assertEquals(11, targetCell.getRow());
		assertEquals(1, targetCell.getCol());
		
		//if room just visited is in list, each target (including room) selected randomly
		testPlayer.setPlayerRow(10);
		testPlayer.setPlayerCol(19);
		testCell = board.getCellAt(testPlayer.getPlayerRow(), testPlayer.getPlayerCol());
		board.calcTargets(testCell.getRow(), testCell.getCol(), 2);
		testTargets = board.getTargets();
		testPlayer.addRoomToRoomsVisited(board.getCellAt(11, 19));
		
		count0 = 0;
		count1 = 0;
		count2 = 0;
		count3 = 0;
		for(int i = 0; i < 300; i++){
			targetCell =  testPlayer.pickLocation(testTargets);
			//11,19
			if((targetCell.getRow() == 11) && (targetCell.getCol() == 19)) count0++;
			//9,20
			if((targetCell.getRow() == 9) && (targetCell.getCol() == 20)) count1++;
			//9,18
			if((targetCell.getRow() == 9) && (targetCell.getCol() == 18)) count2++;
			//10,17
			if((targetCell.getRow() == 10) && (targetCell.getCol() == 17)) count3++;
		}
		assert(count0 > 1);
		assert(count1 > 1);
		assert(count2 > 1);
		assert(count3 > 1);
	}
	//checking an accusation
	@Test
	public void checkAccusationTest(){
		//ComputerPlayer testPlayer = new ComputerPlayer();
		
		//set solution
		//Solution sol = new Solution();
		//sol.person = "Marvin the Martian";
		//sol.room = "Jupiter";
		//sol.weapon = "Candlestick";
		//call makeAccusation
		//testPlayer.makeAccusation();
		//check against solution
		//assertEquals(sol.person, testPlayer.getAccusation().person);
		//assertEquals(sol.weapon, testPlayer.getAccusation().weapon);
		//assertEquals(sol.room, testPlayer.getAccusation().room);
	}
	
	//disprove a suggestion
	
	//handling a suggestion
	
	//creating a suggestion
	@Test
	public void createSuggestionTest(){
		ComputerPlayer testPlayer = new ComputerPlayer();
		Solution testSuggestion = new Solution();
		testSuggestion = testPlayer.createSuggestion();
		
		//room matches current location
		assertEquals(testSuggestion.room, testPlayer.getCurrentRoom());
		
		//if only one weapon not seen, it is selected
		testPlayer.addToCardsSeen(board.getFullDeck().get(0));
		testPlayer.addToCardsSeen(board.getFullDeck().get(1));
		testPlayer.addToCardsSeen(board.getFullDeck().get(2));
		testPlayer.addToCardsSeen(board.getFullDeck().get(3));
		testPlayer.addToCardsSeen(board.getFullDeck().get(4)); //the 6th card, Matt Damon, has not been seen
		assertEquals("Matt Damon", testSuggestion.person);
		
		//if only one person not seen, it is selected
		testPlayer.addToCardsSeen(board.getFullDeck().get(6));
		testPlayer.addToCardsSeen(board.getFullDeck().get(7));
		testPlayer.addToCardsSeen(board.getFullDeck().get(8));
		testPlayer.addToCardsSeen(board.getFullDeck().get(9));
		testPlayer.addToCardsSeen(board.getFullDeck().get(10)); //the 11th card, Candlestick, has not been seen
		assertEquals("Candlestick", testSuggestion.weapon);
		
		//if multiple people not seen, randomly selected
		testPlayer.removeFromCardsSeen(board.getFullDeck().get(3));
		testPlayer.removeFromCardsSeen(board.getFullDeck().get(4));	//Cards not seen are at indices 3, 4, 5 for type people
		
		
		//if multiple weapons not seen, randomly selected
		testPlayer.removeFromCardsSeen(board.getFullDeck().get(9)); 
		testPlayer.removeFromCardsSeen(board.getFullDeck().get(10));	//Cards not seen are at indices 9, 10 ,11 for type weapons
		
		int count3 = 0;
		int count4= 0;
		int count5 = 0;
		int count9= 0;
		int count10= 0;
		int count11= 0;
		
		for(int i = 0; i < 300; i++){
			if(testSuggestion.person == "Wall-E") count3++;
			if(testSuggestion.person == "Niel Armstrong") count4++;
			if(testSuggestion.person == "Matt Damon") count5++;
			if(testSuggestion.weapon == "Meteor Strike") count9++;
			if(testSuggestion.weapon == "The Void") count10++;
			if(testSuggestion.weapon == "Candlestick") count11++;
		}
		
		assert(count3 > 1);
		assert(count4 > 1);
		assert(count5 > 1);
		assert(count9 > 1);
		assert(count10 > 1);
		assert(count11 > 1);
		
			
	}
}
