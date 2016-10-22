package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	// variable used for singleton pattern
	private static Board theInstance = new Board();

	private Map<BoardCell, HashSet<BoardCell>> adjMtx;
	private BoardCell[][] grid;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private Set<Card> fullDeck = new HashSet<Card>();
	private Set<Player> people = new HashSet<Player>();
	private String layoutFile = "";
	private String legendFile = "";
	private String cardFile = "";
	private String playerFile = "";
	private int boardRows = 0;
	private int boardCols = 0;
	private Player humanplayer;
	private Player computerplayer1;
	private Player computerplayer2;
	private Player computerplayer3;
	private Player computerplayer4;
	private Player computerplayer5;
	private Map<Character, String> legend;

	// ctor is private to ensure only one can be created
	private Board() {
		adjMtx = new HashMap<BoardCell, HashSet<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		legend = new HashMap<Character, String>();
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void setConfigFiles(String layout, String legend) {
		layoutFile = layout;
		legendFile = legend;
	}
	
	public void setCardFile(String card){
		cardFile = card;
	}
	
	public void setPlayerFile(String playerFile){
		playerFile = playerFile;
	}

	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
			loadCardConfig();
			loadPlayerConfig();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		calcAdjacencies();
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public int getNumRows() {
		return boardRows;
	}

	public int getNumColumns() {
		return boardCols;
	}

	public BoardCell getCellAt(int i, int j) {
		return grid[i][j];
	}

	public void loadRoomConfig() throws FileNotFoundException {
		FileReader legendReader;
		legendReader = new FileReader(legendFile);
		Scanner legendIn = new Scanner(legendReader);

		while (legendIn.hasNextLine()) {
			String line = legendIn.nextLine();
			String[] legendStrings = line.split(", ");

			Character initial = legendStrings[0].charAt(0);
			String roomName = legendStrings[1];
			legend.put(initial, roomName);
			if (!legendStrings[2].equals("Card") && !legendStrings[2].equals("Other"))
			{
				throw new BadConfigFormatException("Invalid room type (not 'Other' or 'Card') in " + legendFile + ": " + legendStrings[2]);
			}
		}
		legendIn.close();

	}

	public void loadBoardConfig() throws FileNotFoundException {
		String layout = "";
		
		FileReader layoutReader;
		layoutReader = new FileReader(layoutFile);
		Scanner layoutIn = new Scanner(layoutReader);
		boolean isFirstLine = true;
		while (layoutIn.hasNextLine()) {
			String line = layoutIn.nextLine();
			if (isFirstLine) {
				String[] split = line.split(",");
				boardCols = split.length;
				isFirstLine = false;
			} else {
				if (boardCols == line.split(",").length)
					layout = layout.concat(",");
				else
					throw new BadConfigFormatException("Incorrect column length in row " + boardRows);
			}
			layout = layout.concat(line);

			boardRows++;
		}
		layoutIn.close();

		grid = new BoardCell[boardRows][boardCols];

		String[] layoutArray = layout.split(",");

		int counter = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (legend.containsKey(layoutArray[counter].charAt(0)))
				{
					grid[i][j] = new BoardCell(i, j, layoutArray[counter]);
					counter++;
				}
				else
				{
					throw new BadConfigFormatException(layoutArray[counter] + " is not in legend specified by " + legendFile);
				}
			}
		}

	}
	
	public void loadCardConfig() throws FileNotFoundException {
		FileReader cardReader;
		cardReader = new FileReader(cardFile);
		Scanner cardIn = new Scanner(cardReader);
		
		
	}
	
	public void loadPlayerConfig() throws FileNotFoundException {
		FileReader playerReader;
		playerReader = new FileReader(playerFile);
		Scanner playerIn = new Scanner(playerReader);
		
		
	}

	public Set<BoardCell> getAdjList(int cellRow, int cellCol) {
		return adjMtx.get(getCellAt(cellRow,cellCol));
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public void calcAdjacencies()
	{
		for (int i = 0; i < grid.length; i++) // i iterates over columns
		{
			for (int j = 0; j < grid[i].length; j++) // j iterates over each row in a column
			{
				HashSet<BoardCell> tempSet = new HashSet<BoardCell>();
				
				if ((grid[i][j].getInitial() != 'W') && (!grid[i][j].isDoorway()))
				{
					
					adjMtx.put(grid[i][j], tempSet);
					continue;
				}
				if (i != 0) 
				{
					if ((grid[i-1][j].getInitial() == 'W') || (grid[i-1][j].isDoorway() && (grid[i-1][j].getDoorDirection() == DoorDirection.DOWN)))
					{
						tempSet.add(grid[i-1][j]);
					}
				}
				if (i != grid.length - 1) 
				{
					if ((grid[i+1][j].getInitial() == 'W') || (grid[i+1][j].isDoorway() && (grid[i+1][j].getDoorDirection() == DoorDirection.UP)))
					{
						tempSet.add(grid[i+1][j]);
					}
				}
				if (j != 0) 
				{
					if ((grid[i][j-1].getInitial() == 'W') || (grid[i][j-1].isDoorway() && (grid[i][j-1].getDoorDirection() == DoorDirection.RIGHT)))
					{
						tempSet.add(grid[i][j-1]);
					}

				}
				if (j != grid[i].length - 1) 
				{
					if ((grid[i][j+1].getInitial() == 'W') || (grid[i][j+1].isDoorway() && (grid[i][j+1].getDoorDirection() == DoorDirection.LEFT)))
					{
						tempSet.add(grid[i][j+1]);
					}
				}

				adjMtx.put(grid[i][j], tempSet);

				// if row = 0 and col = 0, add (0,j+1) and (i+1, and 0)
				// if row = # rows - 1 and col = # of cols - 1, add (0, j-1) and (i+1, 0)
				// if row = 0, but col is ok, add (0, j + 1), (0, j - 1), and (i + 1, j)
				// if row is ok, but col = 0, add (0, j + 1), (i - 1), (j - 1)
				// etc
			}
		}
	}

	public void calcTargets(int cellRow, int cellCol, int pathLength) {
		visited.clear();
		targets.clear();
		visited.add(getCellAt(cellRow,cellCol));
		findAllTargets(cellRow,cellCol, pathLength);
	}
	
	public void findAllTargets(int cellRow, int cellCol, int pathLength) {
		for (BoardCell cell : adjMtx.get(getCellAt(cellRow,cellCol)))
		{
			if (!visited.contains(cell))
			{
				visited.add(cell);
				if (pathLength == 1 || cell.isDoorway())
					targets.add(cell);
				else
					findAllTargets(cell.getRow(), cell.getCol(), pathLength - 1);
				
				visited.remove(cell);
			}
			
		}
	}
	
	public Set<Card> getFullDeck(){
		return fullDeck;
	}
	
	public Set<Player> getPeople(){
		return people;
	}
	public Player gethumanPlayer(){
		return humanplayer;
	}
	public Player getcomputerPlayer1(){
		return computerplayer1;
	}

	public Player getcomputerPlayer2(){
		return computerplayer2;
	}

	public Player getcomputerPlayer3(){
		return computerplayer3;
	}
	public Player getcomputerPlayer4(){
		return computerplayer4;
	}
	public Player getcomputerPlayer5(){
		return computerplayer5;
	}

}
