import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {

	public final int numRows;
	public final int numCols;

	private Box[][] boxes;

	public Board(int numRows, int numCols) {

		this.numRows = numRows;
		this.numCols = numCols;

		// initialize the array of boxes
		boxes = new Box[numRows][numCols];

		// build boxes

		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {

				// each box creates the line to the right and bottom of it
				Line rightLine = new Line();
				Line botLine = new Line();

				// pull the top line from the box above it, unless we are in the first row
				Line topLine;
				if (r == 0) {
					topLine = new Line();
				} else {
					// line is above box
					topLine = boxes[r - 1][c].getLine(Line.BOT_LINE);
				}

				// pull the left line from the box left of it, unless we are in the first column
				Line leftLine;
				if (c == 0) {
					leftLine = new Line();
				} else {
					// line is left of box
					leftLine = boxes[r][c - 1].getLine(Line.RIGHT_LINE);
				}

				// create the box
				Box box = new Box(leftLine, topLine, rightLine, botLine);
				
				// give the lines access to the box
				leftLine.setBox2(box);
				topLine.setBox2(box);
				
				rightLine.setBox1(box);
				botLine.setBox1(box);

				boxes[r][c] = box;
			}
		}

	}
	
	// copy constructor
	public Board(Board b) {
		
		this.numRows = b.numRows;
		this.numCols = b.numCols;

		boxes = new Box[numRows][numCols];
		
		// build boxes

		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				
				Box otherBox = b.boxes[r][c];
				
				// pull all four lines from the other box

				Line rightLine = new Line(otherBox.getLine(Line.RIGHT_LINE));
				Line botLine = new Line(otherBox.getLine(Line.BOT_LINE));

				Line topLine;
				if (r == 0) {
					topLine = new Line(otherBox.getLine(Line.TOP_LINE));
				} else {
					topLine = boxes[r - 1][c].getLine(Line.BOT_LINE);
				}

				Line leftLine;
				if (c == 0) {
					leftLine = new Line(otherBox.getLine(Line.LEFT_LINE));
				} else {
					leftLine = boxes[r][c - 1].getLine(Line.RIGHT_LINE);
				}

				// create the box
				Box box = new Box(leftLine, topLine, rightLine, botLine);
				
				// give lines access to box object
				leftLine.setBox2(box);
				topLine.setBox2(box);
				
				rightLine.setBox1(box);
				botLine.setBox1(box);
				
				box.setValue(otherBox.getValue());

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
	 * @throws Exception if play was illegal
	 */
	public boolean placeLine(int player, Dot dot1, Dot dot2) throws Exception {

		// keep track of whether play finished a box
		boolean extraTurn = false;

		// horizontal line
		if (dot1.r == dot2.r && Math.abs(dot1.c - dot2.c) == 1) {

			Dot leftDot;

			// find left dot and right dot
			if (dot1.c - dot2.c == 1) {
				leftDot = dot2;
			} else {
				leftDot = dot1;
			}

			// get r and c values of boxes
			int botBoxR = leftDot.r;
			int topBoxR = leftDot.r - 1;
			int boxC = leftDot.c;

			// update top box
			if (topBoxR >= 0) {
				Box b = boxes[topBoxR][boxC];
				Line l = b.getLine(Line.BOT_LINE);
				
				if (l.getValue() != Line.EMPTY) {
					throw new Exception("Line is not empty but played there anyway");
				}
				
				l.setValue(Line.FULL);

				if (b.updateBox(player)) {
					extraTurn = true;
				}
			}

			// update bottom box
			if (botBoxR < numRows) {
				Box b = boxes[botBoxR][boxC];
				
				Line l = b.getLine(Line.TOP_LINE);
				
				l.setValue(Line.FULL);

				if (b.updateBox(player)) {
					extraTurn = true;
				}
			}

		}

		// vertical line
		else if (dot1.c == dot2.c && Math.abs(dot1.r - dot2.r) == 1) {

			Dot topDot;

			// find top dot and bottom dot
			if (dot1.r - dot2.r == 1) {
				topDot = dot2;
			} else  {
				topDot = dot1;
			}

			// get r and c values of boxes
			int rightBoxC = topDot.c;
			int leftBoxC = topDot.c - 1;
			int boxR = topDot.r;

			// update left box
			if (leftBoxC >= 0) {
				Box b = boxes[boxR][leftBoxC];
				
				Line l = b.getLine(Line.RIGHT_LINE);
				
				l.setValue(Line.FULL);

				if (b.updateBox(player)) {
					extraTurn = true;
				}
			}

			// update right box
			if (rightBoxC < numCols) {
				Box b = boxes[boxR][rightBoxC];
				Line l = b.getLine(Line.LEFT_LINE);
				
				l.setValue(Line.FULL);

				if (b.updateBox(player)) {
					extraTurn = true;
				}
			}
		}

		// invalid - dots not next to each other
		else {
			throw new Exception("Invalid dot selection");
		}
		
		return extraTurn;

	}
	
	/**
	 * Given the dots played, undo a move (used in MiniMax algorithm to save memory)
	 * @param dot1
	 * @param dot2
	 * @throws Exception if the undo was illegal
	 */
	public void undoMove(Dot dot1, Dot dot2) throws Exception {
		
		if (dot1.r == dot2.r && Math.abs(dot1.c - dot2.c) == 1) {

			Dot leftDot;

			// find left dot and right dot
			if (dot1.c - dot2.c == 1) {
				leftDot = dot2;
			} else {
				leftDot = dot1;
			}

			// get r and c values of boxes
			int botBoxR = leftDot.r;
			int topBoxR = leftDot.r - 1;
			int boxC = leftDot.c;

			// top box
			if (topBoxR >= 0) {
				Box b = boxes[topBoxR][boxC];
				Line l = b.getLine(Line.BOT_LINE);
				
				if (l.getValue() == Line.EMPTY) {
					throw new Exception("Line is empty but tried to remove it");
				}
				
				l.setValue(Line.EMPTY);
				b.setValue(Box.EMPTY);
			}

			// bottom box
			if (botBoxR < numRows) {
				Box b = boxes[botBoxR][boxC];
				
				Line l = b.getLine(Line.TOP_LINE);
				
				l.setValue(Line.EMPTY);
				b.setValue(Box.EMPTY);
			}

		}

		// vertical line
		else if (dot1.c == dot2.c && Math.abs(dot1.r - dot2.r) == 1) {

			Dot topDot;

			// find top dot and bottom dot
			if (dot1.r - dot2.r == 1) {
				topDot = dot2;
			} else  {
				topDot = dot1;
			}

			// get r and c values of boxes
			int rightBoxC = topDot.c;
			int leftBoxC = topDot.c - 1;
			int boxR = topDot.r;

			// left box
			if (leftBoxC >= 0) {
				Box b = boxes[boxR][leftBoxC];
				
				Line l = b.getLine(Line.RIGHT_LINE);
				
				if (l.getValue() == Line.EMPTY) {
					throw new Exception("Line is empty but tried to remove it");
				}
				
				l.setValue(Line.EMPTY);
				b.setValue(Box.EMPTY);
			}

			// right box
			if (rightBoxC < numCols) {
				Box b = boxes[boxR][rightBoxC];
				Line l = b.getLine(Line.LEFT_LINE);
				
				l.setValue(Line.EMPTY);
				b.setValue(Box.EMPTY);
			}
		}

		// invalid - dots not next to each other
		else {
			throw new Exception("Invalid dot selection");
		}
	}
	
	/**
	 * build a list of valid moves to make
	 * @return
	 */
	private List<Move> getMoves() {
		
		// explored set of lines
		Set<Line> lines = new HashSet<>();
		
		// list of moves (composed of two dots on either end of the line)
		List<Move> moves = new ArrayList<>();
		
		// iterate through the boxes
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				
				Box box = boxes[r][c];
				
				// only check empty boxes - full boxes will not have any empty lines
				if (box.getValue() == Box.EMPTY) {
					
					// check the lines of the box
					for (int i = 0; i < 4; i++) {
						
						Line l = box.getLine(i);
						
						// only check unique lines
						if (l.getValue() == Line.EMPTY && !lines.contains(l)) {
						
							lines.add(l);
							
							// the max number of lines it will leave adjacent boxes with
							int filledAfter = l.numLinesFilledAfter();							
							
							// for each side, find the corresponding dots, make a new move object
							
							if (i == Line.LEFT_LINE) {
								
								Dot dot1 = new Dot(r, c);
								Dot dot2 = new Dot(r + 1, c);
								moves.add(new Move(dot1, dot2, filledAfter));
								
							} else if (i == Line.TOP_LINE) {
								
								Dot dot1 = new Dot(r, c);
								Dot dot2 = new Dot(r, c + 1);
								moves.add(new Move(dot1, dot2, filledAfter));
								
							} else if (i == Line.RIGHT_LINE) {
								
								Dot dot1 = new Dot(r, c + 1);
								Dot dot2 = new Dot(r + 1, c + 1);
								moves.add(new Move(dot1, dot2, filledAfter));
								
							} else {
								// bottom line
								
								Dot dot1 = new Dot(r + 1, c);
								Dot dot2 = new Dot(r + 1, c + 1);
								moves.add(new Move(dot1, dot2, filledAfter));
								
							}
							
						}
					}
				}
			}
		}
		
		return moves;
	}
	
	public List<Move> getOrderedMoves() {
		
		List<Move> moves = getMoves();
		
		// shuffle the list of moves
		Collections.shuffle(moves);
		
		// sort the list of moves
		moves.sort(new Comparator<Move>() {
			@Override
			public int compare(Move m1, Move m2) {
				return m1.moveType - m2.moveType;
			}
		});
		
		return moves;
	}

	public boolean isGameOver() {
		
		// if you find any empty box, the game is not over
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
		// the score for a given player is how many boxes have their value
		
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
	// alphabet of letters to be used for dots, to allow user input
	public static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwyxz";
	
	/**
	 * convert a character from user input to dot coordinates
	 * @param ch
	 * @return Dot object for that location
	 */
	public Dot getDot(char ch) {
		int index = alphabet.indexOf(ch);
		
		int r = index / (numCols + 1);
		int c = index % (numCols + 1);
		
		return new Dot(r, c);
	}
	
	/**
	 * convert the board to a human-readable string for console output
	 * @param withLetters use letters from alphabet instead of "+"s for easier user input
	 * @return
	 */
	public String toString(boolean withLetters) {
		StringBuilder sb = new StringBuilder();
		
		int position = 0;
		
		for (int r = 0; r < numRows; r++) {
			
			// top
			if (r == 0) {
				for (int c = 0; c < numCols; c++) {
					
					if (c == 0) {
						if (withLetters) {
							sb.append(alphabet.charAt(position));
							position += 1;
						} else {
							sb.append("+");
						}
					}
					
					if (boxes[r][c].getLine(Line.TOP_LINE).getValue() == Line.FULL) {
						sb.append("-");
					} else {
						sb.append(" ");
					}
					
					if (withLetters) {
						sb.append(alphabet.charAt(position));
						position += 1;
					} else {
						sb.append("+");
					}
					
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
					if (withLetters) {
						sb.append(alphabet.charAt(position));
						position += 1;
					} else {
						sb.append("+");
					}
				}
				
				if (boxes[r][c].getLine(Line.BOT_LINE).getValue() == Line.FULL) {
					sb.append("-");
				} else {
					sb.append(" ");
				}

				if (withLetters) {
					sb.append(alphabet.charAt(position));
					position += 1;
				} else {
					sb.append("+");
				}
				
			}
			
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return this.toString(false);
	}

}
