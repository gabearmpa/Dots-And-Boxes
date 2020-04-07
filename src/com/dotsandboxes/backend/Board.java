package com.dotsandboxes.backend;

public class Board {

	public final int numRows;
	public final int numCols;

	private Box[][] boxes;

	public Board(int numRows, int numCols) {

		this.numRows = numRows;
		this.numCols = numCols;

		boxes = new Box[numRows][numCols];

		// build boxes

		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {

				Line rightLine = new Line();
				Line botLine = new Line();

				Line topLine;
				if (r == 0) {
					topLine = new Line();
				} else {
					topLine = boxes[r - 1][c].getLine(Line.BOT_LINE);
				}

				Line leftLine;
				if (c == 0) {
					leftLine = new Line();
				} else {
					leftLine = boxes[r][c - 1].getLine(Line.RIGHT_LINE);
				}

				Box box = new Box(leftLine, topLine, rightLine, botLine);

				boxes[r][c] = box;
			}
		}

	}

	/**
	 * Play a line, updating the boxes, and return true if player gets another turn
	 * @param player
	 * @param dot1
	 * @param dot2
	 * @return
	 * @throws Exception
	 */
	public boolean placeLine(int player, Dot dot1, Dot dot2) throws Exception {

		boolean extraTurn = false;

		// horizontal line
		if (dot1.x == dot2.x && Math.abs(dot1.y - dot2.y) == 1) {

			Dot leftDot;

			if (dot1.y - dot2.y == 1) {
				leftDot = dot2;
			} else {
				leftDot = dot1;
			}

			int botBoxY = leftDot.y;
			int topBoxY = leftDot.y - 1;
			int boxX = leftDot.x;

			// top box
			if (topBoxY >= 0) {
				Box b = boxes[boxX][topBoxY];
				Line l = b.getLine(Line.BOT_LINE);
				
				if (l.getValue() != Line.EMPTY) {
					throw new Exception("Line is not empty but played there anyway");
				}
				
				l.setValue(Line.FULL);

				if (b.updateBox(player)) {
					extraTurn = true;
				}
			}

			// bottom box
			if (botBoxY < numRows) {
				Box b = boxes[boxX][botBoxY];
				
				Line l = b.getLine(Line.TOP_LINE);
				
				if (l.getValue() != Line.EMPTY) {
					//throw new Exception("Line is not empty but played there anyway");
				}
				
				l.setValue(Line.FULL);

				if (b.updateBox(player)) {
					extraTurn = true;
				}
			}

		}

		// vertical line
		else if (dot1.y == dot2.y && Math.abs(dot1.x - dot2.x) == 1) {

			Dot topDot;

			if (dot1.x - dot2.x == 1) {
				topDot = dot2;
			} else  {
				topDot = dot1;
			}

			int rightBoxX = topDot.x;
			int leftBoxX = topDot.x - 1;
			int boxY = topDot.y;

			// left box
			if (leftBoxX >= 0) {
				Box b = boxes[leftBoxX][boxY];
				
				Line l = b.getLine(Line.RIGHT_LINE);
				
				if (l.getValue() != Line.EMPTY) {
					throw new Exception("Line is not empty but played there anyway");
				}
				
				l.setValue(Line.FULL);

				if (b.updateBox(player)) {
					extraTurn = true;
				}
			}

			// right box
			if (rightBoxX < numCols) {
				Box b = boxes[rightBoxX][boxY];
				Line l = b.getLine(Line.LEFT_LINE);
				
				if (l.getValue() != Line.EMPTY) {
					//throw new Exception("Line is not empty but played there anyway");
				}
				
				l.setValue(Line.FULL);

				if (b.updateBox(player)) {
					extraTurn = true;
				}
			}
		}

		// invalid
		else {
			throw new Exception("Invalid dot selection");
		}
		
		return extraTurn;

	}
	
	public Box[][] getBoxes() {
		// copy
		return boxes;
	}

	public boolean isGameOver() {
		
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				
				Box b = boxes[r][c];
				
				if (b.getValue() == Box.EMPTY) {
					return false;
				}
				
			}
		}
		
		return true;
	}
	
	public int calculateScore(int player) {
		int score = 0;
		
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				
				Box b = boxes[r][c];
				
				if (b.getValue() == player) {
					score += 1;
				}
			}
		}
		
		return score;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int r = 0; r < numRows; r++) {
			
			// top
			if (r == 0) {
				for (int c = 0; c < numCols; c++) {
					
					if (c == 0) {
						sb.append("+");
					}
					
					if (boxes[r][c].getLine(Line.TOP_LINE).getValue() == Line.FULL) {
						sb.append("-");
					} else {
						sb.append(" ");
					}
					
					sb.append("+");
					
				}
				
				sb.append("\n");
			}
			
			// middle
			for (int c = 0; c < numCols; c++) {
				
				Box box = boxes[r][c];
				
				if (c == 0) {
					if (box.getLine(Line.LEFT_LINE).getValue() == Line.FULL) {
						sb.append("|");
					} else {
						sb.append(" ");
					}
				}
				
				if (box.getValue() != Box.EMPTY) {
					sb.append(box.getValue());
				} else {
					sb.append(" ");
				}
				
				if (box.getLine(Line.RIGHT_LINE).getValue() == Line.FULL) {
					sb.append("|");
				} else {
					sb.append(" ");
				}
			}
			
			sb.append("\n");
			
			// bottom
			for (int c = 0; c < numCols; c++) {
				
				if (c == 0) {
					sb.append("+");
				}
				
				if (boxes[r][c].getLine(Line.BOT_LINE).getValue() == Line.FULL) {
					sb.append("-");
				} else {
					sb.append(" ");
				}
				
				sb.append("+");
				
			}
			
			sb.append("\n");
		}
		
		return sb.toString();
	}

}
