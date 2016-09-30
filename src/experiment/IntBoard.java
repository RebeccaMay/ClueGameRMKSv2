package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, HashSet<BoardCell>> adjMtx;
	private BoardCell[][] grid;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	public IntBoard(int boardSize) {
		adjMtx = new HashMap<BoardCell, HashSet<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		grid = new BoardCell[boardSize][boardSize];
	}
	
	public void calcAdjacencies(BoardCell[][] grid, Map<BoardCell, HashSet<BoardCell>> adjMtx)
	{
		
	}
	
	public void calcTargets(BoardCell startCell, int pathLength)
	{
		
	}
	
	public Set<BoardCell> getTargets()
	{
		return null;
	}
	
	public Set<BoardCell> getAdjList(BoardCell startCell)
	{
		return null;
	}

	public BoardCell getCell(int i, int j) {
		return null;
	}

}
