package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import clueGame.BoardCell;

public class IntBoard {
	private Map<BoardCell, HashSet<BoardCell>> adjMtx;
	private BoardCell[][] grid;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private int boardSize;
	
	public IntBoard(int boardSize) {
		adjMtx = new HashMap<BoardCell, HashSet<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		grid = new BoardCell[boardSize][boardSize];
		this.boardSize = boardSize;
		
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				grid[i][j] = new BoardCell(i,j);
			}
		}
		calcAdjacencies();
	}
	
	public void calcAdjacencies()
	{
		for (int i = 0; i < grid.length; i++) // i iterates over columns
		{
			for (int j = 0; j < grid[i].length; j++) // j iterates over each row in a column
			{
				HashSet<BoardCell> tempSet = new HashSet<BoardCell>();
				if (i != 0) 
				{
					tempSet.add(grid[i-1][j]);
				}
				if (i != boardSize - 1) 
				{
					tempSet.add(grid[i+1][j]);

				}
				if (j != 0) 
				{
					tempSet.add(grid[i][j-1]);

				}
				if (j != boardSize - 1) 
				{
					tempSet.add(grid[i][j+1]);

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
	
	
	public void calcTargets(BoardCell startCell, int pathLength)
	{
		visited.add(startCell);
		for (BoardCell cell : adjMtx.get(startCell))
		{
			if (!visited.contains(cell))
			{
				visited.add(cell);
				if (pathLength == 1)
					targets.add(cell);
				else
					calcTargets(cell, pathLength - 1);
				
				visited.remove(cell);
			}
			
		}
		visited.remove(startCell);
	}
	
	public Set<BoardCell> getTargets()
	{
		return targets;
	}
	
	public Set<BoardCell> getAdjList(BoardCell startCell)
	{
		return adjMtx.get(startCell);
	}

	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}

}
