package clueGame;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class BoardCell {
	private int row;
	private int col;
	private Character initial;
	private boolean isDoor;
	private DoorDirection doorDir;
	private ArrayList<Character> doorChars;

	public BoardCell() {
	}

	public BoardCell(int row, int col, String cellType) throws FileNotFoundException {
		super();
		this.row = row;
		this.col = col;
		doorChars = new ArrayList<Character>();
		doorChars.add('U');
		doorChars.add('D');
		doorChars.add('L');
		doorChars.add('R');

		Character initial = cellType.charAt(0);
		this.initial = initial;

		if (cellType.length() > 1) {
			Character secondChar = cellType.charAt(1);
			if (doorChars.contains(secondChar)) {
				isDoor = true;
				doorDir = DoorDirection.fromCharacter(secondChar);
			} else if (Character.toLowerCase(secondChar) == 'n') {

			} else {
				throw new BadConfigFormatException("Invalid second character for cell in " + this);
			}
		} else {
			isDoor = false;
		}

	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
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
