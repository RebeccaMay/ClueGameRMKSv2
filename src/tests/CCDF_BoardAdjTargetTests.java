package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class CCDF_BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		board.setConfigFiles("data/CCDF_ClueLayout.csv", "data/CCDF_ClueLegend.txt");		
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(2, 4);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(8, 22);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(18, 11);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(8, 14);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(14, 4);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(9,5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(9,6)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(7,17);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(7,16)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(10, 11);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(11, 11)));
		//TEST DOORWAY UP
		testList = board.getAdjList(14, 11);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(13, 11)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(3, 6);
		assertTrue(testList.contains(board.getCellAt(2, 6)));
		assertTrue(testList.contains(board.getCellAt(4, 6)));
		assertTrue(testList.contains(board.getCellAt(3, 7)));
		assertTrue(testList.contains(board.getCellAt(3, 5)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(11, 2);
		assertTrue(testList.contains(board.getCellAt(10, 2)));
		assertTrue(testList.contains(board.getCellAt(12, 2)));
		assertTrue(testList.contains(board.getCellAt(11, 1)));
		assertTrue(testList.contains(board.getCellAt(11, 3)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(13, 20);
		assertTrue(testList.contains(board.getCellAt(13, 19)));
		assertTrue(testList.contains(board.getCellAt(13, 21)));
		assertTrue(testList.contains(board.getCellAt(12, 20)));
		assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(13,12);
		assertTrue(testList.contains(board.getCellAt(13, 11)));
		assertTrue(testList.contains(board.getCellAt(13, 13)));
		assertTrue(testList.contains(board.getCellAt(12, 12)));
		assertTrue(testList.contains(board.getCellAt(14, 12)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board. We'll have two cells in the adj list since there's also a door on the top edge.
		// There's no way to avoid this, because both of our walkways on the top edge have a door adjacent
		Set<BoardCell> testList = board.getAdjList(0, 6);
		assertTrue(testList.contains(board.getCellAt(1, 6)));
		assertTrue(testList.contains(board.getCellAt(0, 7)));
		assertEquals(2, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(5, 0);
		assertTrue(testList.contains(board.getCellAt(6, 0)));
		assertTrue(testList.contains(board.getCellAt(5, 1)));
		assertEquals(2, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(1, 18);
		assertTrue(testList.contains(board.getCellAt(0, 18)));
		assertTrue(testList.contains(board.getCellAt(2, 18)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(12, 15);
		assertTrue(testList.contains(board.getCellAt(11, 15)));
		assertTrue(testList.contains(board.getCellAt(13, 15)));
		assertTrue(testList.contains(board.getCellAt(12, 14)));
		assertTrue(testList.contains(board.getCellAt(12, 16)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(21, 17);
		assertTrue(testList.contains(board.getCellAt(20, 17)));
		assertTrue(testList.contains(board.getCellAt(21, 18)));
		assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 2 room pieces
		testList = board.getAdjList(7, 22);
		assertTrue(testList.contains(board.getCellAt(7, 21)));
		assertEquals(1, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(5, 5);
		assertTrue(testList.contains(board.getCellAt(5, 4)));
		assertTrue(testList.contains(board.getCellAt(5, 6)));
		assertTrue(testList.contains(board.getCellAt(6, 5)));
		assertEquals(3, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(4, 22, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 21)));
		
		board.calcTargets(12, 0, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 1)));
		assertTrue(targets.contains(board.getCellAt(11, 0)));	
		assertTrue(targets.contains(board.getCellAt(13, 0)));			
	}
	
	// Tests of just walkways, 2 steps
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(6, 0, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 1)));
		assertTrue(targets.contains(board.getCellAt(6, 2)));
		
		board.calcTargets(4, 8, 2);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 7)));
		assertTrue(targets.contains(board.getCellAt(4, 6)));	
		assertTrue(targets.contains(board.getCellAt(3, 7)));	
		assertTrue(targets.contains(board.getCellAt(3, 9)));
		assertTrue(targets.contains(board.getCellAt(4, 10)));			


	}
	
	// Tests of just walkways, 4 steps
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(21, 18, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 18)));
		assertTrue(targets.contains(board.getCellAt(18, 17)));
		assertTrue(targets.contains(board.getCellAt(20, 17)));
		assertTrue(targets.contains(board.getCellAt(19, 18)));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(7, 20, 4);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(11, 20)));	
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are DARK BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(4, 20, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 18)));
		assertTrue(targets.contains(board.getCellAt(2, 18)));	
		assertTrue(targets.contains(board.getCellAt(2, 19)));	
		assertTrue(targets.contains(board.getCellAt(3, 17)));	
		assertTrue(targets.contains(board.getCellAt(4, 16)));	
		assertTrue(targets.contains(board.getCellAt(6, 16)));	
		assertTrue(targets.contains(board.getCellAt(3, 15)));	
		assertTrue(targets.contains(board.getCellAt(5, 15)));	
		assertTrue(targets.contains(board.getCellAt(4, 14)));	

	}	
	
	// Test getting into a room
	// These are DARK BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(10, 6, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		// directly left (can't go right 2 steps)
		assertTrue(targets.contains(board.getCellAt(9, 5)));
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(11, 5)));
		assertTrue(targets.contains(board.getCellAt(12, 6)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(8, 6)));
		assertTrue(targets.contains(board.getCellAt(9, 7)));
		assertTrue(targets.contains(board.getCellAt(11, 7)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(12, 11, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(14, targets.size());

		// staying on the walkways
		assertTrue(targets.contains(board.getCellAt(12, 8)));
		assertTrue(targets.contains(board.getCellAt(11, 9)));
		assertTrue(targets.contains(board.getCellAt(13, 9)));
		assertTrue(targets.contains(board.getCellAt(12, 10)));
		assertTrue(targets.contains(board.getCellAt(11, 11)));
		assertTrue(targets.contains(board.getCellAt(13, 11)));
		assertTrue(targets.contains(board.getCellAt(12, 12)));
		assertTrue(targets.contains(board.getCellAt(11, 13)));
		assertTrue(targets.contains(board.getCellAt(13, 13)));
		assertTrue(targets.contains(board.getCellAt(12, 14)));

		// into the rooms
		assertTrue(targets.contains(board.getCellAt(10, 11)));
		assertTrue(targets.contains(board.getCellAt(14, 11)));		
		assertTrue(targets.contains(board.getCellAt(14, 10)));		
		assertTrue(targets.contains(board.getCellAt(14, 12)));		
		
	}

	// Test getting out of a room
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(7, 14, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 15)));
		// Take two steps
		board.calcTargets(7, 14, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 15)));
		assertTrue(targets.contains(board.getCellAt(7, 16)));
		assertTrue(targets.contains(board.getCellAt(8, 15)));
	}

}