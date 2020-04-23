package com.dotsandboxes.backend;

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
					// line is above box
					topLine = boxes[r - 1][c].getLine(Line.BOT_LINE);
				}

				Line leftLine;
				if (c == 0) {
					leftLine = new Line();
				} else {
					// line is left of box
					leftLine = boxes[r][c - 1].getLine(Line.RIGHT_LINE);
				}

				Box box = new Box(leftLine, topLine, rightLine, botLine);
				
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

				Box box = new Box(leftLine, topLine, rightLine, botLine);
				
				leftLine.setBox2(box);
				topLine.setBox2(box);
				
				rightLine.setBox1(box);
				botLine.setBox1(box);
				
				box.setValue(otherBox.getValue());

				boxes[r][c] = box;
			}
		}
	}
	
	
	public static long totalPlaceLineNanos = 0;
	public static long totalUndoLineNanos = 0;
	public static long totalGetMovesNanos = 0;
	public static long totalSortMovesNanos = 0;

	/**
	 * Play a line, updating the boxes, and return true if player gets another turn
	 * @param player
	 * @param dot1
	 * @param dot2
	 * @return
	 * @throws Exception
	 */
	public boolean placeLine(int player, Dot dot1, Dot dot2) throws Exception {
		
		long startTime = System.nanoTime();

		boolean extraTurn = false;

		// horizontal line
		if (dot1.r == dot2.r && Math.abs(dot1.c - dot2.c) == 1) {

			Dot leftDot;

			if (dot1.c - dot2.c == 1) {
				leftDot = dot2;
			} else {
				leftDot = dot1;
			}

			int botBoxR = leftDot.r;
			int topBoxR = leftDot.r - 1;
			int boxC = leftDot.c;

			// top box
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

			// bottom box
			if (botBoxR < numRows) {
				Box b = boxes[botBoxR][boxC];
				
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
		else if (dot1.c == dot2.c && Math.abs(dot1.r - dot2.r) == 1) {

			Dot topDot;

			if (dot1.r - dot2.r == 1) {
				topDot = dot2;
			} else  {
				topDot = dot1;
			}

			int rightBoxC = topDot.c;
			int leftBoxC = topDot.c - 1;
			int boxR = topDot.r;

			// left box
			if (leftBoxC >= 0) {
				Box b = boxes[boxR][leftBoxC];
				
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
			if (rightBoxC < numCols) {
				Box b = boxes[boxR][rightBoxC];
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
		
		long endTime = System.nanoTime();
		
		totalPlaceLineNanos += endTime - startTime;
		
		return extraTurn;

	}
	
	public void undoMove(Dot dot1, Dot dot2) throws Exception {
		
		long startTime = System.nanoTime();

		if (dot1.r == dot2.r && Math.abs(dot1.c - dot2.c) == 1) {

			Dot leftDot;

			if (dot1.c - dot2.c == 1) {
				leftDot = dot2;
			} else {
				leftDot = dot1;
			}

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
				
				if (l.getValue() == Line.EMPTY) {
					//throw new Exception("Line is empty but tried to remove it");
				}
				
				l.setValue(Line.EMPTY);
				b.setValue(Box.EMPTY);
			}

		}

		// vertical line
		else if (dot1.c == dot2.c && Math.abs(dot1.r - dot2.r) == 1) {

			Dot topDot;

			if (dot1.r - dot2.r == 1) {
				topDot = dot2;
			} else  {
				topDot = dot1;
			}

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
				
				if (l.getValue() == Line.EMPTY) {
					//throw new Exception("Line is empty but tried to remove it");
				}
				
				l.setValue(Line.EMPTY);
				b.setValue(Box.EMPTY);
			}
		}

		// invalid
		else {
			throw new Exception("Invalid dot selection");
		}
		
		long endTime = System.nanoTime();
		
		totalUndoLineNanos += endTime - startTime;
	}
	
	public Box[][] getBoxes() {
		// copy
		return boxes;
	}
	
	private List<Move> getMoves() {
		
		long startTime = System.nanoTime();
		
		Set<Line> lines = new HashSet<>();
		
		List<Move> moves = new ArrayList<>();
		
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				
				Box box = boxes[r][c];
				
				if (box.getValue() == Box.EMPTY) {
					for (int i = 0; i < 4; i++) {
						Line l = box.getLine(i);
						
						// only check unique lines
						if (l.getValue() == Line.EMPTY && !lines.contains(l)) {
						
							lines.add(l);
							
							int filledAfter = l.numLinesFilledAfter();							
							
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
		
		long endTime = System.nanoTime();
		
		totalGetMovesNanos += endTime - startTime;
		
		return moves;
	}
	
	public List<Move> getOrderedMoves() {
		
		List<Move> moves = getMoves();
		
		long startTime = System.nanoTime();
		
		Collections.shuffle(moves);
		
		moves.sort(new Comparator<Move>() {
			@Override
			public int compare(Move m1, Move m2) {
				return m1.moveType - m2.moveType;
			}
		});
		
		long endTime = System.nanoTime();
		
		totalSortMovesNanos += endTime - startTime;
		
		return moves;
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
	
	private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	public Dot getDot(char ch) {
		int index = alphabet.indexOf(ch);
		
		int r = index / (numCols + 1);
		int c = index % (numCols + 1);
		
		System.out.println(ch + " : " + r + "," + c);
		
		return new Dot(r, c);
	}
	
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
