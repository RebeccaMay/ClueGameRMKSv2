package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Board {
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	private Map<BoardCell, HashSet<BoardCell>> adjMtx;
	private BoardCell[][] grid;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private ArrayList<Card> fullDeck = new ArrayList<Card>();
	private String layoutFile = "";
	private String legendFile = "";
	private String cardFile = "";
	private String playerFile = "";
	private int boardRows = 0;
	private int boardCols = 0;
	private Map<Character, String> legend;
	ArrayList<Card> people = new ArrayList<Card>();
	ArrayList<Card> weapons = new ArrayList<Card>();
	ArrayList<Card> rooms = new ArrayList<Card>();
	private ArrayList<Card> newDeck = new ArrayList<Card>();
	ArrayList<Player> playersInPlay = new ArrayList<Player>();
	private int peopleCounter = 0;
	private int weaponCounter = 0;
	private int roomCounter = 0;
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
	
	public void setPlayerFile(String pf){
		playerFile = pf;
	}

	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
			loadCardConfig();
			loadPlayerConfig();
			dealDeck();
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
		while (cardIn.hasNextLine()){
			String line = cardIn.nextLine();
			String[] cardStrings = line.split(", ");
			Card nextCard = new Card();
			
			if(cardStrings[0].equals("PERSON")){
				nextCard.setCardName(cardStrings[1]);
				nextCard.setCardType(CardType.PERSON);
				peopleCounter++;
			}
			else if(cardStrings[0].equals("WEAPON")){
				nextCard.setCardName(cardStrings[1]);
				nextCard.setCardType(CardType.WEAPON);
				weaponCounter++;
			}
			else if(cardStrings[0].equals("ROOM")){
				nextCard.setCardName(cardStrings[1]);
				nextCard.setCardType(CardType.ROOM);
				roomCounter++;
			}
			fullDeck.add(nextCard);
		}
	
		//for(int i = 0; i < fullDeck.size(); i++){
			//for(int j = 0; j < fullDeck.get(i).size(); j++){
		//		System.out.println(fullDeck.get(i).get(j).getCardName());
		//	}
		//}
	}
	
	public void loadPlayerConfig() throws FileNotFoundException {
		FileReader playerReader;
		playerReader = new FileReader(playerFile);
		Scanner playerIn = new Scanner(playerReader);
		int counter = 0;
		while (playerIn.hasNextLine()){
			String line = playerIn.nextLine();
			String[] playerStrings = line.split(", ");
			if (counter == 0){
				Player newPlayer = new HumanPlayer();
				newPlayer.setPlayerName(playerStrings[0]);
				newPlayer.setPlayerColor(playerStrings[1]);
				newPlayer.setPlayerRow(Integer.parseInt(playerStrings[2]));
				newPlayer.setPlayerCol(Integer.parseInt(playerStrings[3]));
				playersInPlay.add(newPlayer);
			}
			else{
				Player newPlayer = new ComputerPlayer();
				newPlayer.setPlayerName(playerStrings[0]);
				newPlayer.setPlayerColor(playerStrings[1]);
				newPlayer.setPlayerRow(Integer.parseInt(playerStrings[2]));
				newPlayer.setPlayerCol(Integer.parseInt(playerStrings[3]));
				playersInPlay.add(newPlayer);
			}
			counter++;
		}
		playerIn.close();
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
			for (int j = 0; j < grid[i].length; j++){ // j iterates over each row in a column
				
				HashSet<BoardCell> tempSet = new HashSet<BoardCell>();
				
				if ((grid[i][j].getInitial() != 'W') && (!grid[i][j].isDoorway())){
					adjMtx.put(grid[i][j], tempSet);
					continue;
				}
				if (i != 0) {
					if ((grid[i-1][j].getInitial() == 'W') || (grid[i-1][j].isDoorway() && (grid[i-1][j].getDoorDirection() == DoorDirection.DOWN))){
						tempSet.add(grid[i-1][j]);
					}
				}
				if (i != grid.length - 1) {
					if ((grid[i+1][j].getInitial() == 'W') || (grid[i+1][j].isDoorway() && (grid[i+1][j].getDoorDirection() == DoorDirection.UP))){
						tempSet.add(grid[i+1][j]);
					}
				}
				if (j != 0) {
					if ((grid[i][j-1].getInitial() == 'W') || (grid[i][j-1].isDoorway() && (grid[i][j-1].getDoorDirection() == DoorDirection.RIGHT))){
						tempSet.add(grid[i][j-1]);
					}

				}
				if (j != grid[i].length - 1) {
					if ((grid[i][j+1].getInitial() == 'W') || (grid[i][j+1].isDoorway() && (grid[i][j+1].getDoorDirection() == DoorDirection.LEFT))){
						tempSet.add(grid[i][j+1]);
					}
				}

				adjMtx.put(grid[i][j], tempSet);
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
	

	public void dealDeck(){
		for(int i = 0; i < fullDeck.size(); i++){
			newDeck.add(fullDeck.get(i));
		}
		
		Random rand = new Random();
		int n = rand.nextInt(newDeck.size());

		Solution sol = new Solution();
		boolean personSelected = false;
		boolean weaponSelected = false;
		boolean roomSelected = false;
		
		while (personSelected == false){
			if (newDeck.get(n).getCardType() == CardType.PERSON){
				sol.person = newDeck.get(n).getCardName();
				newDeck.remove(n);
				personSelected = true;
			}
			n = rand.nextInt(newDeck.size()-1);
		}
		while (weaponSelected == false){
			if (newDeck.get(n).getCardType() == CardType.WEAPON){
				sol.weapon = newDeck.get(n).getCardName();
				newDeck.remove(n);
				weaponSelected = true;
			}
			n = rand.nextInt(newDeck.size()-1);
		}
		while (roomSelected == false){
			if (newDeck.get(n).getCardType() == CardType.ROOM){
				sol.room = newDeck.get(n).getCardName();
				newDeck.remove(n);
				roomSelected = true;
			}
			n = rand.nextInt(newDeck.size() -1);
		}
		
		while(newDeck.size() > 0){
			for (Player players : playersInPlay){
				if (newDeck.size() > 0){
					if (newDeck.size() > 1){
						n = rand.nextInt(newDeck.size() -1);
					}
					players.addCardtoDeck(newDeck.get(n));
					newDeck.remove(n);
				}
			}
		}
		
	}

	
	public ArrayList<Card> getFullDeck(){
		return fullDeck;
	}

	public ArrayList<Card> getNewDeck(){
		return newDeck;
	}
	
	public ArrayList<Player> getPlayersInPlay(){
		return playersInPlay;
	}
	public int getPeopleCounter(){
		return peopleCounter;
	}
	public int getWeaponCounter(){
		return weaponCounter;
	}
	public int getRoomCounter(){
		return roomCounter;
	}

}
