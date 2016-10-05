package clueGame;

public class BoardCell {
	private int row;
	private int col;
	private Character initial;
	private boolean isDoor;
	private DoorDirection doorDir;
	

	public BoardCell() {
	}

	public BoardCell(int row, int col, String cellType) {
		super();
		this.row = row;
		this.col = col;
		
		Character initial = cellType.charAt(0);
		this.initial = initial;
		
		if (cellType.length() > 1)
		{
			Character secondChar = cellType.charAt(1);
			if (secondChar != 'N')
			{
				isDoor = true;
				doorDir = DoorDirection.fromCharacter(secondChar);
			}
		}
		else
		{
			isDoor = false;
		}
		
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", col=" + col + "]";
	}

	public DoorDirection getDoorDirection() {
		return doorDir;
	}

	public boolean isDoorway() {
		return isDoor;
	}

	public char getInitial() {
		return initial;
	}
}
