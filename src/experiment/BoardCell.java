package experiment;

import clueGame.DoorDirection;

public class BoardCell {
	private int row;
	private int col;

	public BoardCell() {
	}

	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", col=" + col + "]";
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return DoorDirection.UP;
	}

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return false;
	}

	public char getInitial() {
		// TODO Auto-generated method stub
		return '\0';
	}
}
