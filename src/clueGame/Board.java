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
	
	private String layoutFile = "";
	private String legendFile = "";
	
	private int boardRows = 0;
	private int boardCols = 0;
	
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

	public void initialize() {

		String layout = "";
		try {
			FileReader layoutReader;
			layoutReader = new FileReader(layoutFile);
			Scanner layoutIn = new Scanner(layoutReader);
			boolean isFirstLine = true;
			while (layoutIn.hasNextLine()) {
				String line = layoutIn.nextLine();
				if (isFirstLine)
				{
					String[] split = line.split(",");
					boardCols = split.length;
					isFirstLine = false;
				}
				else
				{
					layout = layout.concat(",");
				}
				layout = layout.concat(line);
				
				
				boardRows++;
			}	
			layoutIn.close();
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		grid = new BoardCell[boardRows][boardCols];
		
		String[] layoutArray = layout.split(",");
		
		
		int counter = 0;
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				grid[i][j] = new BoardCell(i, j, layoutArray[counter]);
				counter++;
			}
		}
		
		
		try {
			FileReader legendReader;
			legendReader = new FileReader(legendFile);
			Scanner legendIn = new Scanner(legendReader);
			
			while (legendIn.hasNextLine()) {
				String line = legendIn.nextLine();
				String[] legendStrings = line.split(", ");
				
				System.out.println(line);
				System.out.println(legendStrings[1]);
				
				Character initial = legendStrings[0].charAt(0);
				String roomName = legendStrings[1];
				legend.put(initial, roomName);
			}	
			legendIn.close();

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

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

}
